/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.api.userstore.User;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.net.URL;
import java.util.List;

public interface UserStoreUsersService {

    /**
     * Get a list of the users in a user store, using a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource identifier of the user store
     * @return The users in the user store
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService is not configured
     */
    List<User> list(ResourceIdentifier userStore) throws HodErrorException;

    /**
     * Get a list of the users in a user store.
     * @param tokenProxy The token proxy to use for authentication
     * @param userStore The resource identifier of the user store
     * @return The users in the user store
     * @throws HodErrorException
     */
    List<User> list(TokenProxy tokenProxy, ResourceIdentifier userStore) throws HodErrorException;

    /**
     * Resets the authentication associated with the given user store and email address, using a
     * {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource identifier of the user store
     * @param email The email address associated with the authentication
     * @param onSuccess The URL to redirect the user to after the reset
     * @param onError The URL to redirect the user to if the reset fails
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService is not configured
     */
    void resetAuthentication(
        final ResourceIdentifier userStore,
        final String email,
        final URL onSuccess,
        final URL onError
    ) throws HodErrorException;

    /**
     * Resets the authentication associated with the given user store and email address, using the given {@link TokenProxy}
     * @param tokenProxy The token proxy to use to make the request
     * @param userStore The resource identifier of the user store
     * @param email The email address associated with the authentication
     * @param onSuccess The URL to redirect the user to after the reset
     * @param onError The URL to redirect the user to if the reset fails
     * @throws HodErrorException
     */
    void resetAuthentication(
        final TokenProxy tokenProxy,
        final ResourceIdentifier userStore,
        final String email,
        final URL onSuccess,
        final URL onError
    ) throws HodErrorException;

}
