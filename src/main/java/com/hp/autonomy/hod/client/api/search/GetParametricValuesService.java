/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.search;

import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

import java.util.Map;

public interface GetParametricValuesService {
    String URL = "/1/api/sync/getparametricvalues/v1";

    /**
     * Query parametric values for the fieldName using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param fieldName A comma-separated list of field names to return values for.
     * @param params Additional parameters to be sent as part of the request
     * @return A list of field names with their parametric values
     */
    @GET(URL)
    FieldNames getParametricValues(
            @Query("field_name") String fieldName,
            @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Get parametric values for the fieldName using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param fieldName A comma-separated list of field names to return values for.
     * @param params Additional parameters to be sent as part of the request
     * @return A list of field names with their parametric values
     */
    @GET(URL)
    FieldNames getParametricValues(
            @Query("apiKey") String apiKey,
            @Query("field_name") String fieldName,
            @QueryMap Map<String, Object> params
    ) throws HodErrorException;
}
