/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.util.StatusResponse;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class UserStoreUsersServiceImpl implements UserStoreUsersService {
    private final Requester<?, TokenType.Simple> requester;
    private final UserStoreUsersBackend backend;

    public UserStoreUsersServiceImpl(final HodServiceConfig<?, TokenType.Simple> config) {
        requester = config.getRequester();
        backend = config.getRestAdapter().create(UserStoreUsersBackend.class);
    }

    @Override
    public List<User> list(final ResourceName userStore, final boolean includeAccounts, final boolean includeGroups) throws HodErrorException {
        return requester.makeRequest(ListUsersResponse.class, listBackendCaller(userStore, false, includeAccounts, includeGroups)).getUsers();
    }

    @Override
    public List<User> list(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final ResourceName userStore,
        final boolean includeAccounts,
        final boolean includeGroups
    ) throws HodErrorException {
        return requester.makeRequest(tokenProxy, ListUsersResponse.class, listBackendCaller(userStore, false, includeAccounts, includeGroups)).getUsers();
    }

    @Override
    public List<User> listWithMetadata(
        final ResourceName userStore,
        final boolean includeAccounts,
        final boolean includeGroups
    ) throws HodErrorException {
        return requester.makeRequest(ListUsersResponse.class, listBackendCaller(userStore, true, includeAccounts, includeGroups)).getUsers();
    }

    @Override
    public List<User> listWithMetadata(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final ResourceName userStore,
        final boolean includeAccounts,
        final boolean includeGroups
    ) throws HodErrorException {
        return requester.makeRequest(tokenProxy, ListUsersResponse.class, listBackendCaller(userStore, true, includeAccounts, includeGroups)).getUsers();
    }

    @Override
    public void create(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final ResourceName userStore,
        final String userEmail,
        final URL onSuccess,
        final URL onError,
        final CreateUserRequestBuilder params
    ) throws HodErrorException {
        requester.makeRequest(tokenProxy, Void.class, createBackendCaller(userStore, userEmail, onSuccess, onError, params));
    }

    @Override
    public void create(
        final ResourceName userStore,
        final String userEmail,
        final URL onSuccess,
        final URL onError,
        final CreateUserRequestBuilder params
    ) throws HodErrorException {
        requester.makeRequest(Void.class, createBackendCaller(userStore, userEmail, onSuccess, onError, params));
    }

    @Override
    public void delete(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final ResourceName userStore,
        final UUID userUuid
    ) throws HodErrorException {
        requester.makeRequest(tokenProxy, Void.class, deleteBackendCaller(userStore, userUuid));
    }

    @Override
    public void delete(
        final ResourceName userStore,
        final UUID userUuid
    ) throws HodErrorException {
        requester.makeRequest(Void.class, deleteBackendCaller(userStore, userUuid));
    }

    @Override
    public void resetAuthentication(
        final ResourceName userStore,
        final UUID userUuid,
        final URL onSuccess,
        final URL onError
    ) throws HodErrorException {
        requester.makeRequest(StatusResponse.class, getResetBackendCaller(userStore, userUuid, onSuccess, onError));
    }

    @Override
    public void resetAuthentication(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final ResourceName userStore,
        final UUID userUuid,
        final URL onSuccess,
        final URL onError
    ) throws HodErrorException {
        requester.makeRequest(tokenProxy, StatusResponse.class, getResetBackendCaller(userStore, userUuid, onSuccess, onError));
    }

    @Override
    public Map<String, JsonNode> getUserMetadata(final ResourceName userStore, final UUID userUuid) throws HodErrorException {
        return parseMetadata(requester.makeRequest(GetMetadataResponse.class, getUserMetadataBackendCaller(userStore, userUuid)).getMetadata());
    }

    @Override
    public Map<String, JsonNode> getUserMetadata(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceName userStore, final UUID userUuid) throws HodErrorException {
        return parseMetadata(requester.makeRequest(tokenProxy, GetMetadataResponse.class, getUserMetadataBackendCaller(userStore, userUuid)).getMetadata());
    }

    @Override
    public void addUserMetadata(final ResourceName userStore, final UUID userUuid, final Map<String, ?> metadata) throws HodErrorException {
        requester.makeRequest(Void.class, addUserMetadataBackendCaller(userStore, userUuid, metadata));
    }

    @Override
    public void addUserMetadata(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceName userStore, final UUID userUuid, final Map<String, ?> metadata) throws HodErrorException {
        requester.makeRequest(tokenProxy, Void.class, addUserMetadataBackendCaller(userStore, userUuid, metadata));
    }

    @Override
    public void removeUserMetadata(final ResourceName userStore, final UUID userUuid, final String metadataKey) throws HodErrorException {
        requester.makeRequest(Void.class, removeUserMetadataBackendCaller(userStore, userUuid, metadataKey));
    }

    @Override
    public void removeUserMetadata(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceName userStore, final UUID userUuid, final String metadataKey) throws HodErrorException {
        requester.makeRequest(tokenProxy, Void.class, removeUserMetadataBackendCaller(userStore, userUuid, metadataKey));
    }

    @Override
    public UserGroups listUserGroups(
        final ResourceName userStore,
        final UUID userUuid
    ) throws HodErrorException {
        return requester.makeRequest(UserGroups.class, listUserGroupsBackendCaller(userStore, userUuid));
    }

    @Override
    public UserGroups listUserGroups(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final ResourceName userStore,
        final UUID userUuid
    ) throws HodErrorException {
        return requester.makeRequest(tokenProxy, UserGroups.class, listUserGroupsBackendCaller(userStore, userUuid));
    }

    private Map<String, JsonNode> parseMetadata(final List<Metadata<JsonNode>> metadataList) {
        final Map<String, JsonNode> metadata = new HashMap<>();

        for (final Metadata<JsonNode> metadataItem : metadataList) {
            metadata.put(metadataItem.getKey(), metadataItem.getValue());
        }

        return metadata;
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> listBackendCaller(final ResourceName userStore, final boolean includeMetadata, final boolean includeAccounts, final boolean includeGroups) {
        return token -> backend.list(token, userStore, includeMetadata, includeAccounts, includeGroups);
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> createBackendCaller(final ResourceName userStore, final String userEmail, final URL onSuccess, final URL onError, final CreateUserRequestBuilder params) {
        return token -> backend.create(token, userStore, userEmail, onSuccess.toString(), onError.toString(), params == null ? null : params.build());
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> deleteBackendCaller(final ResourceName userStore, final UUID userUuid) {
        return token -> backend.delete(token, userStore, userUuid);
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getResetBackendCaller(final ResourceName userStore, final UUID userUuid, final URL onSuccess, final URL onError) {
        return authenticationToken -> backend.resetAuthentication(authenticationToken, userStore, userUuid, onSuccess.toString(), onError.toString());
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> listUserGroupsBackendCaller(final ResourceName userStore, final UUID userUuid) {
        return authenticationToken -> backend.listUserGroups(authenticationToken, userStore, userUuid);
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getUserMetadataBackendCaller(final ResourceName userStore, final UUID userUuid) {
        return authenticationToken -> backend.getUserMetadata(authenticationToken, userStore, userUuid);
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> addUserMetadataBackendCaller(final ResourceName userStore, final UUID userUuid, final Map<String, ?> metadata) {
        final List<Metadata<?>> metadataList = metadata.entrySet().stream().map(entry -> new Metadata<>(entry.getKey(), entry.getValue())).collect(Collectors.toCollection(LinkedList::new));

        return authenticationToken -> backend.addUserMetadata(authenticationToken, userStore, userUuid, metadataList);
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> removeUserMetadataBackendCaller(final ResourceName userStore, final UUID userUuid, final String metadataKey) {
        return authenticationToken -> backend.removeUserMetadata(authenticationToken, userStore, userUuid, metadataKey);
    }
}
