/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.util.TypedByteArrayWithFilename;
import org.apache.commons.io.IOUtils;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class QueryTextIndexServiceImpl<T> implements QueryTextIndexService<T> {

    private final QueryTextIndexBackend queryTextIndexBackend;
    private final Class<T> clazz;
    private final Requester requester;

    public QueryTextIndexServiceImpl(final HodServiceConfig config, final Class<T> clazz) {
        queryTextIndexBackend = config.getRestAdapter().create(QueryTextIndexBackend.class);
        requester = config.getRequester();
        this.clazz = clazz;
    }

    @Override
    public T queryTextIndexWithText(final String text, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(clazz, getTextBackendCaller(text, params));
    }

    @Override
    public T queryTextIndexWithText(final TokenProxy tokenProxy, final String text, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, clazz, getTextBackendCaller(text, params));
    }

    @Override
    public T queryTextIndexWithReference(final String reference, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(clazz, getReferenceBackendCaller(reference, params));
    }

    @Override
    public T queryTextIndexWithReference(final TokenProxy tokenProxy, final String reference, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, clazz, getReferenceBackendCaller(reference, params));
    }

    @Override
    public T queryTextIndexWithUrl(final String url, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(clazz, getUrlBackendCaller(url, params));
    }

    @Override
    public T queryTextIndexWithUrl(final TokenProxy tokenProxy, final String url, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, clazz, getUrlBackendCaller(url, params));
    }

    @Override
    public T queryTextIndexWithFile(final File file, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(clazz, getFileBackendCaller(file, params));
    }

    @Override
    public T queryTextIndexWithFile(final TokenProxy tokenProxy, final File file, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, clazz, getFileBackendCaller(file, params));
    }

    @Override
    public T queryTextIndexWithFile(final byte[] bytes, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(clazz, getByteArrayBackendCaller(bytes, params));
    }

    @Override
    public T queryTextIndexWithFile(final TokenProxy tokenProxy, final byte[] bytes, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, clazz, getByteArrayBackendCaller(bytes, params));
    }

    @Override
    public T queryTextIndexWithFile(final InputStream inputStream, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(clazz, getInputStreamBackendCaller(inputStream, params));
    }

    @Override
    public T queryTextIndexWithFile(final TokenProxy tokenProxy, final InputStream inputStream, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, clazz, getInputStreamBackendCaller(inputStream, params));
    }

    private Requester.BackendCaller getTextBackendCaller(final String text, final QueryRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return queryTextIndexBackend.queryTextIndexWithText(authenticationToken, text, params.build());
            }
        };
    }

    private Requester.BackendCaller getReferenceBackendCaller(final String reference, final QueryRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return queryTextIndexBackend.queryTextIndexWithReference(authenticationToken, reference, params.build());
            }
        };
    }

    private Requester.BackendCaller getUrlBackendCaller(final String url, final QueryRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return queryTextIndexBackend.queryTextIndexWithUrl(authenticationToken, url, params.build());
            }
        };
    }

    private Requester.BackendCaller getFileBackendCaller(final File file, final QueryRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return queryTextIndexBackend.queryTextIndexWithFile(authenticationToken, new TypedFile("text/plain", file), params.build());
            }
        };
    }

    private Requester.BackendCaller getByteArrayBackendCaller(final byte[] file, final QueryRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return queryTextIndexBackend.queryTextIndexWithFile(authenticationToken, new TypedByteArrayWithFilename("text/plain", file), params.build());
            }
        };
    }

    private Requester.BackendCaller getInputStreamBackendCaller(final InputStream file, final QueryRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                try {
                    return queryTextIndexBackend.queryTextIndexWithFile(authenticationToken, new TypedByteArrayWithFilename("text/plain", IOUtils.toByteArray(file)), params.build());
                } catch (final IOException e) {
                    throw new RuntimeException("Error reading bytes from stream", e);
                }
            }
        };
    }
}
