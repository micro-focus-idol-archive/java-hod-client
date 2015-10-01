/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.api.userstore.StatusResponse;
import com.hp.autonomy.hod.client.api.userstore.User;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import retrofit.client.Response;

import java.net.URL;
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

    @Override
    public void resetAuthentication(final ResourceIdentifier userStore, final String email, final URL onSuccess, final URL onError) throws HodErrorException {
        requester.makeRequest(StatusResponse.class, getResetBackendCaller(userStore, email, onSuccess, onError));
    }

    @Override
    public void resetAuthentication(final TokenProxy tokenProxy, final ResourceIdentifier userStore, final String email, final URL onSuccess, final URL onError) throws HodErrorException {
        requester.makeRequest(tokenProxy, StatusResponse.class, getResetBackendCaller(userStore, email, onSuccess, onError));
    }

    private Requester.BackendCaller listBackendCaller(final ResourceIdentifier userStore, final boolean returnMetaData) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken token) throws HodErrorException {
                return backend.list(token, userStore, returnMetaData);
            }
        };
    }

    private Requester.BackendCaller getResetBackendCaller(final ResourceIdentifier userStore, final String email, final URL onSuccess, final URL onError) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return backend.resetAuthentication(authenticationToken, userStore, email, onSuccess.toString(), onError.toString());
            }
        };
    }
}
