/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.analysis.viewdocument;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import retrofit.http.Streaming;
import retrofit.mime.TypedOutput;

import java.util.Map;

/**
 * Interface representing the ViewDocument API.
 */
interface ViewDocumentBackend {

    String URL = "/2/api/sync/analysis/viewdocument/v1";

    /**
     * Convert a file to HTML and retrieve the result as a stream containing the HTML using the given token
     * @param token The token to use to authenticate the request
     * @param file The file to view
     * @param params Additional parameters to use for the request
     * @return A response whose InputStream contains the HTML of the document. Use response.getBody().in() to access the
     * html. This stream must be closed after use.
     * @throws HodErrorException
     */
    @POST(URL)
    @Multipart
    @Streaming
    Response viewFile(
        @Header("token") AuthenticationToken<?, ?> token,
        @Part("file") TypedOutput file,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Convert an object store object to HTML and retrieve the result as a stream containing the HTML using the given
     * token
     * @param token The token to use to authenticate the request
     * @param reference The object store reference to view
     * @param params Additional parameters to use for the request
     * @return A response whose InputStream contains the HTML of the document. Use response.getBody().in() to access the
     * html. This stream must be closed after use.
     * @throws HodErrorException
     */
    @GET(URL)
    @Streaming
    Response viewReference(
        @Header("token") AuthenticationToken<?, ?> token,
        @Query("reference") String reference,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Convert publicly accessible url to HTML and retrieve the result as a stream containing the HTML using the given
     * token
     * @param token The token to use to authenticate the request
     * @param url The url to view
     * @param params Additional parameters to use for the request
     * @return A response whose InputStream contains the HTML of the document. Use response.getBody().in() to access the
     * html. This stream must be closed after use.
     * @throws HodErrorException
     */
    @GET(URL)
    @Streaming
    Response viewUrl(
        @Header("token") AuthenticationToken<?, ?> token,
        @Query("url") String url,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

}
