/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.util.Collection;

/**
 * Service for making authentication requests to HP Haven OnDemand
 */
public interface AuthenticationService {

    /**
     * Acquire a token for an application
     * @param apiKey The API key of the application
     * @param applicationName The name of the application
     * @param domain The domain of the application
     * @param tokenType The type of the resulting token
     * @return A token for use with HP Haven OnDemand
     * @throws HodErrorException
     */
    TokenProxy authenticateApplication(
        ApiKey apiKey,
        String applicationName,
        String domain,
        TokenType tokenType
    ) throws HodErrorException;

    /**
     * Acquire a token for a user
     * @param apiKey The API key of the user
     * @param applicationName The name of the application
     * @param applicationDomain The domain of the application
     * @param tokenType The type of the resulting token
     * @return A token for use with HP Haven OnDemand
     * @throws HodErrorException
     */
    TokenProxy authenticateUser(
        ApiKey apiKey,
        String applicationName,
        String applicationDomain,
        TokenType tokenType
    ) throws HodErrorException;

    /**
     * Acquire a token for a user in a given user store
     * @param apiKey The API key of the user
     * @param applicationName The name of the application
     * @param applicationDomain The domain of the application
     * @param tokenType The type of the resulting token
     * @param userStore The name of the user store
     * @param storeDomain The domain of the user store
     * @return A token for use with HP Haven OnDemand
     * @throws HodErrorException
     */
    TokenProxy authenticateUser(
        ApiKey apiKey,
        String applicationName,
        String applicationDomain,
        TokenType tokenType,
        String userStore,
        String storeDomain
    ) throws HodErrorException;

    /**
     * Acquire an unbound application token for use with HP Haven OnDemand. This must be combined with an unbound user
     * token before it can be used to query HP Haven OnDemand
     * @param apiKey The application API key
     * @return A response containing an unbound application token and a list of applications
     * @throws HodErrorException
     */
    AuthenticationToken authenticateUnbound(
        ApiKey apiKey
    ) throws HodErrorException;

    /**
     * Get a representation of a request for obtaining a list of applications and domains associated with the given unbound
     * token. This request must then be made from a browser.
     * @param allowedOrigins Origins from which this request may be sent
     * @param token The unbound token to use to sign the request
     * @return A representation of an AJAX request to make from a browser
     */
    SignedRequest combinedGetRequest(
        Collection<String> allowedOrigins,
        AuthenticationToken token
    );

    /**
     * Get a representation of a request for obtaining a combined token from Haven OnDemand. This request must be made
     * from a browser. No nonce is included.
     * @param allowedOrigins Origins from which this request may be sent
     * @param token The unbound token to use to sign the request
     * @param applicationDomain Domain of the application
     * @param applicationName Name of the application
     * @param tokenType The type of the returned token
     * @return A representation of an AJAX request to make from a browser
     */
    SignedRequest combinedRequest(
        Collection<String> allowedOrigins,
        AuthenticationToken token,
        String applicationDomain,
        String applicationName,
        TokenType tokenType
    );

    /**
     * Get a representation of a request for obtaining a combined token from Haven OnDemand, specifying a user store. This
     * request must be made from a browser. No nonce is included.
     * @param allowedOrigins Origins from which this request may be sent
     * @param token The unbound token to use to sign the request
     * @param applicationDomain Domain of the application
     * @param applicationName Name of the application
     * @param userStoreDomain Domain of the user store
     * @param userStoreName Name of the user store
     * @param tokenType The type of the returned token
     * @return A representation of an AJAX request to make from a browser
     */
    SignedRequest combinedRequest(
        Collection<String> allowedOrigins,
        AuthenticationToken token,
        String applicationDomain,
        String applicationName,
        String userStoreDomain,
        String userStoreName,
        TokenType tokenType
    );

    /**
     * Get a representation of a request for obtaining a combined token from Haven OnDemand, specifying a user store. This
     * request must be made from a browser. A random unique nonce is included if useNonce is true.
     * @param allowedOrigins Origins from which this request may be sent
     * @param token The unbound token to use to sign the request
     * @param applicationDomain Domain of the application
     * @param applicationName Name of the application
     * @param userStoreDomain Domain of the user store
     * @param userStoreName Name of the user store
     * @param tokenType The type of the returned token
     * @param useNonce If true, a nonce will be generated and included in the request
     * @return A representation of an AJAX request to make from a browser
     */
    SignedRequest combinedRequest(
            Collection<String> allowedOrigins,
            AuthenticationToken token,
            String applicationDomain,
            String applicationName,
            String userStoreDomain,
            String userStoreName,
            TokenType tokenType,
            boolean useNonce
    );

}
