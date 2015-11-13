/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.config;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RequesterTest {

    private TokenRepository tokenRepository;
    private ResponseParser responseParser;
    private Requester<EntityType.Application, TokenType.Simple> requester;

    @Before
    public void setUp() {
        tokenRepository = mock(TokenRepository.class);
        responseParser = mock(ResponseParser.class);
        requester = new Requester<>(tokenRepository, responseParser, null);
    }

    @Test(expected = NullPointerException.class)
    public void testRequesterThrowsNullPointerExceptionWhenTryingToUseTokenProxyServiceIncorrectly() throws HodErrorException {
        @SuppressWarnings("unchecked")
        final Requester.BackendCaller<EntityType.Application, TokenType.Simple> backendCaller = mock(Requester.BackendCaller.class);
        requester.makeRequest(Object.class, backendCaller);
    }

    @Test
    public void testRequesterUsesTokenProxyService() throws HodErrorException, IOException {
        @SuppressWarnings("unchecked")
        final AuthenticationToken<EntityType.Application, TokenType.Simple> fakeToken = mock(AuthenticationToken.class);

        final TokenProxy<EntityType.Application, TokenType.Simple> tokenProxy = new TokenProxy<>(EntityType.Application.INSTANCE, TokenType.Simple.INSTANCE);
        when(fakeToken.hasExpired()).thenReturn(false);

        final TokenProxyService<?, TokenType.Simple> tokenProxyService = new TokenProxyService<EntityType.Application, TokenType.Simple>() {
            @Override
            public TokenProxy<EntityType.Application, TokenType.Simple> getTokenProxy() {
                return tokenProxy;
            }
        };

        when(tokenRepository.get(tokenProxy)).thenReturn(fakeToken);

        final Response response = new Response("", 200, "", Collections.<Header>emptyList(), mock(TypedInput.class));
        final Object expectedReturnValue = new Object();

        when(responseParser.parseResponse(tokenProxy, Object.class, response)).thenReturn(expectedReturnValue);

        final Requester<EntityType, TokenType.Simple> requester = new Requester<>(tokenRepository, responseParser, tokenProxyService);

        final Object result = requester.makeRequest(tokenProxy, Object.class, getBackendCaller(fakeToken, response));

        assertThat(result, is(expectedReturnValue));
    }

    @Test(expected = HodAuthenticationFailedException.class)
    public void testMakeRequestThrowsWhenNoTokenInRepository() throws IOException, HodErrorException {
        final TokenProxy<EntityType.Application, TokenType.Simple> tokenProxy = new TokenProxy<>(EntityType.Application.INSTANCE, TokenType.Simple.INSTANCE);

        when(tokenRepository.get(tokenProxy)).thenReturn(null);

        requester.makeRequest(tokenProxy, Object.class, null);
    }

    @Test
    public void testMakeRequestThrowsAndRemovesExpiredTokensFromRepository() throws IOException, HodErrorException {
        @SuppressWarnings("unchecked")
        final AuthenticationToken<EntityType.Application, TokenType.Simple> token = mock(AuthenticationToken.class);

        final TokenProxy<EntityType.Application, TokenType.Simple> tokenProxy = new TokenProxy<>(EntityType.Application.INSTANCE, TokenType.Simple.INSTANCE);
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
        @SuppressWarnings("unchecked")
        final AuthenticationToken<EntityType.Application, TokenType.Simple> fakeToken = mock(AuthenticationToken.class);

        final TokenProxy<EntityType.Application, TokenType.Simple> tokenProxy = new TokenProxy<>(EntityType.Application.INSTANCE, TokenType.Simple.INSTANCE);
        when(fakeToken.hasExpired()).thenReturn(false);

        when(tokenRepository.get(tokenProxy)).thenReturn(fakeToken);

        final Response response = new Response("", 200, "", Collections.<Header>emptyList(), mock(TypedInput.class));
        final Object expectedReturnValue = new Object();

        when(responseParser.parseResponse(tokenProxy, Object.class, response)).thenReturn(expectedReturnValue);

        final Requester<EntityType, TokenType.Simple> requester = new Requester<>(tokenRepository, responseParser, null);

        final Object result = requester.makeRequest(tokenProxy, Object.class, getBackendCaller(fakeToken, response));

        assertThat(result, is(expectedReturnValue));
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getBackendCaller(final AuthenticationToken<?, ? extends TokenType.Simple> fakeToken, final Response response) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                assertEquals(authenticationToken, fakeToken);

                return response;
            }
        };
    }

}