/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.api.userstore.User;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import retrofit.client.Response;

import java.util.List;

public class UserStoreUsersServiceImpl implements UserStoreUsersService {
    private final Requester requester;
    private final UserStoreUsersBackend backend;

    public UserStoreUsersServiceImpl(final HodServiceConfig config) {
        requester = config.getRequester();
        backend = config.getRestAdapter().create(UserStoreUsersBackend.class);
    }

    @Override
    public List<User> list(final ResourceIdentifier userStore) throws HodErrorException {
        return requester.makeRequest(ListUsersResponse.class, listBackendCaller(userStore, false)).getUsers();
    }

    @Override
    public List<User> list(final TokenProxy tokenProxy, final ResourceIdentifier userStore) throws HodErrorException {
        return requester.makeRequest(tokenProxy, ListUsersResponse.class, listBackendCaller(userStore, false)).getUsers();
    }

    private Requester.BackendCaller listBackendCaller(final ResourceIdentifier userStore, final boolean returnMetaData) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken token) throws HodErrorException {
                return backend.list(token, userStore, returnMetaData);
            }
        };
    }
}
