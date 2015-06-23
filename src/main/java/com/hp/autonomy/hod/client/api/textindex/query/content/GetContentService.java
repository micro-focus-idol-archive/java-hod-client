/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.content;

import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.util.List;

/**
 * Service representing the GetContent API
 * @param <T> The desired return type of the API methods
 */
public interface GetContentService<T> {

    /**
     * Query HP Haven OnDemand for documents matching query text using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param indexReference The reference list of the documents you want to retrieve
     * @param index The index the document resides in
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents with the given references
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    T getContent(
        List<String> indexReference,
        String index,
        GetContentRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for documents matching query text using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param indexReference The reference list of the documents you want to retrieve
     * @param index The index the document resides in
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents with the given references
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    T getContent(
        TokenProxy tokenProxy,
        List<String> indexReference,
        String index,
        GetContentRequestBuilder params
    ) throws HodErrorException;

}
