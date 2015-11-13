/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Path;

import java.util.Map;

interface QueryProfileBackend {

    String PROFILE_VARIABLE = "query_profile";
    String INDEX_PART = "query_manipulation_index";
    String URL = "/2/api/sync/queryprofile/{" + PROFILE_VARIABLE +"}/v1";
    String TOKEN_HEADER = "token";

    @POST(URL)
    @Multipart
    Response createQueryProfile(
        @Header(TOKEN_HEADER) AuthenticationToken<?, ?> token,
        @Path(PROFILE_VARIABLE) String name,
        @Part(INDEX_PART) String queryManipulationIndex,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    @GET(URL)
    Response retrieveQueryProfile(
        @Header(TOKEN_HEADER) AuthenticationToken<?, ?> token,
        @Path(PROFILE_VARIABLE) ResourceIdentifier queryProfile
    ) throws HodErrorException;

    @PUT(URL)
    @Multipart
    Response updateQueryProfile(
        @Header(TOKEN_HEADER) AuthenticationToken<?, ?> token,
        @Path(PROFILE_VARIABLE) ResourceIdentifier queryProfile,
        @Part(INDEX_PART) String queryManipulationIndex,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    @DELETE(URL)
    Response deleteQueryProfile(
        @Header(TOKEN_HEADER) AuthenticationToken<?, ?> token,
        @Path(PROFILE_VARIABLE) ResourceIdentifier queryProfile
    ) throws HodErrorException;

}
