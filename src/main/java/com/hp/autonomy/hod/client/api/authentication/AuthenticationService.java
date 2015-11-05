/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import com.hp.autonomy.hod.client.api.authentication.tokeninformation.*;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.util.Collection;
import java.util.UUID;

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
     * @param <T> The type of the required token type
     * @return A token for use with HP Haven OnDemand
     * @throws HodErrorException
     */
    <T extends TokenType> TokenProxy<EntityType.Application, T> authenticateApplication(
        ApiKey apiKey,
        String applicationName,
        String domain,
        T tokenType
    ) throws HodErrorException;

    /**
     * Acquire a token for a user
     * @param apiKey The API key of the user
     * @param applicationName The name of the application
     * @param applicationDomain The domain of the application
     * @param tokenType The type of the resulting token
     * @param <T> The type of the required token type
     * @return A token for use with HP Haven OnDemand
     * @throws HodErrorException
     */
    <T extends TokenType> TokenProxy<EntityType.User, T> authenticateUser(
        ApiKey apiKey,
        String applicationName,
        String applicationDomain,
        T tokenType
    ) throws HodErrorException;

    /**
     * Acquire a token for a developer.
     * @param apiKey The API key to use for authentication
     * @param tenantUuid The UUID of the developer's tenant
     * @param email The email address of the developer
     * @return A token to authenticate request made to Haven OnDemand
     * @throws HodErrorException
     */
    AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> authenticateDeveloper(
        ApiKey apiKey,
        UUID tenantUuid,
        String email
    ) throws HodErrorException;

    /**
     * Acquire an unbound application token for use with HP Haven OnDemand. This must be combined with an unbound user
     * token before it can be used to query HP Haven OnDemand
     * @param apiKey The application API key
     * @return A response containing an unbound application token and a list of applications
     * @throws HodErrorException
     */
    <T extends TokenType> AuthenticationToken<EntityType.Unbound, T> authenticateUnbound(
        ApiKey apiKey,
        T tokenType
    ) throws HodErrorException;

    /**
     * Get information from Haven OnDemand about a simple combined token.
     * @param token The combined token
     * @return Information about the combined token
     * @throws HodErrorException
     */
    CombinedTokenInformation getCombinedTokenInformation(AuthenticationToken<EntityType.Combined, TokenType.Simple> token) throws HodErrorException;

    /**
     * Get information from Haven OnDemand about an HMAC combined token.
     * @param token The combined token
     * @return Information about the combined token
     * @throws HodErrorException
     */
    CombinedTokenInformation getHmacCombinedTokenInformation(AuthenticationToken<EntityType.Combined, TokenType.HmacSha1> token) throws HodErrorException;

    /**
     * Get information from Haven OnDemand about a developer token.
     * @param token The developer token
     * @return Information about the developer token
     * @throws HodErrorException
     */
    DeveloperTokenInformation getDeveloperTokenInformation(AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token) throws HodErrorException;

    /**
     * Get information from Haven OnDemand about the simple application token represented by the token proxy.
     * @param tokenProxy The application token proxy
     * @return Information about the application token
     * @throws HodErrorException
     */
    ApplicationTokenInformation getApplicationTokenInformation(TokenProxy<EntityType.Application, TokenType.Simple> tokenProxy) throws HodErrorException;

    /**
     * Get information from Haven OnDemand about the HMAC application token represented by a token proxy.
     * @param tokenProxy The application token proxy
     * @return Information about the application token
     * @throws HodErrorException
     */
    ApplicationTokenInformation getHmacApplicationTokenInformation(TokenProxy<EntityType.Application, TokenType.HmacSha1> tokenProxy) throws HodErrorException;

    /**
     * Get information from Haven OnDemand about the simple user token represented by a token proxy.
     * @param tokenProxy The user token proxy
     * @return Information about the user token
     * @throws HodErrorException
     */
    UserTokenInformation getUserTokenInformation(TokenProxy<EntityType.User, TokenType.Simple> tokenProxy) throws HodErrorException;

    /**
     * Get information from Haven OnDemand about the HMAC user token represented by a token proxy.
     * @param tokenProxy The user token proxy
     * @return Information about the user token
     * @throws HodErrorException
     */
    UserTokenInformation getHmacUserTokenInformation(TokenProxy<EntityType.User, TokenType.HmacSha1> tokenProxy) throws HodErrorException;

    /**
     * Get information from Haven OnDemand about a simple unbound token.
     * @param token The about an unbound token
     * @return Information about the about an unbound token
     * @throws HodErrorException
     */
    UnboundTokenInformation getUnboundTokenInformation(AuthenticationToken<EntityType.Unbound, TokenType.Simple> token) throws HodErrorException;

    /**
     * Get information from Haven OnDemand about an HMAC unbound token.
     * @param token The about an unbound token
     * @return Information about the about an unbound token
     * @throws HodErrorException
     */
    UnboundTokenInformation getHmacUnboundTokenInformation(AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> token) throws HodErrorException;

    /**
     * Get a representation of a request for obtaining a list of applications and domains associated with the given unbound
     * token. This request must then be made from a browser.
     * @param allowedOrigins Origins from which this request may be sent
     * @param token The unbound token to use to sign the request
     * @return A representation of an AJAX request to make from a browser
     */
    SignedRequest combinedGetRequest(
        Collection<String> allowedOrigins,
        AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> token
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
        AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> token,
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
        AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> token,
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
        AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> token,
        String applicationDomain,
        String applicationName,
        String userStoreDomain,
        String userStoreName,
        TokenType tokenType,
        boolean useNonce
    );

}
