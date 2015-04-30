/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.error.IodErrorException;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import retrofit.mime.TypedOutput;

import java.util.Map;

/**
 * Interface representing the FindSimilar API. Use the same parameters as QueryTextIndex, so does not have its own
 * request builder
 */
public interface FindSimilarService {

    String URL = "/api/sync/findsimilar/v1";

    /**
     * Finds similar documents to the given text using an API key provided by a
     * {@link retrofit.RequestInterceptor}
     * @param text The query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    @GET(URL)
    Documents findSimilarDocumentsToText(
            @Query("text") String text,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Finds similar documents to the given text using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param text The query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    @GET(URL)
    Documents findSimilarDocumentsToText(
            @Query("apiKey") String apiKey,
            @Query("text") String text,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Finds similar documents to the text from an object store object using an API key
     * provided by a {@link retrofit.RequestInterceptor}
     * @param reference An IDOL OnDemand reference obtained from either the Expand Container or Store Object API.
     * The contents of the object will be used as the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    @GET(URL)
    Documents findSimilarDocumentsToReference(
            @Query("reference") String reference,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Finds similar documents to the text from an object store object using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param reference An IDOL OnDemand reference obtained from either the Expand Container or Store Object API.
     * The contents of the object will be used as the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    @GET(URL)
    Documents findSimilarDocumentsToReference(
            @Query("apiKey") String apiKey,
            @Query("reference") String reference,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Finds similar documents to the text from a url using an API key
     * provided by a {@link retrofit.RequestInterceptor}
     * @param url A publicly accessible HTTP URL from which the query text can be retrieved
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    @GET(URL)
    Documents findSimilarDocumentsToUrl(
            @Query("url") String url,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Finds similar documents to the text from a url using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param url A publicly accessible HTTP URL from which the query text can be retrieved
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    @GET(URL)
    Documents findSimilarDocumentsToUrl(
            @Query("apiKey") String apiKey,
            @Query("url") String url,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Finds similar documents to the text from a document in IDOL OnDemand using an API key
     * provided by a {@link retrofit.RequestInterceptor}
     * @param indexReference The reference of a document in IDOL OnDemand
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    @GET(URL)
    Documents findSimilarDocumentsToIndexReference(
            @Query("index_reference") String indexReference,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Finds similar documents to the text from a url using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param indexReference The reference of a document in IDOL OnDemand
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    @GET(URL)
    Documents findSimilarDocumentsToIndexReference(
            @Query("apiKey") String apiKey,
            @Query("index_reference") String indexReference,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Finds similar documents to the text in a file
     * @param apiKey The API key to use to authenticate the request
     * @param file A file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    @Multipart
    @POST(URL)
    Documents findSimilarDocumentsToFile(
            @Part("apiKey") String apiKey,
            @Part("file") TypedOutput file,
            @PartMap Map<String, Object> params
    ) throws IodErrorException;
}
