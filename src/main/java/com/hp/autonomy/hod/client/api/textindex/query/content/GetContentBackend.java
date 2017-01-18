/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.content;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;
import retrofit.http.QueryMap;

import java.util.List;
import java.util.Map;

/**
 * Interface representing the QueryTextIndex API.
 */
interface GetContentBackend {

    String URL = "/2/api/sync/textindex/query/content/v1";

    /**
     * Query HP Haven OnDemand for documents matching query text using the given token
     * @param token The token to use to authenticate the request
     * @param indexReference The reference list of the documents you want to retrieve
     * @param indexes The index the document resides in
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents with the given references
     */
    @GET(URL)
    Response getContent(
        @Header("token") AuthenticationToken<?, ?> token,
        @Query("index_reference") List<String> indexReference,
        @Query("indexes") ResourceName indexes,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;
}
