/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.token.TokenRepository;
import org.junit.Before;
import org.junit.Test;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.mime.TypedInput;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

public class ResponseParserTest {
    private static final TypeReference<List<Object>> LIST_TYPE_REFERENCE = new TypeReference<List<Object>>() {};

    // TypeFactory is final so cannot be mocked
    private final TypeFactory typeFactory = TypeFactory.defaultInstance();

    private ResponseParser responseParser;
    private ObjectMapper objectMapper;
    private TokenRepository tokenRepository;

    @Before
    public void setUp() {
        objectMapper = mock(ObjectMapper.class);
        when(objectMapper.getTypeFactory()).thenReturn(typeFactory);

        tokenRepository = mock(TokenRepository.class);
        responseParser = new ResponseParser(tokenRepository, objectMapper);
    }

    @Test
    public void testParseResponseToClassWithARefreshedToken() throws IOException {
        final ResponseAndBody responseAndBody = createTestRefreshResponse();

        final JavaType objectType = typeFactory.uncheckedSimpleType(Object.class);
        when(objectMapper.constructType(eq(Object.class))).thenReturn(objectType);

        final Object expectedReturnValue = new Object();
        final AuthenticationToken newToken = mock(AuthenticationToken.class);

        when(objectMapper.readValue(isA(String.class), eq(AuthenticationToken.class))).thenReturn(newToken);
        when(objectMapper.readValue(eq(responseAndBody.body), eq(objectType))).thenReturn(expectedReturnValue);

        final TokenProxy tokenProxy = new TokenProxy();

        final Object returnValue = responseParser.parseResponse(tokenProxy, Object.class, responseAndBody.response);

        verify(tokenRepository).update(tokenProxy, newToken);
        assertThat(returnValue, is(expectedReturnValue));
    }

    @Test
    public void testParseResponseToClassWithoutARefreshedToken() throws IOException {
        final ResponseAndBody responseAndBody = createTestResponse();
        final Object expectedReturnValue = new Object();

        final JavaType objectType = typeFactory.uncheckedSimpleType(Object.class);
        when(objectMapper.constructType(eq(Object.class))).thenReturn(objectType);

        when(objectMapper.readValue(eq(responseAndBody.body), eq(objectType))).thenReturn(expectedReturnValue);

        final TokenProxy tokenProxy = new TokenProxy();

        final Object returnValue = responseParser.parseResponse(tokenProxy, Object.class, responseAndBody.response);

        verify(tokenRepository, never()).update(isA(TokenProxy.class), isA(AuthenticationToken.class));
        assertThat(returnValue, is(expectedReturnValue));
    }

    @Test
    public void parseResponseToJavaTypeWithRefreshedToken() throws IOException {
        final ResponseAndBody responseAndBody = createTestRefreshResponse();

        final Object expectedReturnValue = new Object();
        final AuthenticationToken newToken = mock(AuthenticationToken.class);

        final JavaType objectType = typeFactory.uncheckedSimpleType(Object.class);

        when(objectMapper.readValue(isA(String.class), eq(AuthenticationToken.class))).thenReturn(newToken);
        when(objectMapper.readValue(eq(responseAndBody.body), eq(objectType))).thenReturn(expectedReturnValue);

        final TokenProxy tokenProxy = new TokenProxy();

        final Object returnValue = responseParser.unsafeParseResponse(tokenProxy, objectType, responseAndBody.response);

        verify(tokenRepository).update(tokenProxy, newToken);
        assertThat(returnValue, is(expectedReturnValue));
    }

    @Test
    public void parseResponseToJavaTypeWithoutRefreshedToken() throws IOException {
        final ResponseAndBody responseAndBody = createTestResponse();

        final TokenProxy tokenProxy = new TokenProxy();
        final Object expectedReturnValue = new Object();

        final JavaType objectType = TypeFactory.defaultInstance().uncheckedSimpleType(Object.class);
        when(objectMapper.readValue(eq(responseAndBody.body), eq(objectType))).thenReturn(expectedReturnValue);

        final Object returnValue = responseParser.unsafeParseResponse(tokenProxy, objectType, responseAndBody.response);

        verify(tokenRepository, never()).update(isA(TokenProxy.class), isA(AuthenticationToken.class));
        assertThat(returnValue, is(expectedReturnValue));
    }

    @Test
    public void parseResponseToTypeReferenceWithRefreshedToken() throws IOException {
        final ResponseAndBody responseAndBody = createTestRefreshResponse();

        final AuthenticationToken newToken = mock(AuthenticationToken.class);
        final List<Object> expectedReturnValue = new ArrayList<>();

        final JavaType listType = typeFactory.constructType(LIST_TYPE_REFERENCE);
        when(objectMapper.readValue(isA(String.class), eq(AuthenticationToken.class))).thenReturn(newToken);
        when(objectMapper.readValue(eq(responseAndBody.body), eq(listType))).thenReturn(expectedReturnValue);

        final TokenProxy tokenProxy = new TokenProxy();

        final List<Object> returnValue = responseParser.parseResponse(tokenProxy, LIST_TYPE_REFERENCE, responseAndBody.response);

        verify(tokenRepository).update(tokenProxy, newToken);
        assertThat(returnValue, is(expectedReturnValue));
    }

    @Test
    public void parseResponseToTypeReferenceWithoutRefreshedToken() throws IOException {
        final ResponseAndBody responseAndBody = createTestResponse();

        final List<Object> expectedReturnValue = new ArrayList<>();

        final JavaType listType = typeFactory.constructType(LIST_TYPE_REFERENCE);
        when(objectMapper.readValue(eq(responseAndBody.body), eq(listType))).thenReturn(expectedReturnValue);

        final TokenProxy tokenProxy = new TokenProxy();

        final List<Object> returnValue = responseParser.parseResponse(tokenProxy, LIST_TYPE_REFERENCE, responseAndBody.response);

        verify(tokenRepository, never()).update(isA(TokenProxy.class), isA(AuthenticationToken.class));
        assertThat(returnValue, is(expectedReturnValue));
    }

    @Test
    public void parseResponseToInputStreamWithRefreshedToken() throws IOException {
        final AuthenticationToken newToken = mock(AuthenticationToken.class);
        final ResponseAndBody responseAndBody = createTestRefreshResponse();

        when(objectMapper.readValue(isA(String.class), eq(AuthenticationToken.class))).thenReturn(newToken);

        final TokenProxy tokenProxy = new TokenProxy();

        final InputStream returnValue = responseParser.parseResponse(tokenProxy, responseAndBody.response);

        verify(tokenRepository).update(tokenProxy, newToken);
        assertThat(returnValue, is(responseAndBody.body));
    }

    @Test
    public void parseResponseToInputStreamWithoutRefreshedToken() throws IOException {
        final ResponseAndBody responseAndBody = createTestResponse();
        final TokenProxy tokenProxy = new TokenProxy();
        final InputStream returnValue = responseParser.parseResponse(tokenProxy, responseAndBody.response);

        verify(tokenRepository, never()).update(isA(TokenProxy.class), isA(AuthenticationToken.class));
        assertThat(returnValue, is(responseAndBody.body));
    }

    private ResponseAndBody createTestResponse() throws IOException {
        return createTestResponseWithHeaders(Collections.<Header>emptyList());
    }

    private ResponseAndBody createTestRefreshResponse() throws IOException {
        final List<Header> headers = Arrays.asList(
            new Header("foo", "bar"),
            new Header("refresh_token", "TOKEN") // the object mapper isn't real so the value can be anything
        );

        return createTestResponseWithHeaders(headers);
    }

    private ResponseAndBody createTestResponseWithHeaders(final List<Header> headers) throws IOException {
        final InputStream mockBodyStream = mock(InputStream.class);
        final TypedInput mockBody = mock(TypedInput.class);
        when(mockBody.in()).thenReturn(mockBodyStream);

        // can't mock this because it is final
        return new ResponseAndBody(new Response("", 200, "", headers, mockBody), mockBodyStream);
    }

    private static class ResponseAndBody {
        private final Response response;
        private final InputStream body;

        private ResponseAndBody(final Response response, final InputStream body) {
            this.response = response;
            this.body = body;
        }
    }
}