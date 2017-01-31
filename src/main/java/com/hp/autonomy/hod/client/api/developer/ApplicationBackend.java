/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.developer;

import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.util.StatusResponse;
import retrofit.http.*;

import java.util.Map;

interface ApplicationBackend {
    @GET("/2/api/sync/domain/{domain_name}/application/v1")
    ListApplicationsResponse list(
            @Header("token") String signature,
            @Path("domain_name") String domain
    ) throws HodErrorException;

    @POST("/2/api/sync/domain/{domain_name}/application/v1")
    @Multipart
    StatusResponse create(
            @Header("token") String signature,
            @Path("domain_name") String domain,
            @Part("application_name") String name,
            @Part("description") String description
    ) throws HodErrorException;

    @DELETE("/2/api/sync/domain/{domain_name}/application/{application_name}/v1")
    StatusResponse delete(
            @Header("token") String signature,
            @Path("domain_name") String domain,
            @Path("application_name") String name
    ) throws HodErrorException;

    @PATCH("/2/api/sync/domain/{domain_name}/application/{application_name}/v1")
    @Multipart
    StatusResponse update(
            @Header("token") String signature,
            @Path("domain_name") String domain,
            @Path("application_name") String name,
            @PartMap Map<String, String> parts
    ) throws HodErrorException;

    @GET("/2/api/sync/domain/{domain_name}/application/{application_name}/authentication/v1")
    ListAuthenticationsResponse listAuthentications(
            @Header("token") String signature,
            @Path("domain_name") String domain,
            @Path("application_name") String name
    ) throws HodErrorException;

    @POST("/2/api/sync/domain/{domain_name}/application/{application_name}/authentication/v1")
    AddApplicationAuthenticationResponse addAuthentication(
            @Header("token") String signature,
            @Path("domain_name") String domain,
            @Path("application_name") String name
    ) throws HodErrorException;
}
