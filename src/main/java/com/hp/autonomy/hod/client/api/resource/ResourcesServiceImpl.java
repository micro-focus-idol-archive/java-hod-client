/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import retrofit.client.Response;

public class ResourcesServiceImpl implements ResourcesService {
    
    private final ResourcesBackend resourcesBackend;
    private final Requester requester;
    
    public ResourcesServiceImpl(final HodServiceConfig hodServiceConfig) {
        resourcesBackend = hodServiceConfig.getRestAdapter().create(ResourcesBackend.class);
        requester = hodServiceConfig.getRequester();
    }
    
    @Override
    public Resources list(final ListResourcesRequestBuilder parameters) throws HodErrorException {
        return requester.makeRequest(Resources.class, getBackendCaller(parameters));
    }

    @Override
    public Resources list(final TokenProxy tokenProxy, final ListResourcesRequestBuilder parameters) throws HodErrorException {
        return requester.makeRequest(tokenProxy, Resources.class, getBackendCaller(parameters));
    }

    private Requester.BackendCaller getBackendCaller(final ListResourcesRequestBuilder parameters) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return resourcesBackend.list(authenticationToken, parameters.build());
            }
        };
    }
}
