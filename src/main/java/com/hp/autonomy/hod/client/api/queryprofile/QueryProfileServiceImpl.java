/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.resource.ListResourcesRequestBuilder;
import com.hp.autonomy.hod.client.api.resource.Resource;
import com.hp.autonomy.hod.client.api.resource.ResourceType;
import com.hp.autonomy.hod.client.api.resource.Resources;
import com.hp.autonomy.hod.client.api.resource.ResourcesService;
import com.hp.autonomy.hod.client.api.resource.ResourcesServiceImpl;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import retrofit.client.Response;

import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation of QueryProfileService
 */
public class QueryProfileServiceImpl implements QueryProfileService {

    private final QueryProfileBackend queryProfileBackend;
    private final Requester requester;

    private final ResourcesService resourcesService;
    private static final ListResourcesRequestBuilder LIST_RESOURCES_REQUEST_BUILDER = new ListResourcesRequestBuilder()
        .setTypes(ResourceType.of(ResourceType.QUERY_PROFILE));

    /**
     * Creates a new QueryProfileServiceImpl with the given configuration
     * @param hodServiceConfig The configuration to use
     */
    public QueryProfileServiceImpl(final HodServiceConfig hodServiceConfig) {
        queryProfileBackend = hodServiceConfig.getRestAdapter().create(QueryProfileBackend.class);
        requester = hodServiceConfig.getRequester();

        resourcesService = new ResourcesServiceImpl(hodServiceConfig);
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
    @Deprecated
    public QueryProfiles listQueryProfiles() throws HodErrorException {
        return parseResources(resourcesService.list(LIST_RESOURCES_REQUEST_BUILDER));
    }

    @Override
    @Deprecated
    public QueryProfiles listQueryProfiles(final TokenProxy tokenProxy) throws HodErrorException {
        return parseResources(resourcesService.list(tokenProxy, LIST_RESOURCES_REQUEST_BUILDER));
    }

    private QueryProfiles parseResources(final Resources resources) {
        final Set<QueryProfileName> queryProfileNames = new HashSet<>();

        for (final Resource resource : resources.getResources()) {
            queryProfileNames.add(new QueryProfileName.Builder()
                .setName(resource.getResource())
                .setDateCreated(resource.getDateCreated())
                .build()
            );
        }

        // there probably won't be any of this, but you never know
        for (final Resource resource : resources.getPublicResources()) {
            queryProfileNames.add(new QueryProfileName.Builder()
                .setName(resource.getResource())
                .setDateCreated(resource.getDateCreated())
                .build()
            );
        }

        return new QueryProfiles.Builder().setQueryProfiles(queryProfileNames).build();
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

}
