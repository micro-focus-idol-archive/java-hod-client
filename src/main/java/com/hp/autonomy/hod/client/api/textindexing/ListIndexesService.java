/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindexing;

import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

import java.util.Map;

/**
 * Interface representing the ListIndexes API.
 */
public interface ListIndexesService {

    String URL = "/1/api/sync/listindexes/v1";

    /**
     * Query HP Haven OnDemand for the list of indexes using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param params Parameters to be sent as part of the request
     * @return A list of available indexes
     */
    @GET(URL)
    Indexes listIndexes(
            @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for indexes using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param params Parameters to be sent as part of the request
     * @return A list of available indexes
     */
    @GET(URL)
    Indexes listIndexes(
            @Query("apiKey") String apiKey,
            @QueryMap Map<String, Object> params
    ) throws HodErrorException;

}
