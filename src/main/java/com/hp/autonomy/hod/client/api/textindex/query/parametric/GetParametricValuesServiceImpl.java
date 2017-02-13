/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.parametric;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.util.Collection;
import java.util.List;

/**
 * Default implementation of GetParametricValuesService
 */
public class GetParametricValuesServiceImpl implements GetParametricValuesService {
    private static final TypeReference<FieldsResponse<FieldValues>> RESPONSE_TYPE = new TypeReference<FieldsResponse<FieldValues>>() {};

    private final GetParametricValuesBackend getParametricValuesBackend;
    private final Requester<?, TokenType.Simple> requester;

    /**
     * Creates a new GetParametricValuesServiceImpl with the given configuration
     *
     * @param hodServiceConfig The configuration to use
     */
    public GetParametricValuesServiceImpl(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig) {
        getParametricValuesBackend = hodServiceConfig.getRestAdapter().create(GetParametricValuesBackend.class);
        requester = hodServiceConfig.getRequester();
    }

    @Override
    public List<FieldValues> getParametricValues(
            final Collection<String> fieldNames,
            final Collection<ResourceName> indexes,
            final GetParametricValuesRequestBuilder params
    ) throws HodErrorException {
        return requester.makeRequest(RESPONSE_TYPE, getBackendCaller(fieldNames, indexes, params)).getFields();
    }

    @Override
    public List<FieldValues> getParametricValues(
            final TokenProxy<?, TokenType.Simple> tokenProxy,
            final Collection<String> fieldNames,
            final Collection<ResourceName> indexes,
            final GetParametricValuesRequestBuilder params
    ) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_TYPE, getBackendCaller(fieldNames, indexes, params)).getFields();
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getBackendCaller(
            final Collection<String> fieldNames,
            final Collection<ResourceName> indexes,
            final GetParametricValuesRequestBuilder params
    ) {
        return authenticationToken -> getParametricValuesBackend.getParametricValues(authenticationToken, fieldNames, indexes, params.build());
    }
}
