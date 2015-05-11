/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.search;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;
import retrofit.http.QueryMap;

import java.util.Map;

public interface RetrieveIndexFieldsService {
    String URL = "/2/api/sync/textindex/query/fields/v1";

    /**
     * Retrieve from HP Haven OnDemand a list of the fields that have been ingested
     * into a given text index using a token provided by a {@link retrofit.RequestInterceptor}
     * @param params Parameters to be sent as part of the request
     * @return A list of fields and their types
     */
    @GET(URL)
    RetrieveIndexFieldsResponse retrieveIndexFields(
            @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Retrieve from HP Haven OnDemand a list of the fields that have been ingested
     * into a given text index using the given token
     * @param token The token to use to authenticate the request
     * @param params Additional parameters to be sent as part of the request
     * @return A list of fields and their types
     */
    @GET(URL)
    RetrieveIndexFieldsResponse retrieveIndexFields(
            @Header("token") AuthenticationToken token,
            @QueryMap Map<String, Object> params
    ) throws HodErrorException;
}
