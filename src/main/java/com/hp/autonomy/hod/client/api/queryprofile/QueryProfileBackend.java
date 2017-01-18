/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
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
    String URL = "/2/api/sync/queryprofile/{" + PROFILE_VARIABLE + '}';
    String V1 = "/v1";
    String V2 = "/v2";
    String TOKEN_HEADER = "token";

    @POST(URL + V2)
    @Multipart
    Response createQueryProfile(
        @Header(TOKEN_HEADER) AuthenticationToken<?, ?> token,
        @Path(PROFILE_VARIABLE) String name,
        @Part(INDEX_PART) String queryManipulationIndex,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    @GET(URL + V1)
    Response retrieveQueryProfile(
        @Header(TOKEN_HEADER) AuthenticationToken<?, ?> token,
        @Path(PROFILE_VARIABLE) ResourceName queryProfile
    ) throws HodErrorException;

    @PUT(URL + V1)
    @Multipart
    Response updateQueryProfile(
        @Header(TOKEN_HEADER) AuthenticationToken<?, ?> token,
        @Path(PROFILE_VARIABLE) ResourceName queryProfile,
        @Part(INDEX_PART) String queryManipulationIndex,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    @DELETE(URL + V1)
    Response deleteQueryProfile(
        @Header(TOKEN_HEADER) AuthenticationToken<?, ?> token,
        @Path(PROFILE_VARIABLE) ResourceName queryProfile
    ) throws HodErrorException;

}
