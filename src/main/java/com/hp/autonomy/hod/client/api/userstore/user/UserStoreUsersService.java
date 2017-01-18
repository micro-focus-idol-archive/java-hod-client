/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Service for managing users in a Haven OnDemand user store.
 */
public interface UserStoreUsersService {

    /**
     * Get a list of the users in a user store, using a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource name of the user store
     * @param includeAccounts If true, accounts are listed for each user
     * @param includeGroups If true, groups are listed for each user
     * @return The users in the user store
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService is not configured
     */
    List<User> list(ResourceName userStore, boolean includeAccounts, boolean includeGroups) throws HodErrorException;

    /**
     * Get a list of the users in a user store.
     * @param tokenProxy The token proxy to use for authentication
     * @param userStore The resource name of the user store
     * @param includeAccounts If true, accounts are listed for each user
     * @param includeGroups If true, groups are listed for each user
     * @return The users in the user store
     * @throws HodErrorException
     */
    List<User> list(TokenProxy<?, TokenType.Simple> tokenProxy, ResourceName userStore, boolean includeAccounts, boolean includeGroups) throws HodErrorException;

    /**
     * Get a list of users and metadata in a user store, using a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource name of the user store
     * @param includeAccounts If true, accounts are listed for each user
     * @param includeGroups If true, groups are listed for each user
     * @return The users in the user store and their metadata
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService is not configured
     */
    <T> List<User> listWithMetadata(ResourceName userStore, boolean includeAccounts, boolean includeGroups) throws HodErrorException;

    /**
     * Get a list of users and metadata in a user store, using a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param tokenProxy The token proxy to use for authentication
     * @param userStore The resource name of the user store
     * @param includeAccounts If true, accounts are listed for each user
     * @param includeGroups If true, groups are listed for each user
     * @return The users in the user store and their metadata
     * @throws HodErrorException
     */
    List<User> listWithMetadata(TokenProxy<?, TokenType.Simple> tokenProxy, ResourceName userStore, boolean includeAccounts, boolean includeGroups) throws HodErrorException;

    /**
     * Create a user in a userstore.
     * @param tokenProxy The token proxy to use for authentication
     * @param userStore The resource name of the user store
     * @param userEmail The email of the user to be created
     * @param onSuccess The URL to redirect the user to after the create
     * @param onError The URL to redirect the user to if the create fails
     * @param params Additional parameters to be sent as part of the request
     * @throws HodErrorException
     */
    void create(TokenProxy<?, TokenType.Simple> tokenProxy, ResourceName userStore, String userEmail, URL onSuccess, URL onError, CreateUserRequestBuilder params) throws HodErrorException;

    /**
     * Create a user in a userstore, using a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource name of the user store
     * @param userEmail The email of the user to be created
     * @param onSuccess The URL to redirect the user to after the create
     * @param onError The URL to redirect the user to if the create fails
     * @param params Additional parameters to be sent as part of the request
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService is not configured
     */
    void create(ResourceName userStore, String userEmail, URL onSuccess, URL onError, CreateUserRequestBuilder params) throws HodErrorException;

    /**
     * Delete a user with a uuid from a userstore.
     * @param tokenProxy The token proxy to use for authentication
     * @param userStore The resource name of the user store
     * @param userUuid The uuid of the user to be deleted
     * @throws HodErrorException
     */
    void delete(TokenProxy<?, TokenType.Simple> tokenProxy, ResourceName userStore, UUID userUuid) throws HodErrorException;

    /**
     * Delete a user with a uuid from a userstore, using a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource name of the user store
     * @param userUuid The uuid of the user to be deleted
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService is not configured
     */
    void delete(ResourceName userStore, UUID userUuid) throws HodErrorException;

    /**
     * Resets the authentication associated with the given user store and UUID, using a
     * {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource name of the user store
     * @param userUuid The UUID of the user
     * @param onSuccess The URL to redirect the user to after the reset
     * @param onError The URL to redirect the user to if the reset fails
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService is not configured
     */
    void resetAuthentication(
        ResourceName userStore,
        UUID userUuid,
        URL onSuccess,
        URL onError
    ) throws HodErrorException;

    /**
     * Resets the authentication associated with the given user store and UUID, using the given {@link TokenProxy}
     * @param tokenProxy The token proxy to use to make the request
     * @param userStore The resource name of the user store
     * @param userUuid The UUID of the user
     * @param onSuccess The URL to redirect the user to after the reset
     * @param onError The URL to redirect the user to if the reset fails
     * @throws HodErrorException
     */
    void resetAuthentication(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        ResourceName userStore,
        UUID userUuid,
        URL onSuccess,
        URL onError
    ) throws HodErrorException;

    /**
     * Get the metadata associated with the given user, using a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * for authentication.
     * @param userStore The resource name of the user store
     * @param userUuid The UUID of the user
     * @return A map of metadata keys to parsed values
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService is not configured
     */
    Map<String, JsonNode> getUserMetadata(
        ResourceName userStore,
        UUID userUuid
    ) throws HodErrorException;

    /**
     * Get the metadata associated with the given user, using a TokenProxy for authentication.
     * @param tokenProxy Used for authentication
     * @param userStore The resource name of the user store
     * @param userUuid The UUID of the user
     * @return A map of metadata keys to parsed values
     * @throws HodErrorException
     */
    Map<String, JsonNode> getUserMetadata(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        ResourceName userStore,
        UUID userUuid
    ) throws HodErrorException;

    /**
     * Add metadata keys and values to the given user, using a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * for authentication.
     * @param userStore The resource name of the user store
     * @param userUuid The UUID of the user
     * @param metadata The metadata keys and value to assign to the user
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService is not configured
     */
    void addUserMetadata(
        ResourceName userStore,
        UUID userUuid,
        Map<String, ?> metadata
    ) throws HodErrorException;

    /**
     * Add metadata keys and values to the given user, using a TokenProxy for authentication.
     * @param tokenProxy Used for authentication
     * @param userStore The resource name of the user store
     * @param userUuid The UUID of the user
     * @param metadata The metadata keys and value to assign to the user
     * @throws HodErrorException
     */
    void addUserMetadata(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        ResourceName userStore,
        UUID userUuid,
        Map<String, ?> metadata
    ) throws HodErrorException;

    /**
     * Remove a metadata key from a user, using a {@link com.hp.autonomy.hod.client.token.TokenProxyService} for authentication.
     * @param userStore The resource name of the user store
     * @param userUuid The UUID of the user
     * @param metadataKey The key to remove
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService is not configured
     */
    void removeUserMetadata(
        ResourceName userStore,
        UUID userUuid,
        String metadataKey
    ) throws HodErrorException;

    /**
     * Remove a metadata key from a user, using a TokenProxy for authentication.
     * @param tokenProxy Used for authentication
     * @param userStore The resource name of the user store
     * @param userUuid The UUID of the user
     * @param metadataKey The key to remove
     * @throws HodErrorException
     */
    void removeUserMetadata(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        ResourceName userStore,
        UUID userUuid,
        String metadataKey
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
        ResourceName userStore,
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
        ResourceName userStore,
        UUID userUuid
    ) throws HodErrorException;

}
