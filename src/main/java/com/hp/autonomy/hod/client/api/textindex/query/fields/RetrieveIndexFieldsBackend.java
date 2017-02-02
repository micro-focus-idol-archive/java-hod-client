/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.fields;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;
import retrofit.http.QueryMap;

import java.util.Collection;
import java.util.Map;

interface RetrieveIndexFieldsBackend {
    String URL = "/2/api/sync/textindex/query/fields/v2";
    String COMBINE_FIELDS_TRUE = "?combine_fields=true";

    /**
     * Retrieve from HP Haven OnDemand a list of the fields across all specified indexes that have been ingested
     * into a given text index using the given token
     *
     * @param token  The token to use to authenticate the request
     * @param params Additional parameters to be sent as part of the request
     * @return A list of fields and their types
     */
    @GET(URL + COMBINE_FIELDS_TRUE)
    Response retrieveIndexFields(
            @Header("token") AuthenticationToken<?, ?> token,
            @Query("indexes") Collection<ResourceName> indexes,
            @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Retrieve from HP Haven OnDemand a list of the fields for each index that have been ingested
     * into a given text index using the given token
     *
     * @param token  The token to use to authenticate the request
     * @param params Additional parameters to be sent as part of the request
     * @return A list of fields and their types
     */
    @GET(URL)
    Response retrieveIndexFieldsByIndex(
            @Header("token") AuthenticationToken<?, ?> token,
            @Query("indexes") Collection<ResourceName> indexes,
            @QueryMap Map<String, Object> params
    ) throws HodErrorException;
}
