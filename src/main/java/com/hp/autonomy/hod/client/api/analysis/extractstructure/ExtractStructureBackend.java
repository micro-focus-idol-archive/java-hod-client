/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.analysis.extractstructure;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedOutput;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Interface representing the extract structure API.
 */
public interface ExtractStructureBackend {
    String URL = "/2/api/sync/analysis/extractcontent/v1";

    /**
     * Extract content from a CSV file into a JSON format using Micro Focus Haven on Demand using a token proxy
     * @param token The authentication token
     * @param file The bytes of a file containing the data to be extracted.
     * @return {@link List} of JSON objects stored as a {@link LinkedHashMap} with column names as keys and cell values as values.
     */
    @POST(URL)
    @Multipart
    Response extractFromFile(@Header("token") AuthenticationToken<?, ?> token, @Part("file") TypedOutput file) throws HodErrorException;

    /**
     * Extract content from a CSV file into a JSON format using Micro Focus Haven on Demand using a token proxy
     * @param token The authentication token
     * @param reference The object store reference containing the data to be extracted.
     * @return {@link List} of JSON objects stored as a {@link LinkedHashMap} with column names as keys and cell values as values.
     */
    @GET(URL)
    Response extractFromReference(@Header("token") AuthenticationToken<?, ?> token, @Query("reference") String reference) throws HodErrorException;


    /**
     * Extract content from a CSV file into a JSON format using Micro Focus Haven on Demand using a token proxy
     * @param token The authentication token
     * @param url The url pointing to a file containing the data to be extracted.
     * @return {@link List} of JSON objects stored as a {@link LinkedHashMap} with column names as keys and cell values as values.
     */
    @GET(URL)
    Response extractFromUrl(@Header("token") AuthenticationToken<?, ?> token, @Query("url") String url) throws HodErrorException;
}
