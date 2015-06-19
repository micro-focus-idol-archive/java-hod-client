/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.parametric;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import org.apache.commons.lang.StringUtils;
import retrofit.client.Response;

import java.util.List;

public class GetParametricValuesServiceImpl implements GetParametricValuesService {
    
    private static final Class<FieldNames> RESPONSE_CLASS = FieldNames.class;
    
    private final GetParametricValuesBackend getParametricValuesBackend;
    private final Requester requester;
    
    public GetParametricValuesServiceImpl(final HodServiceConfig hodServiceConfig) {
        getParametricValuesBackend = hodServiceConfig.getRestAdapter().create(GetParametricValuesBackend.class);
        requester = hodServiceConfig.getRequester();
    }
    
    @Override
    public FieldNames getParametricValues(final List<String> fieldNames, final List<String> indexes, final GetParametricValuesRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getBackendCaller(fieldNames, indexes, params));
    }

    @Override
    public FieldNames getParametricValues(final TokenProxy tokenProxy, final List<String> fieldNames, final List<String> indexes, final GetParametricValuesRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getBackendCaller(fieldNames, indexes, params));
    }

    private Requester.BackendCaller getBackendCaller(final List<String> fieldNames, final List<String> indexes, final GetParametricValuesRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return getParametricValuesBackend.getParametricValues(authenticationToken, StringUtils.join(fieldNames, ','), indexes, params.build());
            }
        };
    }
}
