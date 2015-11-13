/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.analysis.extractstructure;

import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Service for calling the ExtractStructure API
 */
public interface ExtractStructureService {
    /**
     * Extract content from a CSV file into a JSON format using HP Haven on Demand using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param bytes The bytes of a file containing the data to be extracted.
     * @return {@link List} of JSON objects stored as a {@link LinkedHashMap} with column names as keys and cell values as values.
     * @throws NullPointerException                                                           If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     *                                                                                        with the token proxy has expired
     */
    List<LinkedHashMap<String, String>> extractFromFile(byte[] bytes) throws HodErrorException;

    /**
     * Extract content from a CSV file into a JSON format using HP Haven on Demand using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param bytes The bytes of a file containing the data to be extracted.
     * @return {@link List} of JSON objects stored as a {@link LinkedHashMap} with column names as keys and cell values as values.
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     *                                                                                        with the token proxy has expired
     */
    List<LinkedHashMap<String, String>> extractFromFile(TokenProxy<?, TokenType.Simple> tokenProxy, byte[] bytes) throws HodErrorException;

    /**
     * Extract content from a CSV file into a JSON format using HP Haven on Demand using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param inputStream The stream of a file containing the data to be extracted.
     * @return {@link List} of JSON objects stored as a {@link LinkedHashMap} with column names as keys and cell values as values.
     * @throws NullPointerException                                                           If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     *                                                                                        with the token proxy has expired
     */
    List<LinkedHashMap<String, String>> extractFromFile(InputStream inputStream) throws HodErrorException;

    /**
     * Extract content from a CSV file into a JSON format using HP Haven on Demand using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param inputStream The stream of a file containing the data to be extracted.
     * @return {@link List} of JSON objects stored as a {@link LinkedHashMap} with column names as keys and cell values as values.
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     *                                                                                        with the token proxy has expired
     */
    List<LinkedHashMap<String, String>> extractFromFile(TokenProxy<?, TokenType.Simple> tokenProxy, InputStream inputStream) throws HodErrorException;

    /**
     * Extract content from a CSV file into a JSON format using HP Haven on Demand using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param file The file containing the data to be extracted.
     * @return {@link List} of JSON objects stored as a {@link LinkedHashMap} with column names as keys and cell values as values.
     * @throws NullPointerException                                                           If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     *                                                                                        with the token proxy has expired
     */
    List<LinkedHashMap<String, String>> extractFromFile(File file) throws HodErrorException;

    /**
     * Extract content from a CSV file into a JSON format using HP Haven on Demand using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param file The file containing the data to be extracted.
     * @return {@link List} of JSON objects stored as a {@link LinkedHashMap} with column names as keys and cell values as values.
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     *                                                                                        with the token proxy has expired
     */
    List<LinkedHashMap<String, String>> extractFromFile(TokenProxy<?, TokenType.Simple> tokenProxy, File file) throws HodErrorException;

    /**
     * Extract content from a CSV file into a JSON format using HP Haven on Demand using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param reference The object store reference containing the data to be extracted.
     * @return {@link List} of JSON objects stored as a {@link LinkedHashMap} with column names as keys and cell values as values.
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     *                                                                                        with the token proxy has expired
     */
    List<LinkedHashMap<String, String>> extractFromReference(TokenProxy<?, TokenType.Simple> tokenProxy, String reference) throws HodErrorException;

    /**
     * Extract content from a CSV file into a JSON format using HP Haven on Demand using the given token proxy
     * @param reference The object store reference containing the data to be extracted.
     * @return {@link List} of JSON objects stored as a {@link LinkedHashMap} with column names as keys and cell values as values.
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     *                                                                                        with the token proxy has expired
     */
    List<LinkedHashMap<String, String>> extractFromReference(String reference) throws HodErrorException;

    /**
     * Extract content from a CSV file into a JSON format using HP Haven on Demand using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param url The url pointing to a file containing the data to be extracted.
     * @return {@link List} of JSON objects stored as a {@link LinkedHashMap} with column names as keys and cell values as values.
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     *                                                                                        with the token proxy has expired
     */
    List<LinkedHashMap<String, String>> extractFromUrl(TokenProxy<?, TokenType.Simple> tokenProxy, String url) throws HodErrorException;

    /**
     * Extract content from a CSV file into a JSON format using HP Haven on Demand using the given token proxy
     * @param url The url pointing to a file containing the data to be extracted.
     * @return {@link List} of JSON objects stored as a {@link LinkedHashMap} with column names as keys and cell values as values.
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     *                                                                                        with the token proxy has expired
     */
    List<LinkedHashMap<String, String>> extractFromUrl(String url) throws HodErrorException;
}
