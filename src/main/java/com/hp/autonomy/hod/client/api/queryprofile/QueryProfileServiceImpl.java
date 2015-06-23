/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import retrofit.client.Response;

/**
 * Default implementation of QueryProfileService
 */
public class QueryProfileServiceImpl implements QueryProfileService {

    private final QueryProfileBackend queryProfileBackend;
    private final Requester requester;

    /**
     * Creates a new QueryProfileServiceImpl with the given configuration
     * @param hodServiceConfig The configuration to use
     */
    public QueryProfileServiceImpl(final HodServiceConfig hodServiceConfig) {
        queryProfileBackend = hodServiceConfig.getRestAdapter().create(QueryProfileBackend.class);
        requester = hodServiceConfig.getRequester();
    }

    @Override
    public QueryProfileStatusResponse createQueryProfile(final String name, final String queryManipulationIndex, final QueryProfileRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(QueryProfileStatusResponse.class, getCreateBackendCaller(name, queryManipulationIndex, params));
    }

    @Override
    public QueryProfileStatusResponse createQueryProfile(final TokenProxy tokenProxy, final String name, final String queryManipulationIndex, final QueryProfileRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, QueryProfileStatusResponse.class, getCreateBackendCaller(name, queryManipulationIndex, params));
    }

    @Override
    public QueryProfile retrieveQueryProfile(final String name) throws HodErrorException {
        return requester.makeRequest(QueryProfile.class, getRetrieveBackendCaller(name));
    }

    @Override
    public QueryProfile retrieveQueryProfile(final TokenProxy tokenProxy, final String name) throws HodErrorException {
        return requester.makeRequest(tokenProxy, QueryProfile.class, getRetrieveBackendCaller(name));
    }

    @Override
    public QueryProfileStatusResponse updateQueryProfile(final String name, final String queryManipulationIndex, final QueryProfileRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(QueryProfileStatusResponse.class, getUpdateBackendCaller(name, queryManipulationIndex, params));
    }

    @Override
    public QueryProfileStatusResponse updateQueryProfile(final TokenProxy tokenProxy, final String name, final String queryManipulationIndex, final QueryProfileRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, QueryProfileStatusResponse.class, getUpdateBackendCaller(name, queryManipulationIndex, params));
    }

    @Override
    public QueryProfileStatusResponse deleteQueryProfile(final String name) throws HodErrorException {
        return requester.makeRequest(QueryProfileStatusResponse.class, getDeleteBackendCaller(name));
    }

    @Override
    public QueryProfileStatusResponse deleteQueryProfile(final TokenProxy tokenProxy, final String name) throws HodErrorException {
        return requester.makeRequest(tokenProxy, QueryProfileStatusResponse.class, getDeleteBackendCaller(name));
    }

    @Override
    public QueryProfiles listQueryProfiles() throws HodErrorException {
        return requester.makeRequest(QueryProfiles.class, getListBackendCaller());
    }

    @Override
    public QueryProfiles listQueryProfiles(final TokenProxy tokenProxy) throws HodErrorException {
        return requester.makeRequest(tokenProxy, QueryProfiles.class, getListBackendCaller());
    }

    private Requester.BackendCaller getCreateBackendCaller(final String name, final String queryManipulationIndex, final QueryProfileRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return queryProfileBackend.createQueryProfile(authenticationToken, name, queryManipulationIndex, params.build());
            }
        };
    }

    private Requester.BackendCaller getRetrieveBackendCaller(final String name) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return queryProfileBackend.retrieveQueryProfile(authenticationToken, name);
            }
        };
    }

    private Requester.BackendCaller getUpdateBackendCaller(final String name, final String queryManipulationIndex, final QueryProfileRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return queryProfileBackend.updateQueryProfile(authenticationToken, name, queryManipulationIndex, params.build());
            }
        };
    }

    private Requester.BackendCaller getDeleteBackendCaller(final String name) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return queryProfileBackend.deleteQueryProfile(authenticationToken, name);
            }
        };
    }

    private Requester.BackendCaller getListBackendCaller() {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return queryProfileBackend.listQueryProfiles(authenticationToken);
            }
        };
    }
}
