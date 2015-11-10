/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
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
    private final Requester<?, TokenType.Simple> requester;

    /**
     * Creates a new QueryProfileServiceImpl with the given configuration
     * @param hodServiceConfig The configuration to use
     */
    public QueryProfileServiceImpl(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig) {
        queryProfileBackend = hodServiceConfig.getRestAdapter().create(QueryProfileBackend.class);
        requester = hodServiceConfig.getRequester();
    }

    @Override
    public QueryProfileStatusResponse createQueryProfile(
        final String name,
        final String queryManipulationIndex,
        final QueryProfileRequestBuilder params
    ) throws HodErrorException {
        return requester.makeRequest(QueryProfileStatusResponse.class, getCreateBackendCaller(name, queryManipulationIndex, params));
    }

    @Override
    public QueryProfileStatusResponse createQueryProfile(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final String name,
        final String queryManipulationIndex,
        final QueryProfileRequestBuilder params
    ) throws HodErrorException {
        return requester.makeRequest(tokenProxy, QueryProfileStatusResponse.class, getCreateBackendCaller(name, queryManipulationIndex, params));
    }

    @Override
    public QueryProfile retrieveQueryProfile(final ResourceIdentifier queryProfile) throws HodErrorException {
        return requester.makeRequest(QueryProfile.class, getRetrieveBackendCaller(queryProfile));
    }

    @Override
    public QueryProfile retrieveQueryProfile(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceIdentifier queryProfile) throws HodErrorException {
        return requester.makeRequest(tokenProxy, QueryProfile.class, getRetrieveBackendCaller(queryProfile));
    }

    @Override
    public QueryProfileStatusResponse updateQueryProfile(
        final ResourceIdentifier queryProfile,
        final String queryManipulationIndex,
        final QueryProfileRequestBuilder params
    ) throws HodErrorException {
        return requester.makeRequest(QueryProfileStatusResponse.class, getUpdateBackendCaller(queryProfile, queryManipulationIndex, params));
    }

    @Override
    public QueryProfileStatusResponse updateQueryProfile(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final ResourceIdentifier queryProfile,
        final String queryManipulationIndex,
        final QueryProfileRequestBuilder params
    ) throws HodErrorException {
        return requester.makeRequest(tokenProxy, QueryProfileStatusResponse.class, getUpdateBackendCaller(queryProfile, queryManipulationIndex, params));
    }

    @Override
    public QueryProfileStatusResponse deleteQueryProfile(final ResourceIdentifier queryProfile) throws HodErrorException {
        return requester.makeRequest(QueryProfileStatusResponse.class, getDeleteBackendCaller(queryProfile));
    }

    @Override
    public QueryProfileStatusResponse deleteQueryProfile(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceIdentifier queryProfile) throws HodErrorException {
        return requester.makeRequest(tokenProxy, QueryProfileStatusResponse.class, getDeleteBackendCaller(queryProfile));
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getCreateBackendCaller(
        final String name,
        final String queryManipulationIndex,
        final QueryProfileRequestBuilder params
    ) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return queryProfileBackend.createQueryProfile(authenticationToken, name, queryManipulationIndex, params == null ? null : params.build());
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getRetrieveBackendCaller(final ResourceIdentifier queryProfile) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return queryProfileBackend.retrieveQueryProfile(authenticationToken, queryProfile);
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getUpdateBackendCaller(
        final ResourceIdentifier queryProfile,
        final String queryManipulationIndex,
        final QueryProfileRequestBuilder params
    ) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return queryProfileBackend.updateQueryProfile(authenticationToken, queryProfile, queryManipulationIndex, params == null ? null : params.build());
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getDeleteBackendCaller(final ResourceIdentifier queryProfile) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return queryProfileBackend.deleteQueryProfile(authenticationToken, queryProfile);
            }
        };
    }

}
