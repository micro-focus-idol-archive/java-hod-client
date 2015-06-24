/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Service for making authentication requests to HP Haven OnDemand
 */
public interface AuthenticationBackend {

    /**
     * Acquire a token for an application
     * @param apiKey The API key of the application
     * @param applicationName The name of the application
     * @param domain The domain of the application
     * @param tokenType The type of the resulting token
     * @return A response containing a token for use with HP Haven OnDemand
     * @throws HodErrorException
     */
    @POST("/2/authenticate/application")
    @Multipart
    AuthenticationTokenResponse authenticateApplication(
            @Header("apiKey") ApiKey apiKey,
            @Part("name") String applicationName,
            @Part("domain") String domain,
            @Part("token_type") TokenType tokenType
    ) throws HodErrorException;

    /**
     * Acquire a token for a user
     * @param apiKey The API key of the user
     * @param applicationName The name of the application
     * @param applicationDomain The domain of the application
     * @param tokenType The type of the resulting token
     * @return A response containing a token for use with HP Haven OnDemand
     * @throws HodErrorException
     */
    @POST("/2/authenticate/user")
    @Multipart
    AuthenticationTokenResponse authenticateUser(
            @Header("apiKey") ApiKey apiKey,
            @Part("application_name") String applicationName,
            @Part("application_domain") String applicationDomain,
            @Part("token_type") TokenType tokenType
    ) throws HodErrorException;

    /**
     * Acquire a token for a user in a given user store
     * @param apiKey The API key of the user
     * @param applicationName The name of the application
     * @param applicationDomain The domain of the application
     * @param tokenType The type of the resulting token
     * @param userStore The name of the user store
     * @param storeDomain The domain of the user store
     * @return A response containing a token for use with HP Haven OnDemand
     * @throws HodErrorException
     */
    @POST("/2/authenticate/user")
    @Multipart
    AuthenticationTokenResponse authenticateUser(
            @Header("apiKey") ApiKey apiKey,
            @Part("application_name") String applicationName,
            @Part("application_domain") String applicationDomain,
            @Part("token_type") TokenType tokenType,
            @Part("user_store_name") String userStore,
            @Part("user_store_domain") String storeDomain
    ) throws HodErrorException;

    /**
     * Acquire an unbound application token for use with HP Haven OnDemand. This must be combined with an unbound user
     * token before it can be used to query HP Haven OnDemand
     * @param apiKey The application API key
     * @return A response containing an unbound application token and a list of applications
     * @throws HodErrorException
     */
    @POST("/2/authenticate/application/unbound")
    ApplicationUnboundResponse authenticateApplicationUnbound(
            @Header("apiKey") ApiKey apiKey
    ) throws HodErrorException;

    /**
     * Acquire an unbound user token for use with HP Haven OnDemand. This must be combined with an unbound application
     * token before it can be used to query HP Haven OnDemand
     * @param apiKey The user API key
     * @return A response containing an unbound user token and a list of users
     * @throws HodErrorException
     */
    @POST("/2/authenticate/user/unbound")
    UserUnboundResponse authenticateUserUnbound(
            @Header("apiKey") ApiKey apiKey
    ) throws HodErrorException;

    /**
     * Combines an unbound application token with an unbound user token
     * @param applicationToken An unbound application token
     * @param userToken An unbound user token
     * @param application The application name
     * @param domain The domain name
     * @param tokenType The type of the resulting token
     * @return A response containing a token which can be used to call HP Haven OnDemand
     * @throws HodErrorException
     */
    @POST("/2/authenticate/combined")
    @Multipart
    AuthenticationTokenResponse combineTokens(
        @Header("app_token") AuthenticationToken applicationToken,
        @Header("user_token") AuthenticationToken userToken,
        @Part("application") String application,
        @Part("domain") String domain,
        @Part("token_type") TokenType tokenType
    ) throws HodErrorException;

    /**
     * Combines an unbound application token with an unbound user token for a given user store
     * @param applicationToken A unbound application token
     * @param userToken An unbound user token
     * @param application The application name
     * @param domain The application domain name
     * @param tokenType The resulting token type
     * @param userStore The name of the user store
     * @param storeDomain The name of the user store
     * @return A response containing a token which can be used to call HP Haven OnDemand
     * @throws HodErrorException
     */
    @POST("/2/authenticate/combined")
    @Multipart
    AuthenticationTokenResponse combineTokens(
        @Header("app_token") AuthenticationToken applicationToken,
        @Header("user_token") AuthenticationToken userToken,
        @Part("application") String application,
        @Part("domain") String domain,
        @Part("token_type") TokenType tokenType,
        @Part("user_store_name") String userStore,
        @Part("user_store_domain") String storeDomain
    ) throws HodErrorException;

}
