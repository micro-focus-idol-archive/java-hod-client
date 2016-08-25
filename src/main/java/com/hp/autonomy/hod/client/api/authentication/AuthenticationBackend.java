/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;

/**
 * Service for making authentication requests to HP Haven OnDemand
 */
interface AuthenticationBackend {

    String GET_TOKEN_INFORMATION_PATH = "/2/authenticate";
    String COMBINED_PATH = "/2/authenticate/combined";

    String NONCE_PARAMETER = "nonce";
    String USERSTORE_NAME_PARAMETER = "userstore_name";
    String USERSTORE_DOMAIN_PARAMETER = "userstore_domain";
    String DOMAIN_PARAMETER = "domain";
    String APPLICATION_PARAMETER = "application";
    String TOKEN_TYPE_PARAMETER = "token_type";
    String NAME_PARAMETER = "name";
    String ALLOWED_ORIGINS_PARAMETER = "allowed_origins";

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
    Response authenticateApplication(
        @Header("apiKey") ApiKey apiKey,
        @Part(NAME_PARAMETER) String applicationName,
        @Part(DOMAIN_PARAMETER) String domain,
        @Part(TOKEN_TYPE_PARAMETER) String tokenType
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
    Response authenticateUser(
        @Header("apiKey") ApiKey apiKey,
        @Part("application_name") String applicationName,
        @Part("application_domain") String applicationDomain,
        @Part(TOKEN_TYPE_PARAMETER) String tokenType
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
    Response authenticateUser(
        @Header("apiKey") ApiKey apiKey,
        @Part("application_name") String applicationName,
        @Part("application_domain") String applicationDomain,
        @Part("token_type") String tokenType,
        @Part("user_store_name") String userStore,
        @Part("user_store_domain") String storeDomain
    ) throws HodErrorException;

    @POST("/2/authenticate/developer")
    @Multipart
    Response authenticateDeveloper(
        @Header("apiKey") ApiKey apiKey,
        @Part("tenant") String tenant,
        @Part("email") String email
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
    Response authenticateUnbound(
        @Header("apiKey") ApiKey apiKey,
        @Part(TOKEN_TYPE_PARAMETER) String tokenType
    ) throws HodErrorException;

    @GET(COMBINED_PATH)
    Response authenticateCombinedGet(
        @Header("cmb_sso_tkn") AuthenticationToken<EntityType.CombinedSso, TokenType.Simple> combinedSsoToken,
        @Header("token") String signature,
        @Header("origin") String origin,
        @Query(ALLOWED_ORIGINS_PARAMETER) String allowedOrigin,
        @Query(NONCE_PARAMETER) String nonce
    ) throws HodErrorException;

    @POST(COMBINED_PATH)
    @Multipart
    Response authenticateCombined(
        @Header("cmb_sso_tkn") AuthenticationToken<EntityType.CombinedSso, TokenType.Simple> combinedSsoToken,
        @Header("token") String signature,
        @Header("origin") String origin,
        @Query(ALLOWED_ORIGINS_PARAMETER) String allowedOrigin,
        @Part(NONCE_PARAMETER) String nonce,
        @Part(DOMAIN_PARAMETER) String domain,
        @Part(APPLICATION_PARAMETER) String name,
        @Part(USERSTORE_DOMAIN_PARAMETER) String userstoreDomain,
        @Part(USERSTORE_NAME_PARAMETER) String userstoreName,
        @Part(TOKEN_TYPE_PARAMETER) String tokenType
    ) throws HodErrorException;

    @GET(GET_TOKEN_INFORMATION_PATH)
    Response getTokenInformation(
        @Header("token") String token
    ) throws HodErrorException;

}
