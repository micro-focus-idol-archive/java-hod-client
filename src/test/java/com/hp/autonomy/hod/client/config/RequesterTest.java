/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.config;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.token.TokenProxyService;
import com.hp.autonomy.hod.client.token.TokenRepository;
import org.junit.Before;
import org.junit.Test;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.mime.TypedInput;

import java.io.IOException;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RequesterTest {

    private TokenRepository tokenRepository;
    private ResponseParser responseParser;
    private Requester requester;

    @Before
    public void setUp() {
        tokenRepository = mock(TokenRepository.class);
        responseParser = mock(ResponseParser.class);
        requester = new Requester(tokenRepository, responseParser, null);
    }

    @Test(expected = NullPointerException.class)
    public void testRequesterThrowsNullPointerExceptionWhenTryingToUseTokenProxyServiceIncorrectly() throws HodErrorException {
        requester.makeRequest(Object.class, mock(Requester.BackendCaller.class));
    }

    @Test
    public void testRequesterUsesTokenProxyService() throws HodErrorException, IOException {
        final TokenProxy tokenProxy = new TokenProxy();
        final AuthenticationToken fakeToken = mock(AuthenticationToken.class);
        when(fakeToken.hasExpired()).thenReturn(false);

        final TokenProxyService tokenProxyService = new TokenProxyService() {
            @Override
            public TokenProxy getTokenProxy() {
                return tokenProxy;
            }
        };

        when(tokenRepository.get(tokenProxy)).thenReturn(fakeToken);

        final Response response = new Response("", 200, "", Collections.<Header>emptyList(), mock(TypedInput.class));
        final Object expectedReturnValue = new Object();

        when(responseParser.parseResponse(tokenProxy, Object.class, response)).thenReturn(expectedReturnValue);

        final Requester requester = new Requester(tokenRepository, responseParser, tokenProxyService);

        final Object result = requester.makeRequest(tokenProxy, Object.class, getBackendCaller(fakeToken, response));

        assertThat(result, is(expectedReturnValue));
    }

    @Test(expected = HodAuthenticationFailedException.class)
    public void testMakeRequestThrowsWhenNoTokenInRepository() throws IOException, HodErrorException {
        final TokenProxy tokenProxy = new TokenProxy();

        when(tokenRepository.get(tokenProxy)).thenReturn(null);

        requester.makeRequest(tokenProxy, Object.class, null);
    }

    @Test
    public void testMakeRequestThrowsAndRemovesExpiredTokensFromRepository() throws IOException, HodErrorException {
        final TokenProxy tokenProxy = new TokenProxy();
        final AuthenticationToken token = mock(AuthenticationToken.class);
        when(token.hasExpired()).thenReturn(true);

        when(tokenRepository.get(tokenProxy)).thenReturn(token);

        try {
            requester.makeRequest(tokenProxy, Object.class, null);

            fail("HodAuthenticationFailedException not thrown");
        } catch (final HodAuthenticationFailedException e) {
            verify(tokenRepository).remove(tokenProxy);
        }
    }

    @Test
    public void testRequesterWorksCorrectlyWithAValidToken() throws IOException, HodErrorException {
        final TokenProxy tokenProxy = new TokenProxy();
        final AuthenticationToken fakeToken = mock(AuthenticationToken.class);
        when(fakeToken.hasExpired()).thenReturn(false);

        when(tokenRepository.get(tokenProxy)).thenReturn(fakeToken);

        final Response response = new Response("", 200, "", Collections.<Header>emptyList(), mock(TypedInput.class));
        final Object expectedReturnValue = new Object();

        when(responseParser.parseResponse(tokenProxy, Object.class, response)).thenReturn(expectedReturnValue);

        final Requester requester = new Requester(tokenRepository, responseParser, null);

        final Object result = requester.makeRequest(tokenProxy, Object.class, getBackendCaller(fakeToken, response));

        assertThat(result, is(expectedReturnValue));
    }

    private Requester.BackendCaller getBackendCaller(final AuthenticationToken fakeToken, final Response response) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                assertThat(authenticationToken, is(fakeToken));

                return response;
            }
        };
    }

}