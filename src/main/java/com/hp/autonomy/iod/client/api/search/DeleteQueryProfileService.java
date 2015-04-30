/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.error.IodErrorException;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Interface representing the DeleteQueryProfile API.
 */
public interface DeleteQueryProfileService {

    String URL = "/api/sync/deletequeryprofile/v1";

    /**
     * Delete an existing query profile in IDOL OnDemand using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param name The name of the query profile
     * @return The name of the deleted query profile
     */
    @GET(URL)
    CreateDeleteQueryProfileResponse deleteQueryProfile(
            @Query("query_profile") String name
    ) throws IodErrorException;

    /**
     * Delete an existing query profile in IDOL OnDemand using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param name The name of the query profile
     * @return The name of the deleted query profile
     */
    @GET(URL)
    CreateDeleteQueryProfileResponse deleteQueryProfile(
            @Query("apiKey") String apiKey,
            @Query("query_profile") String name
    ) throws IodErrorException;
}
