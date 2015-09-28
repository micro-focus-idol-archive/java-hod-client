/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.group;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.util.MultiMap;
import retrofit.client.Response;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GroupsServiceImpl implements GroupsService {
    private final GroupsBackend backend;
    private final Requester requester;

    public GroupsServiceImpl(final HodServiceConfig config) {
        backend = config.getRestAdapter().create(GroupsBackend.class);
        requester = config.getRequester();
    }

    @Override
    public List<Group> list(final ResourceIdentifier userStore) throws HodErrorException {
        return requester.makeRequest(ListGroupsResponse.class, listBackendCaller(userStore)).getGroups();
    }

    @Override
    public List<Group> list(final TokenProxy tokenProxy, final ResourceIdentifier userStore) throws HodErrorException {
        return requester.makeRequest(tokenProxy, ListGroupsResponse.class, listBackendCaller(userStore)).getGroups();
    }

    @Override
    public GroupInfo getInfo(final ResourceIdentifier userStore, final String name) throws HodErrorException {
        return requester.makeRequest(GroupInfo.class, getInfoBackendCaller(userStore, name));
    }

    @Override
    public GroupInfo getInfo(final TokenProxy tokenProxy, final ResourceIdentifier userStore, final String name) throws HodErrorException {
        return requester.makeRequest(tokenProxy, GroupInfo.class, getInfoBackendCaller(userStore, name));
    }

    @Override
    public CreateGroupResponse create(final ResourceIdentifier userStore, final String name) throws HodErrorException {
        return requester.makeRequest(CreateGroupResponse.class, createBackendCaller(userStore, Collections.<String, Object>emptyMap(), name));
    }

    @Override
    public CreateGroupResponse create(final TokenProxy tokenProxy, final ResourceIdentifier userStore, final String name) throws HodErrorException {
        return requester.makeRequest(tokenProxy, CreateGroupResponse.class, createBackendCaller(userStore, Collections.<String, Object>emptyMap(), name));
    }

    @Override
    public CreateGroupResponse createWithHierarchy(final ResourceIdentifier userStore, final String name, final List<String> parents, final List<String> children) throws HodErrorException {
        return requester.makeRequest(CreateGroupResponse.class, createBackendCaller(userStore, buildHierarchyParameters(parents, children), name));
    }

    @Override
    public CreateGroupResponse createWithHierarchy(final TokenProxy tokenProxy, final ResourceIdentifier userStore, final String name, final List<String> parents, final List<String> children) throws HodErrorException {
        return requester.makeRequest(tokenProxy, CreateGroupResponse.class, createBackendCaller(userStore, buildHierarchyParameters(parents, children), name));
    }

    @Override
    public void delete(final ResourceIdentifier userStore, final String name) throws HodErrorException {
        requester.makeRequest(StatusResponse.class, deleteBackendCaller(userStore, name));
    }

    @Override
    public void delete(final TokenProxy tokenProxy, final ResourceIdentifier userStore, final String name) throws HodErrorException {
        requester.makeRequest(tokenProxy, StatusResponse.class, deleteBackendCaller(userStore, name));
    }

    @Override
    public AssignUserResponse assignUser(final ResourceIdentifier userStore, final String groupName, final UUID userUuid) throws HodErrorException {
        return requester.makeRequest(AssignUserResponseWrapper.class, assignBackendCaller(userStore, groupName, userUuid)).getResult();
    }

    @Override
    public AssignUserResponse assignUser(final TokenProxy tokenProxy, final ResourceIdentifier userStore, final String groupName, final UUID userUuid) throws HodErrorException {
        return requester.makeRequest(tokenProxy, AssignUserResponseWrapper.class, assignBackendCaller(userStore, groupName, userUuid)).getResult();
    }

    @Override
    public void removeUser(final ResourceIdentifier userStore, final String groupName, final UUID userUuid) throws HodErrorException {
        requester.makeRequest(StatusResponse.class, removeBackendCaller(userStore, groupName, userUuid));
    }

    @Override
    public void removeUser(final TokenProxy tokenProxy, final ResourceIdentifier userStore, final String groupName, final UUID userUuid) throws HodErrorException {
        requester.makeRequest(tokenProxy, StatusResponse.class, removeBackendCaller(userStore, groupName, userUuid));
    }

    @Override
    public void link(final ResourceIdentifier userStore, final String parent, final String child) throws HodErrorException {
        requester.makeRequest(StatusResponse.class, linkBackendCaller(userStore, parent, child));
    }

    @Override
    public void link(final TokenProxy tokenProxy, final ResourceIdentifier userStore, final String parent, final String child) throws HodErrorException {
        requester.makeRequest(tokenProxy, StatusResponse.class, linkBackendCaller(userStore, parent, child));
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

    private Requester.BackendCaller listBackendCaller(final ResourceIdentifier userStore) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken token) throws HodErrorException {
                return backend.list(token, userStore);
            }
        };
    }

    private Requester.BackendCaller getInfoBackendCaller(final ResourceIdentifier userStore, final String group) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken token) throws HodErrorException {
                return backend.getInfo(token, userStore, group);
            }
        };
    }

    private Requester.BackendCaller createBackendCaller(final ResourceIdentifier userStore, final Map<String, Object> hierarchyParameters, final String group) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken token) throws HodErrorException {
                return backend.create(token, userStore, group, hierarchyParameters);
            }
        };
    }

    private Requester.BackendCaller deleteBackendCaller(final ResourceIdentifier userStore, final String group) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken token) throws HodErrorException {
                return backend.delete(token, userStore, group);
            }
        };
    }

    private Requester.BackendCaller assignBackendCaller(final ResourceIdentifier userStore, final String group, final UUID userUuid) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken token) throws HodErrorException {
                return backend.assignUser(token, userStore, group, userUuid.toString());
            }
        };
    }

    private Requester.BackendCaller removeBackendCaller(final ResourceIdentifier userStore, final String group, final UUID userUuid) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken token) throws HodErrorException {
                return backend.removeUser(token, userStore, group, userUuid.toString());
            }
        };
    }

    private Requester.BackendCaller linkBackendCaller(final ResourceIdentifier userStore, final String parent, final String child) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken token) throws HodErrorException {
                return backend.link(token, userStore, parent, child);
            }
        };
    }
}
