/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.net.URL;
import java.util.List;
import java.util.UUID;

public interface UserStoreUsersService {

    /**
     * Get a list of the users in a user store, using a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource identifier of the user store
     * @param includeAccounts If true, accounts are listed for each user
     * @param includeGroups If true, groups are listed for each user
     * @return The users in the user store
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService is not configured
     */
    List<User<Void>> list(ResourceIdentifier userStore, boolean includeAccounts, boolean includeGroups) throws HodErrorException;

    /**
     * Get a list of the users in a user store.
     * @param tokenProxy The token proxy to use for authentication
     * @param userStore The resource identifier of the user store
     * @param includeAccounts If true, accounts are listed for each user
     * @param includeGroups If true, groups are listed for each user
     * @return The users in the user store
     * @throws HodErrorException
     */
    List<User<Void>> list(TokenProxy<?, TokenType.Simple> tokenProxy, ResourceIdentifier userStore, boolean includeAccounts, boolean includeGroups) throws HodErrorException;

    /**
     * Get a list of users and metadata in a user store, using a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource identifier of the user store
     * @param metadataType Class object representing the type of the user metadata
     * @param includeAccounts If true, accounts are listed for each user
     * @param includeGroups If true, groups are listed for each user
     * @param <T> The type of the user metadata
     * @return The users in the user store and their metadata
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService is not configured
     */
    <T> List<User<T>> listWithMetadata(ResourceIdentifier userStore, Class<T> metadataType, boolean includeAccounts, boolean includeGroups) throws HodErrorException;

    /**
     * Get a list of users and metadata in a user store, using a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param tokenProxy The token proxy to use for authentication
     * @param userStore The resource identifier of the user store
     * @param metadataType Class object representing the type of the user metadata
     * @param includeAccounts If true, accounts are listed for each user
     * @param includeGroups If true, groups are listed for each user
     * @param <T> The type of the user metadata
     * @return The users in the user store and their metadata
     * @throws HodErrorException
     */
    <T> List<User<T>> listWithMetaData(TokenProxy<?, TokenType.Simple> tokenProxy, ResourceIdentifier userStore, Class<T> metadataType, boolean includeAccounts, boolean includeGroups) throws HodErrorException;

    /**
     * Create a user in a userstore.
     * @param tokenProxy The token proxy to use for authentication
     * @param userStore The resource identifier of the user store
     * @param userEmail The email of the user to be created
     * @param onSuccess The URL to redirect the user to after the create
     * @param onError The URL to redirect the user to if the create fails
     * @param params Additional parameters to be sent as part of the request
     * @throws HodErrorException
     */
    void create(TokenProxy<?, TokenType.Simple> tokenProxy, ResourceIdentifier userStore, String userEmail, URL onSuccess, URL onError, CreateUserRequestBuilder params) throws HodErrorException;

    /**
     * Create a user in a userstore, using a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource identifier of the user store
     * @param userEmail The email of the user to be created
     * @param onSuccess The URL to redirect the user to after the create
     * @param onError The URL to redirect the user to if the create fails
     * @param params Additional parameters to be sent as part of the request
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService is not configured
     */
    void create(ResourceIdentifier userStore, String userEmail, URL onSuccess, URL onError, CreateUserRequestBuilder params) throws HodErrorException;

    /**
     * Delete a user with a uuid from a userstore.
     * @param tokenProxy The token proxy to use for authentication
     * @param userStore The resource identifier of the user store
     * @param userUuid The uuid of the user to be deleted
     * @throws HodErrorException
     */
    void delete(TokenProxy<?, TokenType.Simple> tokenProxy, ResourceIdentifier userStore, UUID userUuid) throws HodErrorException;

    /**
     * Delete a user with a uuid from a userstore, using a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource identifier of the user store
     * @param userUuid The uuid of the user to be deleted
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService is not configured
     */
    void delete(ResourceIdentifier userStore, UUID userUuid) throws HodErrorException;

    /**
     * Resets the authentication associated with the given user store and UUID, using a
     * {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource identifier of the user store
     * @param userUuid The UUID of the user
     * @param onSuccess The URL to redirect the user to after the reset
     * @param onError The URL to redirect the user to if the reset fails
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService is not configured
     */
    void resetAuthentication(
        ResourceIdentifier userStore,
        UUID userUuid,
        URL onSuccess,
        URL onError
    ) throws HodErrorException;

    /**
     * Resets the authentication associated with the given user store and UUID, using the given {@link TokenProxy}
     * @param tokenProxy The token proxy to use to make the request
     * @param userStore The resource identifier of the user store
     * @param userUuid The UUID of the user
     * @param onSuccess The URL to redirect the user to after the reset
     * @param onError The URL to redirect the user to if the reset fails
     * @throws HodErrorException
     */
    void resetAuthentication(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        ResourceIdentifier userStore,
        UUID userUuid,
        URL onSuccess,
        URL onError
    ) throws HodErrorException;

    /**
     * List the groups associated with the given user using a configured {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * for authentication.
     * @param userStore The user store of the user
     * @param userUuid The UUID of the user
     * @return
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService is not configured
     */
    UserGroups listUserGroups(
        ResourceIdentifier userStore,
        UUID userUuid
    ) throws HodErrorException;

    /**
     * List the groups associated with the given user using a TokenProxy.
     * @param tokenProxy The TokenProxy to use for authentication
     * @param userStore The user store of the user
     * @param userUuid The UUID of the user
     * @return
     * @throws HodErrorException
     */
    UserGroups listUserGroups(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        ResourceIdentifier userStore,
        UUID userUuid
    ) throws HodErrorException;

}
