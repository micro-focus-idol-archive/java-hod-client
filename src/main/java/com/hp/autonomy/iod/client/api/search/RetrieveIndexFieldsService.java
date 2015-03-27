/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.error.IodErrorException;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

import java.util.Map;

public interface RetrieveIndexFieldsService {
    String URL = "/api/sync/retrieveindexfields/v1";

    /**
     * Retrieve from IDOL OnDemand a list of the fields that have been ingested
     * into a given text index using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param params Parameters to be sent as part of the request
     * @return A list of fields and their types
     */
    @GET(URL)
    RetrieveIndexFieldsResponse retrieveIndexFields(
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Retrieve from IDOL OnDemand a list of the fields that have been ingested
     * into a given text index using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param params Additional parameters to be sent as part of the request
     * @return A list of fields and their types
     */
    @GET(URL)
    RetrieveIndexFieldsResponse retrieveIndexFields(
            @Query("apiKey") String apiKey,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;
}
