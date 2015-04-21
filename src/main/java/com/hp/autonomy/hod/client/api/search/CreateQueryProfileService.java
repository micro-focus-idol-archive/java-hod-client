/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.search;

import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Interface representing the CreateQueryProfile API.
 */
public interface CreateQueryProfileService {

    String URL = "/api/sync/createqueryprofile/v1";

    /**
     * Create a query profile in HP Haven OnDemand for a specified query manipulation index using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param name The name of the query profile
     * @param config The content of the query profile
     * @return The name of the created query profile
     */
    @POST(URL)
    @Multipart
    QueryProfileStatusResponse createQueryProfile(
            @Part("apiKey") String apiKey,
            @Part("query_profile") String name,
            @Part("config") QueryProfileConfig config
    ) throws HodErrorException;
}
