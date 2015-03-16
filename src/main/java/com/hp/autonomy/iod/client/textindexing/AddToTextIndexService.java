/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.textindexing;

import com.hp.autonomy.iod.client.job.JobId;
import com.hp.autonomy.iod.client.error.IodErrorException;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.mime.TypedInput;

import java.util.Map;

/**
 * Interface representing the Add to Text Index API
 */
public interface AddToTextIndexService {

    String URL = "/async/addtotextindex/v1";

    /**
     * Index JSON documents into IDOL OnDemand
     * @param apiKey The API key to use to authenticate the request
     * @param documents A collection of objects to convert to JSON
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @return The job ID of the request
     * @throws IodErrorException
     */
    @POST(URL)
    @Multipart
    JobId addJsonToTextIndex(
            @Part("apiKey") String apiKey,
            @Part("json") Documents<?> documents,
            @Part("index") String index,
            @PartMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Index JSON documents into IDOL OnDemand
     * @param apiKey The API key to use to authenticate the request
     * @param file A file containing the content of the document
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @return The job ID of the request
     * @throws IodErrorException
     */
    @POST(URL)
    @Multipart
    JobId addFileToTextIndex(
            @Part("apiKey") String apiKey,
            @Part("file") TypedInput file,
            @Part("index") String index,
            @PartMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Index JSON documents into IDOL OnDemand
     * @param apiKey The API key to use to authenticate the request
     * @param reference An object store reference pointing at a file to be used for document content
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @return The job ID of the request
     * @throws IodErrorException
     */
    @POST(URL)
    @Multipart
    JobId addReferenceToTextIndex(
            @Part("apiKey") String apiKey,
            @Part("reference") String reference,
            @Part("index") String index,
            @PartMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Index JSON documents into IDOL OnDemand
     * @param apiKey The API key to use to authenticate the request
     * @param url A publicly accessible url containing the document content
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @return The job ID of the request
     * @throws IodErrorException
     */
    @POST(URL)
    @Multipart
    JobId addUrlToTextIndex(
            @Part("apiKey") String apiKey,
            @Part("url") String url,
            @Part("index") String index,
            @PartMap Map<String, Object> params
    ) throws IodErrorException;

}
