/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.fasterxml.jackson.databind.JavaType;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
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
import java.io.Serializable;

/**
 * Default implementation of FindSimilarService
 * <p/>
 * If you don't need a custom return type use the {@link #documentsService(HodServiceConfig)} static factory
 * @param <T> The desired return type of the service
 */
@SuppressWarnings("WeakerAccess")
public class FindSimilarServiceImpl<T extends Serializable> implements FindSimilarService<T> {

    private static final String MIME_TYPE = "application/octet-stream";

    private final FindSimilarBackend findSimilarBackend;
    private final Requester<?, TokenType.Simple> requester;
    private final JavaType returnType;

    /**
     * Creates a new FindSimilarServiceImpl with the given configuration and return type
     * @param hodServiceConfig The configuration to use
     * @param documentType The desired type of the documents returned by the service. This type must have the correct Jackson
     * annotations to read responses from HP Haven OnDemand
     */
    public FindSimilarServiceImpl(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig, final Class<T> documentType) {
        findSimilarBackend = hodServiceConfig.getRestAdapter().create(FindSimilarBackend.class);
        requester = hodServiceConfig.getRequester();
        returnType = hodServiceConfig.getObjectMapper().getTypeFactory().constructParametrizedType(QueryResults.class, QueryResults.class , documentType);
    }

    /**
     * Creates a new FindSimilarServiceImpl of type {@link QueryResults}
     * @param hodServiceConfig The configuration to use
     * @return The new service
     */
    public static FindSimilarServiceImpl<Document> documentsService(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig) {
        return new FindSimilarServiceImpl<>(hodServiceConfig, Document.class);
    }

    @Override
    public QueryResults<T> findSimilarDocumentsToText(final String text, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(returnType, getTextBackendCaller(text, params));
    }

    @Override
    public QueryResults<T> findSimilarDocumentsToText(final TokenProxy<?, TokenType.Simple> tokenProxy, final String text, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(tokenProxy, returnType, getTextBackendCaller(text, params));
    }

    @Override
    public QueryResults<T> findSimilarDocumentsToReference(final String reference, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(returnType, getReferenceBackendCaller(reference, params));
    }

    @Override
    public QueryResults<T> findSimilarDocumentsToReference(final TokenProxy<?, TokenType.Simple> tokenProxy, final String reference, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(tokenProxy, returnType, getReferenceBackendCaller(reference, params));
    }

    @Override
    public QueryResults<T> findSimilarDocumentsToUrl(final String url, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(returnType, getUrlBackendCaller(url, params));
    }

    @Override
    public QueryResults<T> findSimilarDocumentsToUrl(final TokenProxy<?, TokenType.Simple> tokenProxy, final String url, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(tokenProxy, returnType, getUrlBackendCaller(url, params));
    }

    @Override
    public QueryResults<T> findSimilarDocumentsToIndexReference(final String indexReference, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(returnType, getIndexReferenceBackendCaller(indexReference, params));
    }

    @Override
    public QueryResults<T> findSimilarDocumentsToIndexReference(final TokenProxy<?, TokenType.Simple> tokenProxy, final String indexReference, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(tokenProxy, returnType, getIndexReferenceBackendCaller(indexReference, params));
    }

    @Override
    public QueryResults<T> findSimilarDocumentsToFile(final File file, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(returnType, getFileBackendCaller(file, params));
    }

    @Override
    public QueryResults<T> findSimilarDocumentsToFile(final TokenProxy<?, TokenType.Simple> tokenProxy, final File file, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(tokenProxy, returnType, getFileBackendCaller(file, params));
    }

    @Override
    public QueryResults<T> findSimilarDocumentsToFile(final byte[] bytes, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(returnType, getByteArrayBackendCaller(bytes, params));
    }

    @Override
    public QueryResults<T> findSimilarDocumentsToFile(final TokenProxy<?, TokenType.Simple> tokenProxy, final byte[] bytes, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(tokenProxy, returnType, getByteArrayBackendCaller(bytes, params));
    }

    @Override
    public QueryResults<T> findSimilarDocumentsToFile(final InputStream inputStream, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(returnType, getInputStreamBackendCaller(inputStream, params));
    }

    @Override
    public QueryResults<T> findSimilarDocumentsToFile(final TokenProxy<?, TokenType.Simple> tokenProxy, final InputStream inputStream, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(tokenProxy, returnType, getInputStreamBackendCaller(inputStream, params));
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getTextBackendCaller(final String text, final QueryRequestBuilder params) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return findSimilarBackend.findSimilarDocumentsToText(authenticationToken, text, params.build());
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getReferenceBackendCaller(final String reference, final QueryRequestBuilder params) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return findSimilarBackend.findSimilarDocumentsToReference(authenticationToken, reference, params.build());
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getUrlBackendCaller(final String url, final QueryRequestBuilder params) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return findSimilarBackend.findSimilarDocumentsToUrl(authenticationToken, url, params.build());
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getIndexReferenceBackendCaller(final String indexReference, final QueryRequestBuilder params) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return findSimilarBackend.findSimilarDocumentsToIndexReference(authenticationToken, indexReference, params.build());
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getFileBackendCaller(final File file, final QueryRequestBuilder params) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return findSimilarBackend.findSimilarDocumentsToFile(authenticationToken, new TypedFile(MIME_TYPE, file), params.build());
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getByteArrayBackendCaller(final byte[] bytes, final QueryRequestBuilder params) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return findSimilarBackend.findSimilarDocumentsToFile(authenticationToken, new TypedByteArrayWithFilename(MIME_TYPE, bytes), params.build());
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getInputStreamBackendCaller(final InputStream inputStream, final QueryRequestBuilder params) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                try {
                    return findSimilarBackend.findSimilarDocumentsToFile(authenticationToken, new TypedByteArrayWithFilename(MIME_TYPE, IOUtils.toByteArray(inputStream)), params.build());
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
