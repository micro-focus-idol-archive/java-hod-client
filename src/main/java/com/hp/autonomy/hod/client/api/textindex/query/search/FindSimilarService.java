/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.io.File;
import java.io.InputStream;

public interface FindSimilarService<T> {

    /**
     * Finds similar documents to the given text using a token proxy provided by a
     * {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param text The query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    T findSimilarDocumentsToText(
         String text,
         QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the given text using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param text The query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    T findSimilarDocumentsToText(
         TokenProxy tokenProxy,
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
     */
    T findSimilarDocumentsToReference(
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
     */
    T findSimilarDocumentsToReference(
         TokenProxy tokenProxy,
         String reference,
         QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text from a url using a token
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param url A publicly accessible HTTP URL from which the query text can be retrieved
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    T findSimilarDocumentsToUrl(
         String url,
         QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text from a url using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param url A publicly accessible HTTP URL from which the query text can be retrieved
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    T findSimilarDocumentsToUrl(
         TokenProxy tokenProxy,
         String url,
         QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text from a document in HP Haven OnDemand using a token
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param indexReference The reference of a document in HP Haven OnDemand
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    T findSimilarDocumentsToIndexReference(
         String indexReference,
         QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text from a url using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param indexReference The reference of a document in HP Haven OnDemand
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    T findSimilarDocumentsToIndexReference(
         TokenProxy tokenProxy,
         String indexReference,
         QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text in a file using a token
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param file A file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    T findSimilarDocumentsToFile(
         File file,
         QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text in a file using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param file A file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    T findSimilarDocumentsToFile(
         TokenProxy tokenProxy,
         File file,
         QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text in a file using a token
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param bytes The bytes of a file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    T findSimilarDocumentsToFile(
         byte[] bytes,
         QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text in a file using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param bytes The bytes of a file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    T findSimilarDocumentsToFile(
         TokenProxy tokenProxy,
         byte[] bytes,
         QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text in a file using a token
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param inputStream An InputStream representing a file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    T findSimilarDocumentsToFile(
         InputStream inputStream,
         QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Finds similar documents to the text in a file using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param inputStream An InputStream representing a file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that are similar to the query text
     */
    T findSimilarDocumentsToFile(
         TokenProxy tokenProxy,
         InputStream inputStream,
         QueryRequestBuilder params
    ) throws HodErrorException;

}
