/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException;
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
public class Requester {

    private final TokenRepository tokenRepository;
    private final ResponseParser responseParser;
    private final TokenProxyService tokenProxyService;

    Requester(final TokenRepository tokenRepository, final ResponseParser responseParser, final TokenProxyService tokenProxyService) {
        this.tokenRepository = tokenRepository;
        this.responseParser = responseParser;
        this.tokenProxyService = tokenProxyService;
    }

    /**
     * Makes a request to HP Haven OnDemand using a TokenProxy provided by a {@link TokenProxyService}, converting the
     * response body to the type specified by the Class object.
     * @param returnType The desired type of the value returned by HP Haven OnDemand
     * @param backendCaller Makes the request to HP Haven OnDemand
     * @param <T> The desired type of the value returned by HP Haven OnDemand
     * @return An object representing the output from HP Haven OnDemand
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService has not been configured
     */
    public <T> T makeRequest(final Class<T> returnType, final BackendCaller backendCaller) throws HodErrorException {
        checkTokenProxyService();
        return makeRequest(tokenProxyService.getTokenProxy(), returnType, backendCaller);
    }

    /**
     * Makes a request to HP Haven OnDemand using a TokenProxy provided by a {@link TokenProxyService}, converting the
     * response body to the type specified by the type reference.
     * @param typeReference The desired type of the value returned by HP Haven OnDemand
     * @param backendCaller Makes the request to HP Haven OnDemand
     * @param <T> The desired type of the value returned by HP Haven OnDemand
     * @return An object representing the output from HP Haven OnDemand
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService has not been configured
     */
    public <T> T makeRequest(final TypeReference<T> typeReference, final BackendCaller backendCaller) throws HodErrorException {
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
    public InputStream makeRequest(final BackendCaller backendCaller) throws HodErrorException {
        checkTokenProxyService();
        return makeRequest(tokenProxyService.getTokenProxy(), backendCaller);
    }

    /**
     * Makes a request to HP Haven OnDemand using a TokenProxy provided by a {@link TokenProxyService}, converting the
     * response body to the type specified by the JavaType. This method does not guarantee that the type parameter T
     * represents the given JavaType. If possible, use one of the safe makeRequest methods instead.
     * @param type The desired type of the value returned by HP Haven OnDemand
     * @param backendCaller Makes the request to HP Haven OnDemand
     * @param <T> The desired type of the value returned by HP Haven OnDemand
     * @return An object representing the output from HP Haven OnDemand
     * @throws HodErrorException
     */
    public <T> T unsafeMakeRequest(final JavaType type, final BackendCaller backendCaller) throws HodErrorException {
        checkTokenProxyService();
        return unsafeMakeRequest(tokenProxyService.getTokenProxy(), type, backendCaller);
    }

    /**
     * Makes a request to HP Haven OnDemand, converting the response body to the type specified by the Class object.
     * @param tokenProxy The token proxy to use to make the request
     * @param returnType The desired type of the value returned by HP Haven OnDemand
     * @param backendCaller Makes the request to HP Haven OnDemand
     * @param <T> The desired type of the value returned by HP Haven OnDemand
     * @return An object representing the output from HP Haven OnDemand
     * @throws HodErrorException
     */
    public <T> T makeRequest(final TokenProxy tokenProxy, final Class<T> returnType, final BackendCaller backendCaller) throws HodErrorException {
        final AuthenticationToken authenticationToken = getAuthenticationToken(tokenProxy);
        return responseParser.parseResponse(tokenProxy, returnType, backendCaller.makeRequest(authenticationToken));
    }

    /**
     * Makes a request to HP Haven OnDemand, converting the response body to the type specified by the type reference.
     * @param tokenProxy The token proxy to use to make the request
     * @param typeReference The desired type of the value returned by HP Haven OnDemand
     * @param backendCaller Makes the request to HP Haven OnDemand
     * @param <T> The desired type of the value returned by HP Haven OnDemand
     * @return An object representing the output from HP Haven OnDemand
     * @throws HodErrorException
     */
    public <T> T makeRequest(final TokenProxy tokenProxy, final TypeReference<T> typeReference, final BackendCaller backendCaller) throws HodErrorException {
        final AuthenticationToken authenticationToken = getAuthenticationToken(tokenProxy);
        return responseParser.parseResponse(tokenProxy, typeReference, backendCaller.makeRequest(authenticationToken));
    }

    /**
     * Makes a request to HP Haven OnDemand, returning the response body as an input stream.
     * @param tokenProxy The token proxy to use to make the request
     * @param backendCaller Makes the request to HP Haven OnDemand
     * @return The response body
     * @throws HodErrorException
     */
    public InputStream makeRequest(final TokenProxy tokenProxy, final BackendCaller backendCaller) throws HodErrorException {
        final AuthenticationToken authenticationToken = getAuthenticationToken(tokenProxy);
        return responseParser.parseResponse(tokenProxy, backendCaller.makeRequest(authenticationToken));
    }

    /**
     * Makes a request to HP Haven OnDemand, converting the response body to the type specified by the JavaType. This
     * method does not guarantee that the type parameter T represents the given JavaType. If possible, use one of the
     * safe makeRequest methods instead.
     * @param tokenProxy The token proxy to use to make the request
     * @param type The desired type of the value returned by HP Haven OnDemand
     * @param backendCaller Makes the request to HP Haven OnDemand
     * @param <T> The desired type of the value returned by HP Haven OnDemand
     * @return An object representing the output from HP Haven OnDemand
     * @throws HodErrorException
     */
    public <T> T unsafeMakeRequest(final TokenProxy tokenProxy, final JavaType type, final BackendCaller backendCaller) throws HodErrorException {
        final AuthenticationToken authenticationToken = getAuthenticationToken(tokenProxy);
        return responseParser.unsafeParseResponse(tokenProxy, type, backendCaller.makeRequest(authenticationToken));
    }

    private void checkTokenProxyService() {
        if (tokenProxyService == null) {
            throw new NullPointerException("A TokenProxyService has not been configured so a TokenProxy must be supplied");
        }
    }

    private AuthenticationToken getAuthenticationToken(final TokenProxy tokenProxy) {
        final AuthenticationToken authenticationToken;

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
     * Exchanges an AuthenticationToken for a response from HP Haven OnDemand
     */
    public interface BackendCaller {

        /**
         * Makes a request to HP Haven OnDemand
         * @param authenticationToken The token with which to make the request
         * @return A response from HP Haven OnDemand
         * @throws HodErrorException
         */
        Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException;

    }
}
