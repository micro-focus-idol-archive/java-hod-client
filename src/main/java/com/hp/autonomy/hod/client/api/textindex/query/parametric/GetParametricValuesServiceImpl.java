/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.parametric;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import org.apache.commons.lang.StringUtils;
import retrofit.client.Response;

import java.util.Collection;

/**
 * Default implementation of GetParametricValuesService
 */
public class GetParametricValuesServiceImpl implements GetParametricValuesService {
    
    private static final Class<FieldNames> RESPONSE_CLASS = FieldNames.class;
    
    private final GetParametricValuesBackend getParametricValuesBackend;
    private final Requester<?, TokenType.Simple> requester;

    /**
     * Creates a new GetParametricValuesServiceImpl with the given configuration
     * @param hodServiceConfig The configuration to use
     */
    public GetParametricValuesServiceImpl(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig) {
        getParametricValuesBackend = hodServiceConfig.getRestAdapter().create(GetParametricValuesBackend.class);
        requester = hodServiceConfig.getRequester();
    }
    
    @Override
    public FieldNames getParametricValues(final Collection<String> fieldNames, final Collection<ResourceIdentifier> indexes, final GetParametricValuesRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getBackendCaller(fieldNames, indexes, params));
    }

    @Override
    public FieldNames getParametricValues(final TokenProxy<?, TokenType.Simple> tokenProxy, final Collection<String> fieldNames, final Collection<ResourceIdentifier> indexes, final GetParametricValuesRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getBackendCaller(fieldNames, indexes, params));
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getBackendCaller(final Collection<String> fieldNames, final Collection<ResourceIdentifier> indexes, final GetParametricValuesRequestBuilder params) {
        return authenticationToken -> getParametricValuesBackend.getParametricValues(authenticationToken, StringUtils.join(fieldNames, ','), indexes, params.build());
    }
}
