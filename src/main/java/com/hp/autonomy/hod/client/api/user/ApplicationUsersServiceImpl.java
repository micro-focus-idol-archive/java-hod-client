/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.user;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import retrofit.client.Response;

import java.util.List;

public class ApplicationUsersServiceImpl implements ApplicationUsersService {
    private final ApplicationUsersBackend backend;
    private final Requester requester;
    private final Requester.BackendCaller backendCaller;

    public ApplicationUsersServiceImpl(final HodServiceConfig config) {
        backend = config.getRestAdapter().create(ApplicationUsersBackend.class);
        requester = config.getRequester();
        backendCaller = new BackendCaller();
    }

    @Override
    public List<User> getUsers() throws HodErrorException {
        return requester.makeRequest(ApplicationUsersResponse.class, backendCaller).getUsers();
    }

    @Override
    public List<User> getUsers(final TokenProxy tokenProxy) throws HodErrorException {
        return requester.makeRequest(tokenProxy, ApplicationUsersResponse.class, backendCaller).getUsers();
    }

    private class BackendCaller implements Requester.BackendCaller {
        @Override
        public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
            return backend.getUsers(authenticationToken);
        }
    }
}
