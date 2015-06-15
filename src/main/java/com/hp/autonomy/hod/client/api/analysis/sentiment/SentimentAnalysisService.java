/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.analysis.sentiment;

import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.io.File;
import java.io.InputStream;

public interface SentimentAnalysisService {

    /**
     * Analyze the sentiment of the given text using a token proxy provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param text The text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    SentimentAnalysisResponse analyzeSentimentForText(
         String text,
         SentimentAnalysisLanguage language
    ) throws HodErrorException;

    /**
     * Analyze the sentiment of the given text using the given token proxy
     * @param tokenProxy The token proxy to use to authenticate the request
     * @param text The text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    SentimentAnalysisResponse analyzeSentimentForText(
         TokenProxy tokenProxy,
         String text,
         SentimentAnalysisLanguage language
    ) throws HodErrorException;

    /**
     * Analyze the sentiment of the given file using a token proxy provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param file The file containing the text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    SentimentAnalysisResponse analyzeSentimentForFile(
         File file,
         SentimentAnalysisLanguage language
    ) throws HodErrorException;

    /**
     * Analyze the sentiment of the given file using the given token proxy
     * @param tokenProxy The token proxy to use to authenticate the request
     * @param file The file containing the text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    SentimentAnalysisResponse analyzeSentimentForFile(
         TokenProxy tokenProxy,
         File file,
         SentimentAnalysisLanguage language
    ) throws HodErrorException;

    /**
     * Analyze the sentiment of the given file using a token proxy provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param bytes The bytes of a file containing the text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    SentimentAnalysisResponse analyzeSentimentForFile(
         byte[] bytes,
         SentimentAnalysisLanguage language
    ) throws HodErrorException;

    /**
     * Analyze the sentiment of the given file using the given token proxy
     * @param tokenProxy The token proxy to use to authenticate the request
     * @param bytes The bytes of a file containing the text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    SentimentAnalysisResponse analyzeSentimentForFile(
         TokenProxy tokenProxy,
         byte[] bytes,
         SentimentAnalysisLanguage language
    ) throws HodErrorException;

    /**
     * Analyze the sentiment of the given file using a token proxy provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param inputStream An InputStream representing a file containing the text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    SentimentAnalysisResponse analyzeSentimentForFile(
         InputStream inputStream,
         SentimentAnalysisLanguage language
    ) throws HodErrorException;

    /**
     * Analyze the sentiment of the given file using the given token proxy
     * @param tokenProxy The token proxy to use to authenticate the request
     * @param inputStream An InputStream representing a file containing the text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    SentimentAnalysisResponse analyzeSentimentForFile(
         TokenProxy tokenProxy,
         InputStream inputStream,
         SentimentAnalysisLanguage language
    ) throws HodErrorException;

    /**
     * Analyze the sentiment of the given object store object using a token proxy provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param reference The object store reference containing the text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    SentimentAnalysisResponse analyzeSentimentForReference(
         String reference,
         SentimentAnalysisLanguage language
    ) throws HodErrorException;

    /**
     * Analyze the sentiment of the given object store object using the given token proxy
     * @param tokenProxy The token proxy to use to authenticate the request
     * @param reference The object store reference containing the text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    SentimentAnalysisResponse analyzeSentimentForReference(
         TokenProxy tokenProxy,
         String reference,
         SentimentAnalysisLanguage language
    ) throws HodErrorException;

    /**
     * Analyze the sentiment of the given url using a token proxy provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param url The object store reference containing the text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    SentimentAnalysisResponse analyzeSentimentForUrl(
         String url,
         SentimentAnalysisLanguage language
    ) throws HodErrorException;

    /**
     * Analyze the sentiment of the given url using the given token proxy
     * @param tokenProxy The token proxy to use to authenticate the request
     * @param url The object store reference containing the text to analyze
     * @param language The language of the text
     * @return The sentiment of the response
     */
    SentimentAnalysisResponse analyzeSentimentForUrl(
         TokenProxy tokenProxy,
         String url,
         SentimentAnalysisLanguage language
    ) throws HodErrorException;

}
