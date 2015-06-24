/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.fields;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import retrofit.client.Response;

/**
 * Default implementation of RetrieveIndexFieldsService
 */
public class RetrieveIndexFieldsServiceImpl implements RetrieveIndexFieldsService {

    private static final Class<RetrieveIndexFieldsResponse> RESPONSE_CLASS = RetrieveIndexFieldsResponse.class;

    private final RetrieveIndexFieldsBackend retrieveIndexFieldsBackend;
    private final Requester requester;

    /**
     * Creates a new RetrieveIndexFieldsServiceImpl with the given configuration
     * @param hodServiceConfig The configuration to use
     */
    public RetrieveIndexFieldsServiceImpl(final HodServiceConfig hodServiceConfig) {
        retrieveIndexFieldsBackend = hodServiceConfig.getRestAdapter().create(RetrieveIndexFieldsBackend.class);
        requester = hodServiceConfig.getRequester();
    }
    
    @Override
    public RetrieveIndexFieldsResponse retrieveIndexFields(final String index, final RetrieveIndexFieldsRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getBackendCaller(index, params));
    }

    @Override
    public RetrieveIndexFieldsResponse retrieveIndexFields(final TokenProxy tokenProxy, final String index, final RetrieveIndexFieldsRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getBackendCaller(index, params));
    }

    private Requester.BackendCaller getBackendCaller(final String index, final RetrieveIndexFieldsRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return retrieveIndexFieldsBackend.retrieveIndexFields(authenticationToken, index, params.build());
            }
        };
    }
}
