/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.util.List;

public interface UserService {

    /**
     * Get details for the user represented by the given user unbound token. This token should be the result of calling
     * {@link AuthenticationService#authenticateUserUnbound}
     * @param userUnboundToken The user unbound token
     * @return A list of users associated with the token
     * @throws HodErrorException
     */
    List<User> getUser(AuthenticationToken userUnboundToken) throws HodErrorException;

    /**
     * Get details for the user represented by the given token proxy. This token proxy should be the result of calling
     * {@link AuthenticationService#authenticateUser} or {@link AuthenticationService#combineTokens}
     * @param tokenProxy The token proxy associated with the user's token
     * @return A list of users associated with the token
     * @throws HodErrorException
     */
    List<User> getUser(TokenProxy tokenProxy) throws HodErrorException;

}
