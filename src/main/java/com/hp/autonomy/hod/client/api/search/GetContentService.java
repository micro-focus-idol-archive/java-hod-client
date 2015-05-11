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

import java.util.List;
import java.util.Map;

/**
 * Interface representing the QueryTextIndex API.
 */
public interface GetContentService {

    String URL = "/2/api/sync/textindex/query/getcontent/v1";

    /**
     * Query HP Haven OnDemand for documents matching query text using a token provided by a {@link retrofit.RequestInterceptor}
     * @param indexReference The reference list of the documents you want to view
     * @param indexes The index the document resides in
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @GET(URL)
    Documents getContent(
            @Query("index_reference") List<String> indexReference,
            @Query("indexes") String indexes,
            @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for documents matching query text using the given token
     * @param token The token to use to authenticate the request
     * @param indexReference The reference list of the documents you want to view
     * @param indexes The index the document resides in
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @GET(URL)
    Documents getContent(
            @Header("token") AuthenticationToken token,
            @Query("index_reference") List<String> indexReference,
            @Query("indexes") String indexes,
            @QueryMap Map<String, Object> params
    ) throws HodErrorException;
}
