/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
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

    String URL = "/2/api/sync/queryprofile/{queryProfileName}/v1";
    String LIST_URL = "/2/api/sync/listqueryprofiles/v1";

    /**
     * Create a query profile in HP Haven OnDemand for a specified query manipulation index using the given token
     * @param token The token to use to authenticate the request
     * @param name The name of the query profile
     * @param params Configuration parameters for the query profile
     * @return The name of the created query profile
     */
    @POST(URL)
    @Multipart
    Response createQueryProfile(
        @Header("token") AuthenticationToken token,
        @Path("queryProfileName") String name,
        @Part("query_manipulation_index") String queryManipulationIndex,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Retrieves a query profile using the given token
     * @param token The token to use to authenticate the request
     * @param name The name of the query profile
     * @return The query profile
     * @throws HodErrorException
     */
    @GET(URL)
    Response retrieveQueryProfile(
        @Header("token") AuthenticationToken token,
        @Path("queryProfileName") String name
    ) throws HodErrorException;

    /**
     * Update a query profile in HP Haven OnDemand for a specified query manipulation index using the given token
     * @param token The token to use to authenticate the request
     * @param name The name of the query profile
     * @param params Configuration parameters for the query profile
     * @return The name of the created query profile
     */
    @PUT(URL)
    @Multipart
    Response updateQueryProfile(
        @Header("token") AuthenticationToken token,
        @Path("queryProfileName") String name,
        @Part("query_manipulation_index") String queryManipulationIndex,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Delete an existing query profile in HP Haven OnDemand using the given token
     * @param token The API key to use to authenticate the request
     * @param name The name of the query profile
     * @return The name of the deleted query profile
     */
    @DELETE(URL)
    Response deleteQueryProfile(
        @Header("token") AuthenticationToken token,
        @Path("queryProfileName") String name
    ) throws HodErrorException;

    /**
     * Gets the names of the query profiles that currently exist using the given token
     * @param token  The token to use to access HP Haven OnDemand.
     * @return  A QueryProfiles response object, that has a util method on it for getting the actual names.
     * @throws HodErrorException
     */
    @GET(LIST_URL)
    Response listQueryProfiles(
        @Header("token") AuthenticationToken token
    ) throws HodErrorException;

}
