/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.api.userstore.User;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.util.List;

public interface UserStoreUsersService {

    /**
     * Get a list of the users in a user store, using a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource identifier of the user store
     * @return The users in the user store
     */
    List<User> list(ResourceIdentifier userStore) throws HodErrorException;

    /**
     * Get a list of the users in a user store.
     * @param tokenProxy The token proxy to use for authentication
     * @param userStore The resource identifier of the user store
     * @return The users in the user store
     */
    List<User> list(TokenProxy tokenProxy, ResourceIdentifier userStore) throws HodErrorException;

}
