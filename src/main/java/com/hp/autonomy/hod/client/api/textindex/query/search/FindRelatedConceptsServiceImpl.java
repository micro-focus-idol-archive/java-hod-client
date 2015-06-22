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
import java.util.List;

public class FindRelatedConceptsServiceImpl implements FindRelatedConceptsService {
    
    private static final Class<Entities> RESPONSE_CLASS = Entities.class;
    private static final String MIME_TYPE = "application/octet-stream";

    private final FindRelatedConceptsBackend findRelatedConceptsBackend;
    private final Requester requester;
    
    public FindRelatedConceptsServiceImpl(final HodServiceConfig hodServiceConfig) {
        findRelatedConceptsBackend = hodServiceConfig.getRestAdapter().create(FindRelatedConceptsBackend.class);
        requester = hodServiceConfig.getRequester();
    }
    
    @Override
    public List<Entity> findRelatedConceptsWithText(final String text, final FindRelatedConceptsRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getTextBackendCaller(text, params)).getEntities();
    }

    @Override
    public List<Entity> findRelatedConceptsWithText(final TokenProxy tokenProxy, final String text, final FindRelatedConceptsRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getTextBackendCaller(text, params)).getEntities();
    }

    @Override
    public List<Entity> findRelatedConceptsWithReference(final String reference, final FindRelatedConceptsRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getReferenceBackendCaller(reference, params)).getEntities();
    }

    @Override
    public List<Entity> findRelatedConceptsWithReference(final TokenProxy tokenProxy, final String reference, final FindRelatedConceptsRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getReferenceBackendCaller(reference, params)).getEntities();
    }

    @Override
    public List<Entity> findRelatedConceptsWithUrl(final String url, final FindRelatedConceptsRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getUrlBackendCaller(url, params)).getEntities();
    }

    @Override
    public List<Entity> findRelatedConceptsWithUrl(final TokenProxy tokenProxy, final String url, final FindRelatedConceptsRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getUrlBackendCaller(url, params)).getEntities();
    }

    @Override
    public List<Entity> findRelatedConceptsWithFile(final File file, final FindRelatedConceptsRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getFileBackendCaller(file, params)).getEntities();
    }

    @Override
    public List<Entity> findRelatedConceptsWithFile(final TokenProxy tokenProxy, final File file, final FindRelatedConceptsRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getFileBackendCaller(file, params)).getEntities();
    }

    @Override
    public List<Entity> findRelatedConceptsWithFile(final byte[] bytes, final FindRelatedConceptsRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getByteArrayBackendCaller(bytes, params)).getEntities();
    }

    @Override
    public List<Entity> findRelatedConceptsWithFile(final TokenProxy tokenProxy, final byte[] bytes, final FindRelatedConceptsRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getByteArrayBackendCaller(bytes, params)).getEntities();
    }

    @Override
    public List<Entity> findRelatedConceptsWithFile(final InputStream inputStream, final FindRelatedConceptsRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getInputStreamBackendCaller(inputStream, params)).getEntities();
    }

    @Override
    public List<Entity> findRelatedConceptsWithFile(final TokenProxy tokenProxy, final InputStream inputStream, final FindRelatedConceptsRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getInputStreamBackendCaller(inputStream, params)).getEntities();
    }

    private Requester.BackendCaller getTextBackendCaller(final String text, final FindRelatedConceptsRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return findRelatedConceptsBackend.findRelatedConceptsWithText(authenticationToken, text, params.build());
            }
        };
    }

    private Requester.BackendCaller getReferenceBackendCaller(final String reference, final FindRelatedConceptsRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return findRelatedConceptsBackend.findRelatedConceptsWithReference(authenticationToken, reference, params.build());
            }
        };
    }

    private Requester.BackendCaller getUrlBackendCaller(final String url, final FindRelatedConceptsRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return findRelatedConceptsBackend.findRelatedConceptsWithUrl(authenticationToken, url, params.build());
            }
        };
    }

    private Requester.BackendCaller getFileBackendCaller(final File file, final FindRelatedConceptsRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return findRelatedConceptsBackend.findRelatedConceptsWithFile(authenticationToken, new TypedFile(MIME_TYPE, file), params.build());
            }
        };
    }

    private Requester.BackendCaller getByteArrayBackendCaller(final byte[] bytes, final FindRelatedConceptsRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return findRelatedConceptsBackend.findRelatedConceptsWithFile(authenticationToken, new TypedByteArrayWithFilename(MIME_TYPE, bytes), params.build());
            }
        };
    }

    private Requester.BackendCaller getInputStreamBackendCaller(final InputStream inputStream, final FindRelatedConceptsRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                try {
                    return findRelatedConceptsBackend.findRelatedConceptsWithFile(authenticationToken, new TypedByteArrayWithFilename(MIME_TYPE, IOUtils.toByteArray(inputStream)), params.build());
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
