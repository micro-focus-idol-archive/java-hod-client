/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.group;

import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.util.List;
import java.util.UUID;

/**
 * Service for controlling the groups in a user store.
 */
public interface GroupsService {

    /**
     * List the groups in the given user store, using a token proxy from a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource name for the user store
     * @return The groups in the user store and their relationships
     * @throws HodErrorException
     */
    List<Group> list(ResourceName userStore) throws HodErrorException;

    /**
     * List the groups in the given user store.
     * @param tokenProxy The token proxy to use for authentication
     * @param userStore The resource name for the user store
     * @return The groups in the user store and their relationships
     * @throws HodErrorException
     */
    List<Group> list(TokenProxy<?, TokenType.Simple> tokenProxy, ResourceName userStore) throws HodErrorException;

    /**
     * Get information about the given group in the given user store, including its members, using a token proxy from a
     * {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource name for the user store
     * @param name The group name
     * @return Information about the group
     * @throws HodErrorException
     */
    GroupInfo getInfo(ResourceName userStore, String name) throws HodErrorException;

    /**
     * Get information about the given group in the given user store, including its members.
     * @param tokenProxy The token proxy to use for authentication
     * @param userStore The resource name for the user store
     * @param name The group name
     * @return Information about the group
     * @throws HodErrorException
     */
    GroupInfo getInfo(TokenProxy<?, TokenType.Simple> tokenProxy, ResourceName userStore, String name) throws HodErrorException;

    /**
     * Create a group in the given user store, using a token proxy from a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * The group will be created without any hierarchical relationships.
     * @param userStore The resource name for the user store
     * @param name The group name
     * @return Details about the resulting action
     * @throws HodErrorException
     */
    CreateGroupResponse create(ResourceName userStore, String name) throws HodErrorException;

    /**
     * Create a group in the given user store. The group will be created without any hierarchical relationships.
     * @param tokenProxy The token proxy to use for authentication
     * @param userStore The resource name for the user store
     * @param name The new group name
     * @return Details about the resulting action
     * @throws HodErrorException
     */
    CreateGroupResponse create(TokenProxy<?, TokenType.Simple> tokenProxy, ResourceName userStore, String name) throws HodErrorException;

    /**
     * Create a group in the given user store using a token proxy from a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * Parent and child relationships will be set up between the new group and the groups listed in the parents and children
     * parameters.
     * @param userStore The resource name for the user store
     * @param name The new group name
     * @param parents A list of names of groups which should be parents of the new group (can be null or empty)
     * @param children A list of names of groups which should be children of the new group (can be null or empty)
     * @return Details about the resulting action
     * @throws HodErrorException
     */
    CreateGroupResponse createWithHierarchy(ResourceName userStore, String name, List<String> parents, List<String> children) throws HodErrorException;

    /**
     * Create a group in the given user store. Parent and child relationships will be set up between the new group and
     * the groups listed in the parents and children parameters.
     * @param tokenProxy The token proxy to use for authentication
     * @param userStore The resource name for the user store
     * @param name The new group name
     * @param parents A list of names of groups which should be parents of the new group (can be null or empty)
     * @param children A list of names of groups which should be children of the new group (can be null or empty)
     * @return Details about the resulting action
     * @throws HodErrorException
     */
    CreateGroupResponse createWithHierarchy(TokenProxy<?, TokenType.Simple> tokenProxy, ResourceName userStore, String name, List<String> parents, List<String> children) throws HodErrorException;

    /**
     * Delete a group from the given user store using a token proxy from a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource name for the user store
     * @param name The group name
     * @throws HodErrorException
     */
    void delete(ResourceName userStore, String name) throws HodErrorException;

    /**
     * Delete a group from the given user store.
     * @param tokenProxy The token proxy to use for authentication
     * @param userStore The resource name for the user store
     * @param name The group name
     * @throws HodErrorException
     */
    void delete(TokenProxy<?, TokenType.Simple> tokenProxy, ResourceName userStore, String name) throws HodErrorException;

    /**
     * Assign a user to a group in a user store. Uses a token proxy from a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource name for the user store
     * @param groupName The group name
     * @param userUuid The UUID of the user
     * @throws HodErrorException
     */
    void assignUser(ResourceName userStore, String groupName, UUID userUuid) throws HodErrorException;

    /**
     * Assign a user to a group in a user store.
     * @param tokenProxy The token proxy to use for authentication
     * @param userStore The resource name for the user store
     * @param groupName The group name
     * @param userUuid The UUID of the user
     * @throws HodErrorException
     */
    void assignUser(TokenProxy<?, TokenType.Simple> tokenProxy, ResourceName userStore, String groupName, UUID userUuid) throws HodErrorException;

    /**
     * Remove a user from a group in a user store. Uses a token proxy from a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource name for the user store
     * @param groupName The group name
     * @param userUuid The UUID of the user
     * @throws HodErrorException
     */
    void removeUser(ResourceName userStore, String groupName, UUID userUuid) throws HodErrorException;

    /**
     * Remove a user from a group in a user store.
     * @param tokenProxy The token proxy to use for authentication
     * @param userStore The resource name for the user store
     * @param groupName The group name
     * @param userUuid The UUID of the user
     * @throws HodErrorException
     */
    void removeUser(TokenProxy<?, TokenType.Simple> tokenProxy, ResourceName userStore, String groupName, UUID userUuid) throws HodErrorException;

    /**
     * Create a hierarchical relationship between two groups in a user store. Uses a token proxy from a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource name for the user store
     * @param parent The parent group name
     * @param child The child group name
     * @throws HodErrorException
     */
    void link(ResourceName userStore, String parent, String child) throws HodErrorException;

    /**
     * Create a hierarchical relationship between two groups in a user store.
     * @param tokenProxy The token proxy to use for authentication
     * @param userStore The resource name for the user store
     * @param parent The parent group name
     * @param child The child group name
     * @throws HodErrorException
     */
    void link(TokenProxy<?, TokenType.Simple> tokenProxy, ResourceName userStore, String parent, String child) throws HodErrorException;

    /**
     * Remove a hierarchical relationship between two groups in a user store. Uses a token proxy from a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * @param userStore The resource name for the user store
     * @param parent The parent group name
     * @param child The child group name
     * @throws HodErrorException
     */
    void unlink(ResourceName userStore, String parent, String child) throws HodErrorException;

    /**
     * Remove a hierarchical relationship between two groups in a user store.
     * @param tokenProxy The token proxy to use for authentication
     * @param userStore The resource name for the user store
     * @param parent The parent group name
     * @param child The child group name
     * @throws HodErrorException
     */
    void unlink(TokenProxy<?, TokenType.Simple> tokenProxy, ResourceName userStore, String parent, String child) throws HodErrorException;

}
