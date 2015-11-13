/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.group;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.util.MultiMap;
import com.hp.autonomy.hod.client.util.StatusResponse;
import retrofit.client.Response;

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
    public List<Group> list(final ResourceIdentifier userStore) throws HodErrorException {
        return requester.makeRequest(ListGroupsResponse.class, listBackendCaller(userStore)).getGroups();
    }

    @Override
    public List<Group> list(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceIdentifier userStore) throws HodErrorException {
        return requester.makeRequest(tokenProxy, ListGroupsResponse.class, listBackendCaller(userStore)).getGroups();
    }

    @Override
    public GroupInfo getInfo(final ResourceIdentifier userStore, final String name) throws HodErrorException {
        return requester.makeRequest(GroupInfo.class, getInfoBackendCaller(userStore, name));
    }

    @Override
    public GroupInfo getInfo(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceIdentifier userStore, final String name) throws HodErrorException {
        return requester.makeRequest(tokenProxy, GroupInfo.class, getInfoBackendCaller(userStore, name));
    }

    @Override
    public CreateGroupResponse create(final ResourceIdentifier userStore, final String name) throws HodErrorException {
        return requester.makeRequest(CreateGroupResponse.class, createBackendCaller(userStore, Collections.<String, Object>emptyMap(), name));
    }

    @Override
    public CreateGroupResponse create(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceIdentifier userStore, final String name) throws HodErrorException {
        return requester.makeRequest(tokenProxy, CreateGroupResponse.class, createBackendCaller(userStore, Collections.<String, Object>emptyMap(), name));
    }

    @Override
    public CreateGroupResponse createWithHierarchy(final ResourceIdentifier userStore, final String name, final List<String> parents, final List<String> children) throws HodErrorException {
        return requester.makeRequest(CreateGroupResponse.class, createBackendCaller(userStore, buildHierarchyParameters(parents, children), name));
    }

    @Override
    public CreateGroupResponse createWithHierarchy(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceIdentifier userStore, final String name, final List<String> parents, final List<String> children) throws HodErrorException {
        return requester.makeRequest(tokenProxy, CreateGroupResponse.class, createBackendCaller(userStore, buildHierarchyParameters(parents, children), name));
    }

    @Override
    public void delete(final ResourceIdentifier userStore, final String name) throws HodErrorException {
        requester.makeRequest(StatusResponse.class, deleteBackendCaller(userStore, name));
    }

    @Override
    public void delete(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceIdentifier userStore, final String name) throws HodErrorException {
        requester.makeRequest(tokenProxy, StatusResponse.class, deleteBackendCaller(userStore, name));
    }

    @Override
    public void assignUser(final ResourceIdentifier userStore, final String groupName, final UUID userUuid) throws HodErrorException {
        requester.makeRequest(StatusResponse.class, assignBackendCaller(userStore, groupName, userUuid));
    }

    @Override
    public void assignUser(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceIdentifier userStore, final String groupName, final UUID userUuid) throws HodErrorException {
        requester.makeRequest(tokenProxy, StatusResponse.class, assignBackendCaller(userStore, groupName, userUuid));
    }

    @Override
    public void removeUser(final ResourceIdentifier userStore, final String groupName, final UUID userUuid) throws HodErrorException {
        requester.makeRequest(StatusResponse.class, removeBackendCaller(userStore, groupName, userUuid));
    }

    @Override
    public void removeUser(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceIdentifier userStore, final String groupName, final UUID userUuid) throws HodErrorException {
        requester.makeRequest(tokenProxy, StatusResponse.class, removeBackendCaller(userStore, groupName, userUuid));
    }

    @Override
    public void link(final ResourceIdentifier userStore, final String parent, final String child) throws HodErrorException {
        requester.makeRequest(StatusResponse.class, linkBackendCaller(userStore, parent, child));
    }

    @Override
    public void link(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceIdentifier userStore, final String parent, final String child) throws HodErrorException {
        requester.makeRequest(tokenProxy, StatusResponse.class, linkBackendCaller(userStore, parent, child));
    }

    @Override
    public void unlink(final ResourceIdentifier userStore, final String parent, final String child) throws HodErrorException {
        requester.makeRequest(StatusResponse.class, unlinkBackendCaller(userStore, parent, child));
    }

    @Override
    public void unlink(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceIdentifier userStore, final String parent, final String child) throws HodErrorException {
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

    private Requester.BackendCaller<EntityType, TokenType.Simple> listBackendCaller(final ResourceIdentifier userStore) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> token) throws HodErrorException {
                return backend.list(token, userStore);
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getInfoBackendCaller(final ResourceIdentifier userStore, final String group) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> token) throws HodErrorException {
                return backend.getInfo(token, userStore, group);
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> createBackendCaller(final ResourceIdentifier userStore, final Map<String, Object> hierarchyParameters, final String group) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> token) throws HodErrorException {
                return backend.create(token, userStore, group, hierarchyParameters);
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> deleteBackendCaller(final ResourceIdentifier userStore, final String group) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> token) throws HodErrorException {
                return backend.delete(token, userStore, group);
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> assignBackendCaller(final ResourceIdentifier userStore, final String group, final UUID userUuid) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> token) throws HodErrorException {
                return backend.assignUser(token, userStore, group, userUuid.toString());
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> removeBackendCaller(final ResourceIdentifier userStore, final String group, final UUID userUuid) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> token) throws HodErrorException {
                return backend.removeUser(token, userStore, group, userUuid.toString());
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> linkBackendCaller(final ResourceIdentifier userStore, final String parent, final String child) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> token) throws HodErrorException {
                return backend.link(token, userStore, parent, child);
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> unlinkBackendCaller(final ResourceIdentifier userStore, final String parent, final String child) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> token) throws HodErrorException {
                return backend.unlink(token, userStore, parent, child);
            }
        };
    }
}
