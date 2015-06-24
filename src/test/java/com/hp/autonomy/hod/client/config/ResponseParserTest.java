/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.config;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.token.TokenRepository;
import org.junit.Before;
import org.junit.Test;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ResponseParserTest {

    private ResponseParser responseParser;
    private Converter converter;
    private TokenRepository tokenRepository;

    @Before
    public void setUp() {
        converter = mock(Converter.class);
        tokenRepository = mock(TokenRepository.class);

        responseParser = new ResponseParser(tokenRepository, converter);
    }

    @Test
    public void testParseResponseWithARefreshedToken() throws IOException, ConversionException {
        final AuthenticationToken newToken = mock(AuthenticationToken.class);

        final List<Header> headers = Arrays.asList(
            new Header("foo", "bar"),
            new Header("refresh_token", "TOKEN") // the converter isn't real so the value can be anything
        );

        final Object expectedReturnValue = new Object();

        // can't mock this because it is final
        final Response response = new Response("", 200, "", headers, mock(TypedInput.class));

        when(converter.fromBody(isA(TypedInput.class), eq(AuthenticationToken.class))).thenReturn(newToken);
        when(converter.fromBody(isA(TypedInput.class), eq(Object.class))).thenReturn(expectedReturnValue);

        final TokenProxy tokenProxy = new TokenProxy();

        final Object returnValue = responseParser.parseResponse(tokenProxy, Object.class, response);

        verify(tokenRepository).update(tokenProxy, newToken);
        assertThat(returnValue, is(expectedReturnValue));
    }

    @Test
    public void testResponseParserWithoutARefreshedToken() throws ConversionException, IOException {
        final Object expectedReturnValue = new Object();

        // can't mock this because it is final
        final Response response = new Response("", 200, "", Collections.<Header>emptyList(), mock(TypedInput.class));

        when(converter.fromBody(isA(TypedInput.class), eq(Object.class))).thenReturn(expectedReturnValue);

        final TokenProxy tokenProxy = new TokenProxy();

        final Object returnValue = responseParser.parseResponse(tokenProxy, Object.class, response);

        verify(tokenRepository, never()).update(isA(TokenProxy.class), isA(AuthenticationToken.class));
        assertThat(returnValue, is(expectedReturnValue));
    }

}