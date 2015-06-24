/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.config;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.token.TokenRepository;
import com.hp.autonomy.hod.client.token.TokenRepositoryException;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedString;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Parses a response from HP Haven OnDemand. If a token is refreshed, this will update
 */
class ResponseParser {

    private static final String TOKEN_HEADER_NAME = "refresh_token";

    private final TokenRepository tokenRepository;
    private final Converter converter;

    /**
     * Create a new ResponseParse
     * @param tokenRepository The token repository to update if required
     * @param converter The converter to use to convert the token and the response body
     */
    ResponseParser(final TokenRepository tokenRepository, final Converter converter) {
        this.tokenRepository = tokenRepository;
        this.converter = converter;
    }

    /**
     *
     * @param <T> The desired type of the response body
     * @param tokenProxy The token proxy to update in the token repository if required
     * @param clazz A class object representing the desired type of the response body
     * @param response The response to parse
     * @return An object representing the result from HP Haven OnDemand
     */
    <T> T parseResponse(final TokenProxy tokenProxy, final Class<T> clazz, final Response response) {
        final List<Header> headers = response.getHeaders();

        for(final Header header : headers) {
            if(TOKEN_HEADER_NAME.equals(header.getName())) {
                try {
                    final AuthenticationToken token = (AuthenticationToken) converter.fromBody(new TypedString(header.getValue()), AuthenticationToken.class);

                    tokenRepository.update(tokenProxy, token);
                } catch (final ConversionException | IOException e) {
                    throw new TokenRepositoryException(e);
                }

                break;
            }
        }

        try {
            if (clazz == InputStream.class) {
                // T is InputStream.class, so the caller is expecting an InputStream
                //noinspection unchecked
                return (T) response.getBody().in();
            } else {
                // The Jackson ObjectMapper actually returns a T but the Converter interface throws this info away
                @SuppressWarnings({"UnnecessaryLocalVariable", "unchecked"})
                final T typedResult = (T) converter.fromBody(response.getBody(), clazz);

                return typedResult;
            }
        } catch (final ConversionException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
