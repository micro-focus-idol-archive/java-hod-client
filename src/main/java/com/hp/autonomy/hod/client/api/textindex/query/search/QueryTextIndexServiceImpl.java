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
 * Default implementation of QueryTextIndexService
 * <p/>
 * If you don't need a custom return type use the {@link #documentsService(HodServiceConfig)} static factory
 * @param <T> The desired return type of the methods of the service
 */
@SuppressWarnings("WeakerAccess")
public class QueryTextIndexServiceImpl<T extends Serializable> implements QueryTextIndexService<T> {

    private final QueryTextIndexBackend queryTextIndexBackend;
    private final JavaType returnType;
    private final Requester<?, TokenType.Simple> requester;

    /**
     * Creates a new QueryTextIndexServiceImpl with the given configuration and return type
     * @param config The configuration to use
     * @param documentType The desired type of documents returned by this service. This type must have to correct Jackson
     * annotations to read responses from HP Haven OnDemand
     */
    public QueryTextIndexServiceImpl(final HodServiceConfig<?, TokenType.Simple> config, final Class<T> documentType) {
        queryTextIndexBackend = config.getRestAdapter().create(QueryTextIndexBackend.class);
        requester = config.getRequester();
        returnType = config.getObjectMapper().getTypeFactory().constructParametrizedType(QueryResults.class, QueryResults.class, documentType);
    }

    /**
     * Creates a new QueryTextIndexServiceImpl of type {@link Document}
     * @param hodServiceConfig The configuration to use
     * @return A new {@literal QueryTextIndexServiceImpl<Document>}
     */
    public static QueryTextIndexServiceImpl<Document> documentsService(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig) {
        return new QueryTextIndexServiceImpl<>(hodServiceConfig, Document.class);
    }

    @Override
    public QueryResults<T> queryTextIndexWithText(final String text, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(returnType, getTextBackendCaller(text, params));
    }

    @Override
    public QueryResults<T> queryTextIndexWithText(final TokenProxy<?, TokenType.Simple> tokenProxy, final String text, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(tokenProxy, returnType, getTextBackendCaller(text, params));
    }

    @Override
    public QueryResults<T> queryTextIndexWithReference(final String reference, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(returnType, getReferenceBackendCaller(reference, params));
    }

    @Override
    public QueryResults<T> queryTextIndexWithReference(final TokenProxy<?, TokenType.Simple> tokenProxy, final String reference, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(tokenProxy, returnType, getReferenceBackendCaller(reference, params));
    }

    @Override
    public QueryResults<T> queryTextIndexWithUrl(final String url, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(returnType, getUrlBackendCaller(url, params));
    }

    @Override
    public QueryResults<T> queryTextIndexWithUrl(final TokenProxy<?, TokenType.Simple> tokenProxy, final String url, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(tokenProxy, returnType, getUrlBackendCaller(url, params));
    }

    @Override
    public QueryResults<T> queryTextIndexWithFile(final File file, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(returnType, getFileBackendCaller(file, params));
    }

    @Override
    public QueryResults<T> queryTextIndexWithFile(final TokenProxy<?, TokenType.Simple> tokenProxy, final File file, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(tokenProxy, returnType, getFileBackendCaller(file, params));
    }

    @Override
    public QueryResults<T> queryTextIndexWithFile(final byte[] bytes, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(returnType, getByteArrayBackendCaller(bytes, params));
    }

    @Override
    public QueryResults<T> queryTextIndexWithFile(final TokenProxy<?, TokenType.Simple> tokenProxy, final byte[] bytes, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(tokenProxy, returnType, getByteArrayBackendCaller(bytes, params));
    }

    @Override
    public QueryResults<T> queryTextIndexWithFile(final InputStream inputStream, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(returnType, getInputStreamBackendCaller(inputStream, params));
    }

    @Override
    public QueryResults<T> queryTextIndexWithFile(final TokenProxy<?, TokenType.Simple> tokenProxy, final InputStream inputStream, final QueryRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(tokenProxy, returnType, getInputStreamBackendCaller(inputStream, params));
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getTextBackendCaller(final String text, final QueryRequestBuilder params) {
        return authenticationToken -> queryTextIndexBackend.queryTextIndexWithText(authenticationToken, text, params.build());
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getReferenceBackendCaller(final String reference, final QueryRequestBuilder params) {
        return authenticationToken -> queryTextIndexBackend.queryTextIndexWithReference(authenticationToken, reference, params.build());
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getUrlBackendCaller(final String url, final QueryRequestBuilder params) {
        return authenticationToken -> queryTextIndexBackend.queryTextIndexWithUrl(authenticationToken, url, params.build());
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getFileBackendCaller(final File file, final QueryRequestBuilder params) {
        return authenticationToken -> queryTextIndexBackend.queryTextIndexWithFile(authenticationToken, new TypedFile("text/plain", file), params.build());
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getByteArrayBackendCaller(final byte[] file, final QueryRequestBuilder params) {
        return authenticationToken -> queryTextIndexBackend.queryTextIndexWithFile(authenticationToken, new TypedByteArrayWithFilename("text/plain", file), params.build());
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getInputStreamBackendCaller(final InputStream file, final QueryRequestBuilder params) {
        return authenticationToken -> {
            try {
                return queryTextIndexBackend.queryTextIndexWithFile(authenticationToken, new TypedByteArrayWithFilename("text/plain", IOUtils.toByteArray(file)), params.build());
            } catch (final IOException e) {
                throw new RuntimeException("Error reading bytes from stream", e);
            }
        };
    }
}
