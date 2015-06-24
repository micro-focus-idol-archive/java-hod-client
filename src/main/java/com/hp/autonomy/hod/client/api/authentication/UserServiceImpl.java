/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import retrofit.client.Response;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserBackend userBackend;
    private final Requester requester;

    public UserServiceImpl(final HodServiceConfig hodServiceConfig) {
        userBackend = hodServiceConfig.getRestAdapter().create(UserBackend.class);
        requester = hodServiceConfig.getRequester();
    }

    @Override
    public List<User> getUser(final AuthenticationToken userUnboundToken) throws HodErrorException {
        return userBackend.getUser(userUnboundToken).getUsers();
    }

    @Override
    public List<User> getUser(final TokenProxy tokenProxy) throws HodErrorException {
        return requester.makeRequest(tokenProxy, GetUserResponse.class, new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return userBackend.getUserCombined(authenticationToken);
            }
        }).getUsers();
    }
}
