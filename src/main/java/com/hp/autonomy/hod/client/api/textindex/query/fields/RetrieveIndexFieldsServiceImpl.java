/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.fields;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import retrofit.client.Response;

import java.util.Collection;
import java.util.Map;

/**
 * Default implementation of RetrieveIndexFieldsService
 */
public class RetrieveIndexFieldsServiceImpl implements RetrieveIndexFieldsService {

    private static final Class<RetrieveIndexFieldsResponse> RESPONSE_CLASS = RetrieveIndexFieldsResponse.class;
    private static final Class<RetrieveIndexFieldsByIndexResponseWrapper> RESPONSE_WRAPPER_CLASS = RetrieveIndexFieldsByIndexResponseWrapper.class;

    private final RetrieveIndexFieldsBackend retrieveIndexFieldsBackend;
    private final Requester<?, TokenType.Simple> requester;

    /**
     * Creates a new RetrieveIndexFieldsServiceImpl with the given configuration
     *
     * @param hodServiceConfig The configuration to use
     */
    public RetrieveIndexFieldsServiceImpl(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig) {
        retrieveIndexFieldsBackend = hodServiceConfig.getRestAdapter().create(RetrieveIndexFieldsBackend.class);
        requester = hodServiceConfig.getRequester();
    }

    @Override
    public RetrieveIndexFieldsResponse retrieveIndexFields(final Collection<ResourceIdentifier> indexes, final RetrieveIndexFieldsRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getRetrieveIndexFieldsBackendCaller(indexes, params));
    }

    @Override
    public RetrieveIndexFieldsResponse retrieveIndexFields(final TokenProxy<?, TokenType.Simple> tokenProxy, final Collection<ResourceIdentifier> indexes, final RetrieveIndexFieldsRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getRetrieveIndexFieldsBackendCaller(indexes, params));
    }

    @Override
    public Map<String, RetrieveIndexFieldsResponse> retrieveIndexFieldsByIndex(final Collection<ResourceIdentifier> indexes, final RetrieveIndexFieldsRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(RESPONSE_WRAPPER_CLASS, getRetrieveIndexFieldsByIndexBackendCaller(indexes, params)).getResponseMap();
    }

    @Override
    public Map<String, RetrieveIndexFieldsResponse> retrieveIndexFieldsByIndex(final TokenProxy<?, TokenType.Simple> tokenProxy, final Collection<ResourceIdentifier> indexes, final RetrieveIndexFieldsRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_WRAPPER_CLASS, getRetrieveIndexFieldsByIndexBackendCaller(indexes, params)).getResponseMap();
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getRetrieveIndexFieldsBackendCaller(final Collection<ResourceIdentifier> indexes, final RetrieveIndexFieldsRequestBuilder params) {
        return authenticationToken -> retrieveIndexFieldsBackend.retrieveIndexFields(authenticationToken, indexes, params.build());
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getRetrieveIndexFieldsByIndexBackendCaller(final Collection<ResourceIdentifier> indexes, final RetrieveIndexFieldsRequestBuilder params) {
        return authenticationToken -> retrieveIndexFieldsBackend.retrieveIndexFieldsByIndex(authenticationToken, indexes, params.build());
    }
}
