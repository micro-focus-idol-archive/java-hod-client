/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.analysis.sentiment;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedOutput;

/**
 * Interface representing the SentimentAnalysis API
 */
interface SentimentAnalysisBackend {

    String URL = "/2/api/sync/analysis/sentiment/v1";

    /**
     * Analyze the sentiment of the given text using the given token
     * @param token The token to use to authenticate the request
     * @param text The text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    @GET(URL)
    Response analyzeSentimentForText(
        @Header("token") AuthenticationToken<?, ?> token,
        @Query("text") String text,
        @Query("language") SentimentAnalysisLanguage language
    ) throws HodErrorException;

    /**
     * Analyze the sentiment of the given file using the given token
     * @param token The token to use to authenticate the request
     * @param file The file containing the text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    @POST(URL)
    @Multipart
    Response analyzeSentimentForFile(
        @Header("token") AuthenticationToken<?, ?> token,
        @Part("file") TypedOutput file,
        @Part("language") SentimentAnalysisLanguage language
    ) throws HodErrorException;

    /**
     * Analyze the sentiment of the given object store object using the given token
     * @param token The token to use to authenticate the request
     * @param reference The object store reference containing the text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    @GET(URL)
    Response analyzeSentimentForReference(
        @Header("token") AuthenticationToken<?, ?> token,
        @Query("reference") String reference,
        @Query("language") SentimentAnalysisLanguage language
    ) throws HodErrorException;

    /**
     * Analyze the sentiment of the given url using the given token
     * @param token The token to use to authenticate the request
     * @param url The object store reference containing the text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    @GET(URL)
    Response analyzeSentimentForUrl(
        @Header("token") AuthenticationToken<?, ?> token,
        @Query("url") String url,
        @Query("language") SentimentAnalysisLanguage language
    ) throws HodErrorException;

}
