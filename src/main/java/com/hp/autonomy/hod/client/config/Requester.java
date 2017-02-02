/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.token.TokenProxyService;
import com.hp.autonomy.hod.client.token.TokenRepository;
import com.hp.autonomy.hod.client.token.TokenRepositoryException;
import retrofit.client.Response;

import java.io.IOException;
import java.io.InputStream;

/**
 * Makes a request to HP Haven OnDemand
 */
public class Requester<E extends EntityType, T extends TokenType> {

    private final TokenRepository tokenRepository;
    private final ResponseParser responseParser;
    private final TokenProxyService<? extends E, ? extends T> tokenProxyService;

    Requester(final TokenRepository tokenRepository, final ResponseParser responseParser, final TokenProxyService<? extends E, ? extends T> tokenProxyService) {
        this.tokenRepository = tokenRepository;
        this.responseParser = responseParser;
        this.tokenProxyService = tokenProxyService;
    }

    /**
     * Makes a request to HP Haven OnDemand using a TokenProxy provided by a {@link TokenProxyService}, converting the
     * response body to the type specified by the Class object.
     * @param returnType The desired type of the value returned by HP Haven OnDemand
     * @param backendCaller Makes the request to HP Haven OnDemand
     * @param <U> The desired type of the value returned by HP Haven OnDemand
     * @return An object representing the output from HP Haven OnDemand
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService has not been configured
     */
    public <U> U makeRequest(final Class<U> returnType, final BackendCaller<? super E, ? super T> backendCaller) throws HodErrorException {
        checkTokenProxyService();
        return makeRequest(tokenProxyService.getTokenProxy(), returnType, backendCaller);
    }

    /**
     * Makes a request to HP Haven OnDemand using a TokenProxy provided by a {@link TokenProxyService}, converting the
     * response body to the type specified by the type reference.
     * @param typeReference The desired type of the value returned by HP Haven OnDemand
     * @param backendCaller Makes the request to HP Haven OnDemand
     * @param <U> The desired type of the value returned by HP Haven OnDemand
     * @return An object representing the output from HP Haven OnDemand
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService has not been configured
     */
    public <U> U makeRequest(final TypeReference<U> typeReference, final BackendCaller<? super E, ? super T> backendCaller) throws HodErrorException {
        checkTokenProxyService();
        return makeRequest(tokenProxyService.getTokenProxy(), typeReference, backendCaller);
    }

    /**
     * Makes a request to HP Haven OnDemand using a TokenProxy provided by a {@link TokenProxyService}, returning the
     * response body as an input stream.
     * @param backendCaller Makes the request to HP Haven OnDemand
     * @return The response body
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService has not been configured
     */
    public InputStream makeRequest(final BackendCaller<? super E, ? super T> backendCaller) throws HodErrorException {
        checkTokenProxyService();
        return makeRequest(tokenProxyService.getTokenProxy(), backendCaller);
    }

    /**
     * Makes a request to HP Haven OnDemand using a TokenProxy provided by a {@link TokenProxyService}, converting the
     * response body to the type specified by the JavaType. This method does not guarantee that the type parameter U
     * represents the given JavaType. If possible, use one of the safe makeRequest methods instead.
     * @param type The desired type of the value returned by HP Haven OnDemand
     * @param backendCaller Makes the request to HP Haven OnDemand
     * @param <U> The desired type of the value returned by HP Haven OnDemand
     * @return An object representing the output from HP Haven OnDemand
     * @throws HodErrorException
     */
    public <U> U unsafeMakeRequest(final JavaType type, final BackendCaller<? super E, ? super T> backendCaller) throws HodErrorException {
        checkTokenProxyService();
        return unsafeMakeRequest(tokenProxyService.getTokenProxy(), type, backendCaller);
    }

    /**
     * Makes a request to HP Haven OnDemand, converting the response body to the type specified by the Class object.
     * @param tokenProxy The token proxy to use to make the request
     * @param returnType The desired type of the value returned by HP Haven OnDemand
     * @param backendCaller Makes the request to HP Haven OnDemand
     * @param <U> The desired type of the value returned by HP Haven OnDemand
     * @param <BE> The entity type of the token proxy
     * @param <BT> The token type of the token proxy
     * @return An object representing the output from HP Haven OnDemand
     * @throws HodErrorException
     */
    public <U, BE extends EntityType, BT extends TokenType> U makeRequest(
        final TokenProxy<BE, BT> tokenProxy,
        final Class<U> returnType,
        final BackendCaller<? super BE, ? super BT> backendCaller
    ) throws HodErrorException {
        return responseParser.parseResponse(tokenProxy, returnType, backendCaller.makeRequest(getAuthenticationToken(tokenProxy)));
    }

    /**
     * Makes a request to HP Haven OnDemand, converting the response body to the type specified by the type reference.
     * @param tokenProxy The token proxy to use to make the request
     * @param typeReference The desired type of the value returned by HP Haven OnDemand
     * @param backendCaller Makes the request to HP Haven OnDemand
     * @param <U> The desired type of the value returned by HP Haven OnDemand
     * @param <BE> The entity type of the token proxy
     * @param <BT> The token type of the token proxy
     * @return An object representing the output from HP Haven OnDemand
     * @throws HodErrorException
     */
    public <U, BE extends EntityType, BT extends TokenType> U makeRequest(
        final TokenProxy<BE, BT> tokenProxy,
        final TypeReference<U> typeReference,
        final BackendCaller<? super BE, ? super BT> backendCaller
    ) throws HodErrorException {
        return responseParser.parseResponse(tokenProxy, typeReference, backendCaller.makeRequest(getAuthenticationToken(tokenProxy)));
    }

    /**
     * Makes a request to HP Haven OnDemand, returning the response body as an input stream.
     * @param tokenProxy The token proxy to use to make the request
     * @param backendCaller Makes the request to HP Haven OnDemand
     * @param <BE> The entity type of the token proxy
     * @param <BT> The token type of the token proxy
     * @return The response body
     * @throws HodErrorException
     */
    public <BE extends EntityType, BT extends TokenType> InputStream makeRequest(
        final TokenProxy<BE, BT> tokenProxy,
        final BackendCaller<? super BE, ? super BT> backendCaller
    ) throws HodErrorException {
        return responseParser.parseResponse(tokenProxy, backendCaller.makeRequest(getAuthenticationToken(tokenProxy)));
    }

    /**
     * Makes a request to HP Haven OnDemand, converting the response body to the type specified by the JavaType. This
     * method does not guarantee that the type parameter U represents the given JavaType. If possible, use one of the
     * safe makeRequest methods instead.
     * @param tokenProxy The token proxy to use to make the request
     * @param type The desired type of the value returned by HP Haven OnDemand
     * @param backendCaller Makes the request to HP Haven OnDemand
     * @param <U> The desired type of the value returned by HP Haven OnDemand
     * @param <BE> The entity type of the token proxy
     * @param <BT> The token type of the token proxy
     * @return An object representing the output from HP Haven OnDemand
     * @throws HodErrorException
     */
    public <U, BE extends EntityType, BT extends TokenType> U unsafeMakeRequest(
        final TokenProxy<BE, BT> tokenProxy,
        final JavaType type,
        final BackendCaller<? super BE, ? super BT> backendCaller
    ) throws HodErrorException {
        return responseParser.unsafeParseResponse(tokenProxy, type, backendCaller.makeRequest(getAuthenticationToken(tokenProxy)));
    }

    private void checkTokenProxyService() {
        if (tokenProxyService == null) {
            throw new NullPointerException("A TokenProxyService has not been configured so a TokenProxy must be supplied");
        }
    }

    private <PE extends EntityType, PT extends TokenType> AuthenticationToken<PE, PT> getAuthenticationToken(final TokenProxy<PE, PT> tokenProxy) {
        final AuthenticationToken<PE, PT> authenticationToken;

        try {
            authenticationToken = tokenRepository.get(tokenProxy);

            if (authenticationToken == null) {
                throw new HodAuthenticationFailedException("No token found - perhaps it has expired", tokenProxy);
            }
            else if (authenticationToken.hasExpired()) {
                // this token is no good, so why hang onto it
                tokenRepository.remove(tokenProxy);

                throw new HodAuthenticationFailedException("Token has expired", tokenProxy);
            }
        } catch (final IOException e) {
            throw new TokenRepositoryException(e);
        }

        return authenticationToken;
    }

    /**
     * Exchanges an AuthenticationToken for a response from HP Haven OnDemand.
     * @param <E> The most general authentication entity type which this backend caller accepts
     * @param <T> The most general authentication token type which this backend caller accepts
     */
    @FunctionalInterface
    public interface BackendCaller<E extends EntityType, T extends TokenType> {

        /**
         * Makes a request to HP Haven OnDemand
         * @param authenticationToken The token with which to make the request
         * @return A response from HP Haven OnDemand
         * @throws HodErrorException
         */
        Response makeRequest(final AuthenticationToken<? extends E, ? extends T> authenticationToken) throws HodErrorException;

    }
}
