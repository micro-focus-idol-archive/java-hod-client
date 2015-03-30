/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.textindexing;

import com.hp.autonomy.iod.client.error.IodErrorException;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

import java.util.Map;

/**
 * Interface representing the CreateTextIndex API.
 */
public interface CreateTextIndexService {

    String URL = "/api/sync/createtextindex/v1";

    /**
     * Create a text index using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param index The name of the index
     * @param flavor The flavor of the index
     * @param params Additional parameters to be sent as part of the request
     * @return A response with the index created and a message related to the attempt to create the index
     */
    @GET(URL)
    CreateTextIndexResponse createTextIndex(
            @Query("index") String index,
            @Query("flavor") String flavor,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Create a text index using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param index The name of the index
     * @param flavor The flavor of the index
     * @param params Additional parameters to be sent as part of the request
     * @return A response with the index created and a message related to the attempt to create the index
     */
    @GET(URL)
    CreateTextIndexResponse createTextIndex(
            @Query("apiKey") String apiKey,
            @Query("index") String index,
            @Query("flavor") String flavor,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;
}
