/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.content;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.textindex.query.search.Documents;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import retrofit.client.Response;

import java.util.List;

/**
 * Default implementation of GetContentService
 * <p/>
 * If a custom return type is not required, use the {@link #documentsService(HodServiceConfig)} static factory method
 * @param <T> The desired return type of the services methods
 */
public class GetContentServiceImpl<T> implements GetContentService<T> {
    
    private final GetContentBackend getContentBackend;
    private final Requester requester;
    private final Class<T> returnType;

    /**
     * Create a new GetContentServiceImpl of the desired parameterized type
     * @param hodServiceConfig The configuration to use
     * @param returnType The desired return type for service methods. This type must have the correct Jackson annotations
     * to read HP Haven OnDemand responses
     */
    public GetContentServiceImpl(final HodServiceConfig hodServiceConfig, final Class<T> returnType) {
        getContentBackend = hodServiceConfig.getRestAdapter().create(GetContentBackend.class);
        requester = hodServiceConfig.getRequester();
        this.returnType = returnType;
    }

    /**
     * Create a new GetContentServiceImpl of type {@link Documents}
     * @param hodServiceConfig The configuration to use
     * @return A new {@literal GetContentService<Documents>}
     */
    public static GetContentServiceImpl<Documents> documentsService(final HodServiceConfig hodServiceConfig) {
        return new GetContentServiceImpl<>(hodServiceConfig, Documents.class);
    }
    
    @Override
    public T getContent(final List<String> indexReference, final String index, final GetContentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(returnType, getBackendCaller(indexReference, index, params));
    }

    @Override
    public T getContent(final TokenProxy tokenProxy, final List<String> indexReference, final String index, final GetContentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, getBackendCaller(indexReference, index, params));
    }

    private Requester.BackendCaller getBackendCaller(final List<String> indexReference, final String indexes, final GetContentRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return getContentBackend.getContent(authenticationToken, indexReference, indexes, params.build());
            }
        };
    }
}
