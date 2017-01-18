/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.content;

import com.fasterxml.jackson.databind.JavaType;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.api.textindex.query.search.Document;
import com.hp.autonomy.hod.client.api.textindex.query.search.QueryResults;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.io.Serializable;
import java.util.List;

/**
 * Default implementation of GetContentService
 * <p/>
 * If a custom return type is not required, use the {@link #documentsService(HodServiceConfig)} static factory method
 * @param <T> The desired return type of the services methods
 */
@SuppressWarnings("WeakerAccess")
public class GetContentServiceImpl<T extends Serializable> implements GetContentService<T> {
    
    private final GetContentBackend getContentBackend;
    private final Requester<?, TokenType.Simple> requester;
    private final JavaType returnType;

    /**
     * Create a new GetContentServiceImpl of the desired parameterized type
     * @param config The configuration to use
     * @param documentType The desired document type for service methods. This type must have the correct Jackson annotations
     * to read HP Haven OnDemand responses
     */
    public GetContentServiceImpl(final HodServiceConfig<?, TokenType.Simple> config, final Class<T> documentType) {
        getContentBackend = config.getRestAdapter().create(GetContentBackend.class);
        requester = config.getRequester();
        returnType = config.getObjectMapper().getTypeFactory().constructParametrizedType(QueryResults.class, QueryResults.class, documentType);
    }

    /**
     * Create a new GetContentServiceImpl of type {@link QueryResults}
     * @param hodServiceConfig The configuration to use
     * @return A new {@literal GetContentService<Document>}
     */
    public static GetContentServiceImpl<Document> documentsService(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig) {
        return new GetContentServiceImpl<>(hodServiceConfig, Document.class);
    }
    
    @Override
    public QueryResults<T> getContent(final List<String> indexReference, final ResourceIdentifier index, final GetContentRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(returnType, getBackendCaller(indexReference, index, params));
    }

    @Override
    public QueryResults<T> getContent(final TokenProxy<?, TokenType.Simple> tokenProxy, final List<String> indexReference, final ResourceIdentifier index, final GetContentRequestBuilder params) throws HodErrorException {
        return requester.unsafeMakeRequest(tokenProxy, returnType, getBackendCaller(indexReference, index, params));
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getBackendCaller(final List<String> indexReference, final ResourceIdentifier indexes, final GetContentRequestBuilder params) {
        return authenticationToken -> getContentBackend.getContent(authenticationToken, indexReference, indexes, params.build());
    }
}
