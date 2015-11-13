/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.developer;

import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.util.StatusResponse;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;

interface ApplicationBackend {
    String APPLICATION_PATH = "/2/application";

    String APP_AUTH_MODE_PART = "app_auth_mode";
    String USER_AUTH_MODE_PART = "user_auth_mode";
    String RETURN_TOKEN_TYPE_PART = "return_token_type";

    @GET(APPLICATION_PATH)
    ListApplicationsResponse list(
        @Header("token") String signature
    ) throws HodErrorException;

    @POST(APPLICATION_PATH)
    @Multipart
    StatusResponse create(
        @Header("token") String signature,
        @Part("domain_name") String domain,
        @Part("application_name") String name,
        @Part("description") String description
    ) throws HodErrorException;

    @DELETE("/2/domain/{domain_name}/application/{application_name}")
    StatusResponse delete(
        @Header("token") String signature,
        @Path("domain_name") String domain,
        @Path("application_name") String name
    ) throws HodErrorException;

    @GET("/2/domain/{domain_name}/application/{application_name}/authentication")
    ListAuthenticationsResponse listAuthentications(
        @Header("token") String signature,
        @Path("domain_name") String domain,
        @Path("application_name") String name
    ) throws HodErrorException;

    @POST("/2/domain/{domain_name}/application/{application_name}/authentication")
    AddApplicationAuthenticationResponse addAuthentication(
        @Header("token") String signature,
        @Path("domain_name") String domain,
        @Path("application_name") String name
    ) throws HodErrorException;

    @POST("/2/domain/{domain_name}/application/{application_name}/authentication_mode")
    @Multipart
    StatusResponse addAuthMode(
        @Header("token") String signature,
        @Path("domain_name") String domain,
        @Path("application_name") String name,
        @Part(APP_AUTH_MODE_PART) String applicationMode,
        @Part(USER_AUTH_MODE_PART) String userMode,
        @Part(RETURN_TOKEN_TYPE_PART) String returnTokenType
    ) throws HodErrorException;
}
