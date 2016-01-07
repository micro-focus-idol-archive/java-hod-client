/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.types.requests.Documents;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Service representing the FindSimilarDocuments API
 * @param <T> The desired type of the documents returned by the service
 */
public interface FindSimilarService<T extends Serializable> {

    /**
     * Finds similar documents to the given text using a token proxy provided by a
     * {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param text The query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    Documents<T> findSimilarDocumentsToText(
        String text,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the given text using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param text The query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    Documents<T> findSimilarDocumentsToText(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String text,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text from an object store object using a token
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param reference An HP Haven OnDemand reference obtained from either the Expand Container or Store Object API.
     * The contents of the object will be used as the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    Documents<T> findSimilarDocumentsToReference(
        String reference,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text from an object store object using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param reference An HP Haven OnDemand reference obtained from either the Expand Container or Store Object API.
     * The contents of the object will be used as the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    Documents<T> findSimilarDocumentsToReference(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String reference,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text from a url using a token
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param url A publicly accessible HTTP URL from which the query text can be retrieved
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    Documents<T> findSimilarDocumentsToUrl(
        String url,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text from a url using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param url A publicly accessible HTTP URL from which the query text can be retrieved
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    Documents<T> findSimilarDocumentsToUrl(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String url,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text from a document in HP Haven OnDemand using a token
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param indexReference The reference of a document in HP Haven OnDemand
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    Documents<T> findSimilarDocumentsToIndexReference(
        String indexReference,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text from a url using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param indexReference The reference of a document in HP Haven OnDemand
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    Documents<T> findSimilarDocumentsToIndexReference(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String indexReference,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text in a file using a token
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param file A file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    Documents<T> findSimilarDocumentsToFile(
        File file,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text in a file using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param file A file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    Documents<T> findSimilarDocumentsToFile(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        File file,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text in a file using a token
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param bytes The bytes of a file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    Documents<T> findSimilarDocumentsToFile(
        byte[] bytes,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text in a file using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param bytes The bytes of a file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    Documents<T> findSimilarDocumentsToFile(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        byte[] bytes,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text in a file using a token
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param inputStream An InputStream representing a file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    Documents<T> findSimilarDocumentsToFile(
        InputStream inputStream,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text in a file using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param inputStream An InputStream representing a file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    Documents<T> findSimilarDocumentsToFile(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        InputStream inputStream,
        QueryRequestBuilder params
    ) throws HodErrorException;

}
