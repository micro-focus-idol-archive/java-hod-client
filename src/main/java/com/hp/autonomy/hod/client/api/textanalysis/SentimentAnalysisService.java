/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textanalysis;

import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedOutput;

/**
 * Interface representing the SentimentAnalysis API
 */
public interface SentimentAnalysisService {

    String URL = "/api/sync/analyzesentiment/v1";

    /**
     * Analyze the sentiment of the given text using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param text The text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    @GET(URL)
    SentimentAnalysisResponse analyzeSentimentForText(@Query("text") String text, @Query("language") SentimentAnalysisLanguage language);

    /**
     * Analyze the sentiment of the given text using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param text The text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    @GET(URL)
    SentimentAnalysisResponse analyzeSentimentForText(@Query("apiKey") String apiKey, @Query("text") String text, @Query("language") SentimentAnalysisLanguage language);

    /**
     * Analyze the sentiment of the given file using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param file The file containing the text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    @POST(URL)
    @Multipart
    SentimentAnalysisResponse analyzeSentimentForFile(@Part("file") TypedOutput file, @Part("language") SentimentAnalysisLanguage language);

    /**
     * Analyze the sentiment of the given file using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param file The file containing the text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    @POST(URL)
    @Multipart
    SentimentAnalysisResponse analyzeSentimentForFile(@Part("apiKey") String apiKey, @Part("file") TypedOutput file, @Part("language") SentimentAnalysisLanguage language);

    /**
     * Analyze the sentiment of the given object store object using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param reference The object store reference containing the text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    @GET(URL)
    SentimentAnalysisResponse analyzeSentimentForReference(@Query("reference") String reference, @Query("language") SentimentAnalysisLanguage language);

    /**
     * Analyze the sentiment of the given object store object using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param reference The object store reference containing the text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    @GET(URL)
    SentimentAnalysisResponse analyzeSentimentForReference(@Query("apiKey") String apiKey, @Query("reference") String reference, @Query("language") SentimentAnalysisLanguage language);

    /**
     * Analyze the sentiment of the given url using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param url The object store reference containing the text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    @GET(URL)
    SentimentAnalysisResponse analyzeSentimentForUrl(@Query("url") String url, @Query("language") SentimentAnalysisLanguage language);

    /**
     * Analyze the sentiment of the given url using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param url The object store reference containing the text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    @GET(URL)
    SentimentAnalysisResponse analyzeSentimentForUrl(@Query("apiKey") String apiKey, @Query("url") String url, @Query("language") SentimentAnalysisLanguage language);

}
