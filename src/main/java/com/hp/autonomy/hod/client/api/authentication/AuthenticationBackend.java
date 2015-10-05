/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.http.GET;
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
     * Acquire an unbound token for use with HP Haven OnDemand. This must be combined with user authentication in a
     * user's browser.
     * @param apiKey The application API key
     * @return A response containing an unbound application token
     * @throws HodErrorException
     */
    @POST("/2/authenticate/unbound")
    @Multipart
    AuthenticationTokenResponse authenticateApplicationUnbound(
            @Header("apiKey") ApiKey apiKey,
            @Part("token_type") TokenType tokenType
    ) throws HodErrorException;

}
