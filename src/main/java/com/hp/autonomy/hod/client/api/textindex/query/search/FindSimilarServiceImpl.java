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

public class FindSimilarServiceImpl<T> implements FindSimilarService<T> {

    private static final String MIME_TYPE = "application/octet-stream";

    private final FindSimilarBackend findSimilarBackend;
    private final Requester requester;
    private final Class<T> returnType;

    public FindSimilarServiceImpl(final HodServiceConfig hodServiceConfig, final Class<T> returnType) {
        findSimilarBackend = hodServiceConfig.getRestAdapter().create(FindSimilarBackend.class);
        requester = hodServiceConfig.getRequester();
        this.returnType = returnType;
    }

    public static FindSimilarServiceImpl<Documents> documentsService(final HodServiceConfig hodServiceConfig) {
        return new FindSimilarServiceImpl<>(hodServiceConfig, Documents.class);
    }

    @Override
    public T findSimilarDocumentsToText(final String text, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(returnType, getTextBackendCaller(text, params));
    }

    @Override
    public T findSimilarDocumentsToText(final TokenProxy tokenProxy, final String text, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, getTextBackendCaller(text, params));
    }

    @Override
    public T findSimilarDocumentsToReference(final String reference, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(returnType, getReferenceBackendCaller(reference, params));
    }

    @Override
    public T findSimilarDocumentsToReference(final TokenProxy tokenProxy, final String reference, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, getReferenceBackendCaller(reference, params));
    }

    @Override
    public T findSimilarDocumentsToUrl(final String url, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(returnType, getUrlBackendCaller(url, params));
    }

    @Override
    public T findSimilarDocumentsToUrl(final TokenProxy tokenProxy, final String url, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, getUrlBackendCaller(url, params));
    }

    @Override
    public T findSimilarDocumentsToIndexReference(final String indexReference, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(returnType, getIndexReferenceBackendCaller(indexReference, params));
    }

    @Override
    public T findSimilarDocumentsToIndexReference(final TokenProxy tokenProxy, final String indexReference, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, getIndexReferenceBackendCaller(indexReference, params));
    }

    @Override
    public T findSimilarDocumentsToFile(final File file, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(returnType, getFileBackendCaller(file, params));
    }

    @Override
    public T findSimilarDocumentsToFile(final TokenProxy tokenProxy, final File file, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, getFileBackendCaller(file, params));
    }

    @Override
    public T findSimilarDocumentsToFile(final byte[] bytes, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(returnType, getByteArrayBackendCaller(bytes, params));
    }

    @Override
    public T findSimilarDocumentsToFile(final TokenProxy tokenProxy, final byte[] bytes, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, getByteArrayBackendCaller(bytes, params));
    }

    @Override
    public T findSimilarDocumentsToFile(final InputStream inputStream, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(returnType, getInputStreamBackendCaller(inputStream, params));
    }

    @Override
    public T findSimilarDocumentsToFile(final TokenProxy tokenProxy, final InputStream inputStream, final QueryRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, getInputStreamBackendCaller(inputStream, params));
    }

    private Requester.BackendCaller getTextBackendCaller(final String text, final QueryRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return findSimilarBackend.findSimilarDocumentsToText(authenticationToken, text, params.build());
            }
        };
    }

    private Requester.BackendCaller getReferenceBackendCaller(final String reference, final QueryRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return findSimilarBackend.findSimilarDocumentsToReference(authenticationToken, reference, params.build());
            }
        };
    }

    private Requester.BackendCaller getUrlBackendCaller(final String url, final QueryRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return findSimilarBackend.findSimilarDocumentsToUrl(authenticationToken, url, params.build());
            }
        };
    }

    private Requester.BackendCaller getIndexReferenceBackendCaller(final String indexReference, final QueryRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return findSimilarBackend.findSimilarDocumentsToIndexReference(authenticationToken, indexReference, params.build());
            }
        };
    }

    private Requester.BackendCaller getFileBackendCaller(final File file, final QueryRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return findSimilarBackend.findSimilarDocumentsToFile(authenticationToken, new TypedFile(MIME_TYPE, file), params.build());
            }
        };
    }

    private Requester.BackendCaller getByteArrayBackendCaller(final byte[] bytes, final QueryRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return findSimilarBackend.findSimilarDocumentsToFile(authenticationToken, new TypedByteArrayWithFilename(MIME_TYPE, bytes), params.build());
            }
        };
    }

    private Requester.BackendCaller getInputStreamBackendCaller(final InputStream inputStream, final QueryRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                try {
                    return findSimilarBackend.findSimilarDocumentsToFile(authenticationToken, new TypedByteArrayWithFilename(MIME_TYPE, IOUtils.toByteArray(inputStream)), params.build());
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
