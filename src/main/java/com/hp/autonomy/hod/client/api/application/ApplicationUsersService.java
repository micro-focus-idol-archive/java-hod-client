/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.application;

import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.util.List;

/**
 * Service for getting information about which users can authenticate with an application.
 */
public interface ApplicationUsersService {

    /**
     * Get a list of users able to authenticate with the application associated with the token proxy obtained from a
     * {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @return The users associated with the application
     * @throws HodErrorException
     */
    List<User> getUsers() throws HodErrorException;

    /**
     * Get a list of users able to authenticate with the application associated with the given token proxy.
     * @return The users associated with the application
     * @throws HodErrorException
     */
    List<User> getUsers(TokenProxy<?, TokenType.Simple> tokenProxy) throws HodErrorException;

}
