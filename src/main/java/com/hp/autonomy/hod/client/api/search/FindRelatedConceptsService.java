/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.search;

import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import retrofit.mime.TypedOutput;

import java.util.Map;

public interface FindRelatedConceptsService {
    String URL = "/api/sync/findrelatedconcepts/v1";

    /**
     * Query HP Haven OnDemand for documents matching query text using an API key provided by a
     * {@link retrofit.RequestInterceptor}
     * @param text The query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @GET(URL)
    Entities findRelatedConceptsWithText(
            @Query("text") String text,
            @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for documents matching query text using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param text The query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @GET(URL)
    Entities findRelatedConceptsWithText(
            @Query("apiKey") String apiKey,
            @Query("text") String text,
            @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for documents using query text from an object store object using an API key provided by a
     * {@link retrofit.RequestInterceptor}
     * @param reference An IDOL OnDemand reference obtained from either the Expand Container or Store Object API.
     * The contents of the object will be used as the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @GET(URL)
    Entities findRelatedConceptsWithReference(
            @Query("reference") String reference,
            @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for documents using query text from an object store object using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param reference An HP Haven OnDemand reference obtained from either the Expand Container or Store Object API.
     * The contents of the object will be used as the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @GET(URL)
    Entities findRelatedConceptsWithReference(
            @Query("apiKey") String apiKey,
            @Query("reference") String reference,
            @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for documents using query text from a url using an API key provided by a
     * {@link retrofit.RequestInterceptor}
     * @param url A publicly accessible HTTP URL from which the query text can be retrieved
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @GET(URL)
    Entities findRelatedConceptsWithUrl(
            @Query("url") String url,
            @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for documents using query text from a url using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param url A publicly accessible HTTP URL from which the query text can be retrieved
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @GET(URL)
    Entities findRelatedConceptsWithUrl(
            @Query("apiKey") String apiKey,
            @Query("url") String url,
            @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for documents using query text in a file
     * @param apiKey The API key to use to authenticate the request
     * @param file A file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @Multipart
    @POST(URL)
    Entities findRelatedConceptsWithFile(
            @Part("apiKey") String apiKey,
            @Part("file") TypedOutput file,
            @PartMap Map<String, Object> params
    ) throws HodErrorException;
}
