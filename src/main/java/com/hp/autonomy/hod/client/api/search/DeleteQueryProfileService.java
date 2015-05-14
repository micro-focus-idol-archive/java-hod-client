/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.search;

import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Interface representing the DeleteQueryProfile API.
 */
public interface DeleteQueryProfileService {

    String URL = "/1/api/sync/deletequeryprofile/v1";

    /**
     * Delete an existing query profile in HP Haven OnDemand using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param name The name of the query profile
     * @return The name of the deleted query profile
     */
    @GET(URL)
    QueryProfileStatusResponse deleteQueryProfile(
            @Query("query_profile") String name
    ) throws HodErrorException;

    /**
     * Delete an existing query profile in HP Haven OnDemand using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param name The name of the query profile
     * @return The name of the deleted query profile
     */
    @GET(URL)
    QueryProfileStatusResponse deleteQueryProfile(
            @Query("apiKey") String apiKey,
            @Query("query_profile") String name
    ) throws HodErrorException;
}
