/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.token.TokenRepository;
import com.hp.autonomy.hod.client.token.TokenRepositoryException;
import retrofit.client.Header;
import retrofit.client.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Parses a response from HP Haven OnDemand. If a token is refreshed, this will update the token repository.
 */
class ResponseParser {

    private static final String TOKEN_HEADER_NAME = "refresh_token";

    private final TokenRepository tokenRepository;
    private final ObjectMapper objectMapper;

    /**
     * Create a new ResponseParser
     * @param tokenRepository The token repository to update if required
     * @param objectMapper The object mapper to use to convert the token and the response body
     */
    ResponseParser(final TokenRepository tokenRepository, final ObjectMapper objectMapper) {
        this.tokenRepository = tokenRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Parse the response into a type represented by the Class object. Checks for a refreshed token and updates the token
     * repository accordingly.
     * @param <T> The desired type of the response body
     * @param tokenProxy The token proxy to update in the token repository if required
     * @param clazz A class object representing the desired type of the response body
     * @param response The response to parse
     * @return An object representing the result from HP Haven OnDemand
     */
    <T> T parseResponse(final TokenProxy<?, ?> tokenProxy, final Class<T> clazz, final Response response) {
        return unsafeParseResponse(tokenProxy, objectMapper.constructType(clazz), response);
    }

    /**
     * Parse the response into a type represented by the TypeReference object. Checks for a refreshed token and updates
     * the token repository accordingly.
     * @param <T> The desired type of the response body
     * @param tokenProxy The token proxy to update in the token repository if required
     * @param typeReference A type reference object representing the desired type of the response body
     * @param response The response to parse
     * @return An object representing the result from HP Haven OnDemand
     */
    <T> T parseResponse(final TokenProxy<?, ?> tokenProxy, final TypeReference<T> typeReference, final Response response) {
        return unsafeParseResponse(tokenProxy, objectMapper.getTypeFactory().constructType(typeReference), response);
    }

    /**
     * Parse the response into a type represented by the JavaType object. Checks for a refreshed token and updates
     * the token repository accordingly. This method does not guarantee that the type parameter T represents the given
     * JavaType. If possible, use one of the safe parseResponse methods instead.
     * @param <T> The desired type of the response body
     * @param tokenProxy The token proxy to update in the token repository if required
     * @param type A JavaType representing the desired type of the response body
     * @param response The response to parse
     * @return An object representing the result from HP Haven OnDemand
     */
    <T> T unsafeParseResponse(final TokenProxy<?, ?> tokenProxy, final JavaType type, final Response response) {
        checkRefresh(tokenProxy, response);

        try {
            return objectMapper.readValue(response.getBody().in(), type);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks the response headers for a refreshed authentication token then returns the response body as an input stream.
     * @param tokenProxy The token proxy to update in the token repository if required
     * @param response The response to read
     * @return The response body as an input stream
     */
    InputStream parseResponse(final TokenProxy<?, ?> tokenProxy, final Response response) {
        checkRefresh(tokenProxy, response);

        try {
            return response.getBody().in();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <E extends EntityType, T extends TokenType> void checkRefresh(final TokenProxy<E, T> tokenProxy, final Response response) {
        final List<Header> headers = response.getHeaders();

        for(final Header header : headers) {
            if(TOKEN_HEADER_NAME.equals(header.getName())) {
                try {
                    final AuthenticationToken.Json parsedHeader = objectMapper.readValue(header.getValue(), AuthenticationToken.Json.class);
                    final AuthenticationToken<E, T> token = parsedHeader.buildToken(tokenProxy.getEntityType(), tokenProxy.getTokenType());
                    tokenRepository.update(tokenProxy, token);
                } catch (final IOException e) {
                    throw new TokenRepositoryException(e);
                }

                break;
            }
        }
    }

}
