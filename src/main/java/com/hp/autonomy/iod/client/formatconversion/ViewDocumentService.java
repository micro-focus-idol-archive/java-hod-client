/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.formatconversion;

import com.hp.autonomy.iod.client.error.IodErrorException;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import retrofit.http.Streaming;
import retrofit.mime.TypedInput;

import java.util.Map;

/**
 * Interface representing the ViewDocument API.
 */
public interface ViewDocumentService {

    String URL = "/api/sync/viewdocument/v1";

    /**
     * Convert a file to HTML and retrieve the result as a stream containing the HTML
     * @param apiKey The API key to use to authenticate the request
     * @param file The file to view
     * @param params Additional parameters to use for the request
     * @return A response whose InputStream contains the HTML of the document. Use response.getBody().in() to access the
     * html. This stream must be closed after use.
     * @throws IodErrorException
     */
    @POST(URL)
    @Multipart
    @Streaming
    Response viewFile(
            @Part("apiKey") String apiKey,
            @Part("file") TypedInput file,
            @PartMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Convert an object store object to HTML and retrieve the result as a stream containing the HTML
     * @param apiKey The API key to use to authenticate the request
     * @param reference The object store reference to view
     * @param params Additional parameters to use for the request
     * @return A response whose InputStream contains the HTML of the document. Use response.getBody().in() to access the
     * html. This stream must be closed after use.
     * @throws IodErrorException
     */
    @GET(URL)
    @Streaming
    Response viewReference(
            @Query("apiKey") String apiKey,
            @Query("reference") String reference,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Convert publicly accessible url to HTML and retrieve the result as a stream containing the HTML
     * @param apiKey The API key to use to authenticate the request
     * @param url The url to view
     * @param params Additional parameters to use for the request
     * @return A response whose InputStream contains the HTML of the document. Use response.getBody().in() to access the
     * html. This stream must be closed after use.
     * @throws IodErrorException
     */
    @GET(URL)
    @Streaming
    Response viewUrl(
            @Query("apiKey") String apiKey,
            @Query("url") String url,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Convert a file to HTML and retrieve the result as an HTML String. When using this method the
     * raw_html parameter MUST be set to false
     * @param apiKey The API key to use to authenticate the request
     * @param file The file to view
     * @param params Additional parameters to use for the request
     * @return A response with a String containing the HTML
     * @throws IodErrorException
     */
    @POST(URL)
    @Multipart
    ViewDocumentResponse viewFileAsHtmlString(
            @Part("apiKey") String apiKey,
            @Part("file") TypedInput file,
            @PartMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Convert a reference to HTML and retrieve the result as an HTML String.
     * @param apiKey The API key to use to authenticate the request
     * @param reference The reference to view
     * @param params Additional parameters to use for the request
     * @return A response with a String containing the HTML
     * @throws IodErrorException
     */
    @GET(URL + "?raw_html=false")
    ViewDocumentResponse viewReferenceAsHtmlString(
            @Query("apiKey") String apiKey,
            @Query("reference") String reference,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Convert a file to HTML and retrieve the result as an HTML String.
     * @param apiKey The API key to use to authenticate the request
     * @param url The url to view
     * @param params Additional parameters to use for the request
     * @return A response with a String containing the HTML
     * @throws IodErrorException
     */
    @GET(URL + "?raw_html=false")
    ViewDocumentResponse viewUrlAsHtmlString(
            @Query("apiKey") String apiKey,
            @Query("url") String url,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;

}
