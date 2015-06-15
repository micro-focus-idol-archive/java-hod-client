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
import com.hp.autonomy.hod.client.token.TokenRepositoryException;
import retrofit.client.Response;

import java.io.IOException;

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
     * Makes a request to HP Haven OnDemand using a TokenProxy provided by a {@link TokenProxyService}
     * @param returnType The desired type of the value returned by HP Haven OnDemand
     * @param backendCaller Makes the request to HP Haven OnDemand
     * @param <T> The desired type of the value returned by HP Haven OnDemand
     * @return An object representing the output from HP Haven OnDemand
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService has not been configured
     */
    public <T> T makeRequest(final Class<T> returnType, final BackendCaller backendCaller) throws HodErrorException {
        if (tokenProxyService == null) {
            throw new NullPointerException("A TokenProxyService has not been configured so a TokenProxy must be supplied");
        }

        return makeRequest(tokenProxyService.getTokenProxy(), returnType, backendCaller);
    }

    /**
     * Makes a request to HP Haven OnDemand using the given TokenProxy
     * @param tokenProxy The token proxy to use to make the request
     * @param returnType The desired type of the value returned by HP Haven OnDemand
     * @param backendCaller Makes the request to HP Haven OnDemand
     * @param <T> The desired type of the value returned by HP Haven OnDemand
     * @return An object representing the output from HP Haven OnDemand
     * @throws HodErrorException
     */
    public <T> T makeRequest(final TokenProxy tokenProxy, final Class<T> returnType, final BackendCaller backendCaller) throws HodErrorException {
        try {
            final AuthenticationToken authenticationToken = tokenRepository.get(tokenProxy);

            if (authenticationToken == null) {
                throw new HodAuthenticationFailedException("No token found - perhaps it has expired", tokenProxy);
            }
            else if(authenticationToken.hasExpired()) {
                // this token is no good, so why hang onto it
                tokenRepository.remove(tokenProxy);

                throw new HodAuthenticationFailedException("Token has expired", tokenProxy);
            }
            else {
                return responseParser.parseResponse(tokenProxy, returnType, backendCaller.makeRequest(authenticationToken));
            }
        } catch (final IOException e) {
            throw new TokenRepositoryException(e);
        }
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
