/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.http.GET;
import retrofit.http.Header;
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
public interface FindSimilarBackend {

    String URL = "/2/api/sync/textindex/query/findsimilar/v1";

    /**
     * Finds similar documents to the given text using a token provided by a
     * {@link retrofit.RequestInterceptor}
     * @param text The query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    @GET(URL)
    Documents findSimilarDocumentsToText(
        @Query("text") String text,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the given text using the given token
     * @param token The token to use to authenticate the request
     * @param text The query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    @GET(URL)
    Documents findSimilarDocumentsToText(
        @Header("token") AuthenticationToken token,
        @Query("text") String text,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text from an object store object using a token
     * provided by a {@link retrofit.RequestInterceptor}
     * @param reference An HP Haven OnDemand reference obtained from either the Expand Container or Store Object API.
     * The contents of the object will be used as the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    @GET(URL)
    Documents findSimilarDocumentsToReference(
        @Query("reference") String reference,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text from an object store object using the given token
     * @param token The token to use to authenticate the request
     * @param reference An HP Haven OnDemand reference obtained from either the Expand Container or Store Object API.
     * The contents of the object will be used as the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    @GET(URL)
    Documents findSimilarDocumentsToReference(
        @Header("token") AuthenticationToken token,
        @Query("reference") String reference,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text from a url using a token
     * provided by a {@link retrofit.RequestInterceptor}
     * @param url A publicly accessible HTTP URL from which the query text can be retrieved
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    @GET(URL)
    Documents findSimilarDocumentsToUrl(
        @Query("url") String url,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text from a url using the given token
     * @param token The token to use to authenticate the request
     * @param url A publicly accessible HTTP URL from which the query text can be retrieved
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    @GET(URL)
    Documents findSimilarDocumentsToUrl(
        @Header("token") AuthenticationToken token,
        @Query("url") String url,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text from a document in HP Haven OnDemand using a token
     * provided by a {@link retrofit.RequestInterceptor}
     * @param indexReference The reference of a document in HP Haven OnDemand
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    @GET(URL)
    Documents findSimilarDocumentsToIndexReference(
        @Query("index_reference") String indexReference,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text from a url using the given token
     * @param token The token to use to authenticate the request
     * @param indexReference The reference of a document in HP Haven OnDemand
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    @GET(URL)
    Documents findSimilarDocumentsToIndexReference(
        @Header("token") AuthenticationToken token,
        @Query("index_reference") String indexReference,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text in a file using a token
     * provided by a {@link retrofit.RequestInterceptor}
     * @param file A file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    @Multipart
    @POST(URL)
    Documents findSimilarDocumentsToFile(
        @Part("file") TypedOutput file,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text in a file using the given token
     * @param token The token to use to authenticate the request
     * @param file A file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    @Multipart
    @POST(URL)
    Documents findSimilarDocumentsToFile(
        @Header("token") AuthenticationToken token,
        @Part("file") TypedOutput file,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;
}
