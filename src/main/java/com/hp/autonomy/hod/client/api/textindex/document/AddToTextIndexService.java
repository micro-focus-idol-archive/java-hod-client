/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.document;

import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.HodJobCallback;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.io.File;
import java.io.InputStream;

/**
 * Service representing the AddToTextIndex API
 */
public interface AddToTextIndexService {
    /**
     * Index JSON documents into HP Haven OnDemand using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param documents A collection of objects to convert to JSON
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    void addJsonToTextIndex(
        Documents<?> documents,
        ResourceName index,
        AddToTextIndexRequestBuilder params,
        HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException;

    /**
     * Index JSON documents into HP Haven OnDemand using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param documents A collection of objects to convert to JSON
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    void addJsonToTextIndex(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        Documents<?> documents,
        ResourceName index,
        AddToTextIndexRequestBuilder params,
        HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException;

    /**
     * Index a public accessible url into HP Haven OnDemand using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param url A publicly accessible url containing the document content
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    void addUrlToTextIndex(
        String url,
        ResourceName index,
        AddToTextIndexRequestBuilder params,
        HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException;

    /**
     * Index a public accessible url into HP Haven OnDemand using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param url A publicly accessible url containing the document content
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    void addUrlToTextIndex(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String url,
        ResourceName index,
        AddToTextIndexRequestBuilder params,
        HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException;

    /**
     * Index an object store object into HP Haven OnDemand using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param reference An object store reference pointing at a file to be used for document content
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    void addReferenceToTextIndex(
        String reference,
        ResourceName index,
        AddToTextIndexRequestBuilder params,
        HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException;

    /**
     * Index an object store object into HP Haven OnDemand using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param reference An object store reference pointing at a file to be used for document content
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    void addReferenceToTextIndex(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String reference,
        ResourceName index,
        AddToTextIndexRequestBuilder params,
        HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException;

    /**
     * Index a file into HP Haven OnDemand using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param file A file containing the content of the document
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    void addFileToTextIndex(
        File file,
        ResourceName index,
        AddToTextIndexRequestBuilder params,
        HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException;

    /**
     * Index a file into HP Haven OnDemand using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param file A file containing the content of the document
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    void addFileToTextIndex(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        File file,
        ResourceName index,
        AddToTextIndexRequestBuilder params,
        HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException;

    /**
     * Index a file into HP Haven OnDemand using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param bytes The bytes of a file containing the content of the document
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    void addFileToTextIndex(
        byte[] bytes,
        ResourceName index,
        AddToTextIndexRequestBuilder params,
        HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException;

    /**
     * Index a file into HP Haven OnDemand using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param bytes The bytes of a file containing the content of the document
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    void addFileToTextIndex(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        byte[] bytes,
        ResourceName index,
        AddToTextIndexRequestBuilder params,
        HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException;

    /**
     * Index a file into HP Haven OnDemand using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param inputStream An InputStream representing a file containing the content of the document
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    void addFileToTextIndex(
        InputStream inputStream,
        ResourceName index,
        AddToTextIndexRequestBuilder params,
        HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException;

    /**
     * Index a file into HP Haven OnDemand using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param inputStream An InputStream representing a file containing the content of the document
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    void addFileToTextIndex(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        InputStream inputStream,
        ResourceName index,
        AddToTextIndexRequestBuilder params,
        HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException;
}
