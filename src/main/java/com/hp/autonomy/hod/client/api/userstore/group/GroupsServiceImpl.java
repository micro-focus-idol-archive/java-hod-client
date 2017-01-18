/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.group;

import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.util.MultiMap;
import com.hp.autonomy.hod.client.util.StatusResponse;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GroupsServiceImpl implements GroupsService {
    private final GroupsBackend backend;
    private final Requester<?, TokenType.Simple> requester;

    public GroupsServiceImpl(final HodServiceConfig<?, TokenType.Simple> config) {
        backend = config.getRestAdapter().create(GroupsBackend.class);
        requester = config.getRequester();
    }

    @Override
    public List<Group> list(final ResourceName userStore) throws HodErrorException {
        return requester.makeRequest(ListGroupsResponse.class, listBackendCaller(userStore)).getGroups();
    }

    @Override
    public List<Group> list(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceName userStore) throws HodErrorException {
        return requester.makeRequest(tokenProxy, ListGroupsResponse.class, listBackendCaller(userStore)).getGroups();
    }

    @Override
    public GroupInfo getInfo(final ResourceName userStore, final String name) throws HodErrorException {
        return requester.makeRequest(GroupInfo.class, getInfoBackendCaller(userStore, name));
    }

    @Override
    public GroupInfo getInfo(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceName userStore, final String name) throws HodErrorException {
        return requester.makeRequest(tokenProxy, GroupInfo.class, getInfoBackendCaller(userStore, name));
    }

    @Override
    public CreateGroupResponse create(final ResourceName userStore, final String name) throws HodErrorException {
        return requester.makeRequest(CreateGroupResponse.class, createBackendCaller(userStore, Collections.<String, Object>emptyMap(), name));
    }

    @Override
    public CreateGroupResponse create(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceName userStore, final String name) throws HodErrorException {
        return requester.makeRequest(tokenProxy, CreateGroupResponse.class, createBackendCaller(userStore, Collections.<String, Object>emptyMap(), name));
    }

    @Override
    public CreateGroupResponse createWithHierarchy(final ResourceName userStore, final String name, final List<String> parents, final List<String> children) throws HodErrorException {
        return requester.makeRequest(CreateGroupResponse.class, createBackendCaller(userStore, buildHierarchyParameters(parents, children), name));
    }

    @Override
    public CreateGroupResponse createWithHierarchy(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceName userStore, final String name, final List<String> parents, final List<String> children) throws HodErrorException {
        return requester.makeRequest(tokenProxy, CreateGroupResponse.class, createBackendCaller(userStore, buildHierarchyParameters(parents, children), name));
    }

    @Override
    public void delete(final ResourceName userStore, final String name) throws HodErrorException {
        requester.makeRequest(StatusResponse.class, deleteBackendCaller(userStore, name));
    }

    @Override
    public void delete(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceName userStore, final String name) throws HodErrorException {
        requester.makeRequest(tokenProxy, StatusResponse.class, deleteBackendCaller(userStore, name));
    }

    @Override
    public void assignUser(final ResourceName userStore, final String groupName, final UUID userUuid) throws HodErrorException {
        requester.makeRequest(StatusResponse.class, assignBackendCaller(userStore, groupName, userUuid));
    }

    @Override
    public void assignUser(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceName userStore, final String groupName, final UUID userUuid) throws HodErrorException {
        requester.makeRequest(tokenProxy, StatusResponse.class, assignBackendCaller(userStore, groupName, userUuid));
    }

    @Override
    public void removeUser(final ResourceName userStore, final String groupName, final UUID userUuid) throws HodErrorException {
        requester.makeRequest(StatusResponse.class, removeBackendCaller(userStore, groupName, userUuid));
    }

    @Override
    public void removeUser(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceName userStore, final String groupName, final UUID userUuid) throws HodErrorException {
        requester.makeRequest(tokenProxy, StatusResponse.class, removeBackendCaller(userStore, groupName, userUuid));
    }

    @Override
    public void link(final ResourceName userStore, final String parent, final String child) throws HodErrorException {
        requester.makeRequest(StatusResponse.class, linkBackendCaller(userStore, parent, child));
    }

    @Override
    public void link(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceName userStore, final String parent, final String child) throws HodErrorException {
        requester.makeRequest(tokenProxy, StatusResponse.class, linkBackendCaller(userStore, parent, child));
    }

    @Override
    public void unlink(final ResourceName userStore, final String parent, final String child) throws HodErrorException {
        requester.makeRequest(StatusResponse.class, unlinkBackendCaller(userStore, parent, child));
    }

    @Override
    public void unlink(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceName userStore, final String parent, final String child) throws HodErrorException {
        requester.makeRequest(tokenProxy, StatusResponse.class, unlinkBackendCaller(userStore, parent, child));
    }

    // Build the hierarchy parameters map for a create group request
    private Map<String, Object> buildHierarchyParameters(final List<String> parents, final List<String> children) {
        final MultiMap<String, Object> parameters = new MultiMap<>();

        if (parents != null) {
            for (final String parent : parents) {
                parameters.put("parents", parent);
            }
        }

        if (children != null) {
            for (final String child : children) {
                parameters.put("children", child);
            }
        }

        return parameters;
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> listBackendCaller(final ResourceName userStore) {
        return token -> backend.list(token, userStore);
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getInfoBackendCaller(final ResourceName userStore, final String group) {
        return token -> backend.getInfo(token, userStore, group);
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> createBackendCaller(final ResourceName userStore, final Map<String, Object> hierarchyParameters, final String group) {
        return token -> backend.create(token, userStore, group, hierarchyParameters);
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> deleteBackendCaller(final ResourceName userStore, final String group) {
        return token -> backend.delete(token, userStore, group);
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> assignBackendCaller(final ResourceName userStore, final String group, final UUID userUuid) {
        return token -> backend.assignUser(token, userStore, group, userUuid.toString());
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> removeBackendCaller(final ResourceName userStore, final String group, final UUID userUuid) {
        return token -> backend.removeUser(token, userStore, group, userUuid.toString());
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> linkBackendCaller(final ResourceName userStore, final String parent, final String child) {
        return token -> backend.link(token, userStore, parent, child);
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> unlinkBackendCaller(final ResourceName userStore, final String parent, final String child) {
        return token -> backend.unlink(token, userStore, parent, child);
    }
}
