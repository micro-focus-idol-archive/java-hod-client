/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Interface representing the QueryTextIndex API
 * @param <T> The desired return type of the methods of the service
 */
@SuppressWarnings("unused")
public interface QueryTextIndexService<T extends Serializable> {

    /**
     * Query HP Haven OnDemand for documents matching query text using a token proxy provided by a
     * {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param text The query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryResults<T> queryTextIndexWithText(
        String text,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for documents matching query text using the given token proxy
     * @param tokenProxy The token proxy to use to authenticate the request
     * @param text The query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryResults<T> queryTextIndexWithText(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String text,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for documents using query text from an object store object using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param reference An HP Haven OnDemand reference obtained from either the Expand Container or Store Object API.
     * The contents of the object will be used as the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryResults<T> queryTextIndexWithReference(
        String reference,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for documents using query text from an object store object using the given token proxy
     * @param tokenProxy The token proxy to use to authenticate the request
     * @param reference An HP Haven OnDemand reference obtained from either the Expand Container or Store Object API.
     * The contents of the object will be used as the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryResults<T> queryTextIndexWithReference(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String reference,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for documents using query text from a url using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param url A publicly accessible HTTP URL from which the query text can be retrieved
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryResults<T> queryTextIndexWithUrl(
        String url,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for documents using query text from a url using the given token proxy
     * @param tokenProxy The token proxy to use to authenticate the request
     * @param url A publicly accessible HTTP URL from which the query text can be retrieved
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryResults<T> queryTextIndexWithUrl(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String url,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for documents using query text in a file using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param file A file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryResults<T> queryTextIndexWithFile(
        File file,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for documents using query text in a file using the given token proxy
     * @param tokenProxy The token proxy to use to authenticate the request
     * @param file A file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryResults<T> queryTextIndexWithFile(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        File file,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for documents using query text in a file using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param bytes The bytes of a file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryResults<T> queryTextIndexWithFile(
        byte[] bytes,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for documents using query text in a file using the given token proxy
     * @param tokenProxy proxy The token to use to authenticate the request
     * @param bytes The bytes of a file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryResults<T> queryTextIndexWithFile(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        byte[] bytes,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for documents using query text in a file using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param inputStream An InputStream representing a file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryResults<T> queryTextIndexWithFile(
        InputStream inputStream,
        QueryRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for documents using query text in a file using the given token proxy
     * @param tokenProxy proxy The token to use to authenticate the request
     * @param inputStream An InputStream representing a file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryResults<T> queryTextIndexWithFile(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        InputStream inputStream,
        QueryRequestBuilder params
    ) throws HodErrorException;

}
