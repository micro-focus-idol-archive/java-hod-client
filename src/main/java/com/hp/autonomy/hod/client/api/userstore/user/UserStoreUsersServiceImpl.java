/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.util.StatusResponse;
import lombok.extern.slf4j.Slf4j;
import retrofit.client.Response;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class UserStoreUsersServiceImpl implements UserStoreUsersService {
    private final ObjectMapper objectMapper;
    private final Requester<?, TokenType.Simple> requester;
    private final UserStoreUsersBackend backend;

    public UserStoreUsersServiceImpl(final HodServiceConfig<?, TokenType.Simple> config) {
        objectMapper = config.getObjectMapper();
        requester = config.getRequester();
        backend = config.getRestAdapter().create(UserStoreUsersBackend.class);
    }

    @Override
    public List<User<Void>> list(final ResourceIdentifier userStore, final boolean includeAccounts, final boolean includeGroups) throws HodErrorException {
        final List<User.Json> rawUsers = requester.makeRequest(ListUsersResponse.class, listBackendCaller(userStore, false, includeAccounts, includeGroups)).getUsers();
        return parseRawUsers(rawUsers);
    }

    @Override
    public List<User<Void>> list(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final ResourceIdentifier userStore,
        final boolean includeAccounts,
        final boolean includeGroups
    ) throws HodErrorException {
        final List<User.Json> rawUsers = requester.makeRequest(tokenProxy, ListUsersResponse.class, listBackendCaller(userStore, false, includeAccounts, includeGroups)).getUsers();
        return parseRawUsers(rawUsers);
    }

    @Override
    public <T> List<User<T>> listWithMetadata(
        final ResourceIdentifier userStore,
        final Map<String, Class<? extends T>> metadataTypes,
        final boolean includeAccounts,
        final boolean includeGroups
    ) throws HodErrorException {
        final List<User.Json> rawUsers = requester.makeRequest(ListUsersResponse.class, listBackendCaller(userStore, true, includeAccounts, includeGroups)).getUsers();
        return parseRawUsersWithMetadata(rawUsers, metadataTypes);
    }

    @Override
    public <T> List<User<T>> listWithMetadata(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final ResourceIdentifier userStore,
        final Map<String, Class<? extends T>> metadataTypes,
        final boolean includeAccounts,
        final boolean includeGroups
    ) throws HodErrorException {
        final List<User.Json> rawUsers = requester.makeRequest(tokenProxy, ListUsersResponse.class, listBackendCaller(userStore, true, includeAccounts, includeGroups)).getUsers();
        return parseRawUsersWithMetadata(rawUsers, metadataTypes);
    }

    @Override
    public void create(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final ResourceIdentifier userStore,
        final String userEmail,
        final URL onSuccess,
        final URL onError,
        final CreateUserRequestBuilder params
    ) throws HodErrorException {
        requester.makeRequest(tokenProxy, Void.class, createBackendCaller(userStore, userEmail, onSuccess, onError, params));
    }

    @Override
    public void create(
        final ResourceIdentifier userStore,
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
        final ResourceIdentifier userStore,
        final UUID userUuid
    ) throws HodErrorException {
        requester.makeRequest(tokenProxy, Void.class, deleteBackendCaller(userStore, userUuid));
    }

    @Override
    public void delete(
        final ResourceIdentifier userStore,
        final UUID userUuid
    ) throws HodErrorException {
        requester.makeRequest(Void.class, deleteBackendCaller(userStore, userUuid));
    }

    @Override
    public void resetAuthentication(
        final ResourceIdentifier userStore,
        final UUID userUuid,
        final URL onSuccess,
        final URL onError
    ) throws HodErrorException {
        requester.makeRequest(StatusResponse.class, getResetBackendCaller(userStore, userUuid, onSuccess, onError));
    }

    @Override
    public void resetAuthentication(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final ResourceIdentifier userStore,
        final UUID userUuid,
        final URL onSuccess,
        final URL onError
    ) throws HodErrorException {
        requester.makeRequest(tokenProxy, StatusResponse.class, getResetBackendCaller(userStore, userUuid, onSuccess, onError));
    }

    @Override
    public <T> Map<String, T> getUserMetadata(final ResourceIdentifier userStore, final UUID userUuid, final Map<String, Class<? extends T>> metadataTypes) throws HodErrorException {
        final List<Metadata<JsonNode>> metadataList = requester.makeRequest(GetMetadataResponse.class, getUserMetadataBackendCaller(userStore, userUuid)).getMetadata();
        return parseMetadata(metadataList, metadataTypes);
    }

    @Override
    public <T> Map<String, T> getUserMetadata(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceIdentifier userStore, final UUID userUuid, final Map<String, Class<? extends T>> metadataTypes) throws HodErrorException {
        final List<Metadata<JsonNode>> metadataList = requester.makeRequest(tokenProxy, GetMetadataResponse.class, getUserMetadataBackendCaller(userStore, userUuid)).getMetadata();
        return parseMetadata(metadataList, metadataTypes);
    }

    @Override
    public void addUserMetadata(final ResourceIdentifier userStore, final UUID userUuid, final Map<String, ?> metadata) throws HodErrorException {
        requester.makeRequest(Void.class, addUserMetadataBackendCaller(userStore, userUuid, metadata));
    }

    @Override
    public void addUserMetadata(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceIdentifier userStore, final UUID userUuid, final Map<String, ?> metadata) throws HodErrorException {
        requester.makeRequest(tokenProxy, Void.class, addUserMetadataBackendCaller(userStore, userUuid, metadata));
    }

    @Override
    public void removeUserMetadata(final ResourceIdentifier userStore, final UUID userUuid, final String metadataKey) throws HodErrorException {
        requester.makeRequest(Void.class, removeUserMetadataBackendCaller(userStore, userUuid, metadataKey));
    }

    @Override
    public void removeUserMetadata(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceIdentifier userStore, final UUID userUuid, final String metadataKey) throws HodErrorException {
        requester.makeRequest(tokenProxy, Void.class, removeUserMetadataBackendCaller(userStore, userUuid, metadataKey));
    }

    @Override
    public UserGroups listUserGroups(
        final ResourceIdentifier userStore,
        final UUID userUuid
    ) throws HodErrorException {
        return requester.makeRequest(UserGroups.class, listUserGroupsBackendCaller(userStore, userUuid));
    }

    @Override
    public UserGroups listUserGroups(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final ResourceIdentifier userStore,
        final UUID userUuid
    ) throws HodErrorException {
        return requester.makeRequest(tokenProxy, UserGroups.class, listUserGroupsBackendCaller(userStore, userUuid));
    }

    private List<User<Void>> parseRawUsers(final List<User.Json> rawUsers) {
        final List<User<Void>> users = new LinkedList<>();

        for (final User.Json rawUser : rawUsers) {
            users.add(new User<Void>(rawUser, null));
        }

        return users;
    }

    private <T> List<User<T>> parseRawUsersWithMetadata(final List<User.Json> rawUsers, final Map<String, Class<? extends T>> metadataTypes) {
        final List<User<T>> users = new LinkedList<>();

        for (final User.Json rawUser : rawUsers) {
            users.add(new User<>(rawUser, parseMetadata(rawUser.getMetadataList(), metadataTypes)));
        }

        return users;
    }

    private <T> Map<String, T> parseMetadata(final List<Metadata<JsonNode>> metadataList, final Map<String, Class<? extends T>> metadataTypes) {
        final Map<String, T> metadata = new HashMap<>();

        for (final Metadata<JsonNode> metadataItem : metadataList) {
            final String key = metadataItem.getKey();
            final Class<? extends T> clazz = metadataTypes.get(key);

            if (clazz != null) {
                try {
                    final T value = objectMapper.treeToValue(metadataItem.getValue(), clazz);
                    metadata.put(key, value);
                } catch (final JsonProcessingException e) {
                    log.warn("Failed to parse metadata key " + key, e);
                }
            }
        }

        return metadata;
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> listBackendCaller(final ResourceIdentifier userStore, final boolean includeMetadata, final boolean includeAccounts, final boolean includeGroups) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> token) throws HodErrorException {
                return backend.list(token, userStore, includeMetadata, includeAccounts, includeGroups);
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> createBackendCaller(final ResourceIdentifier userStore, final String userEmail, final URL onSuccess, final URL onError, final CreateUserRequestBuilder params) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> token) throws HodErrorException {
                return backend.create(token, userStore, userEmail, onSuccess.toString(), onError.toString(), params == null ? null : params.build());
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> deleteBackendCaller(final ResourceIdentifier userStore, final UUID userUuid) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> token) throws HodErrorException {
                return backend.delete(token, userStore, userUuid);
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getResetBackendCaller(final ResourceIdentifier userStore, final UUID userUuid, final URL onSuccess, final URL onError) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return backend.resetAuthentication(authenticationToken, userStore, userUuid, onSuccess.toString(), onError.toString());
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> listUserGroupsBackendCaller(final ResourceIdentifier userStore, final UUID userUuid) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<? extends EntityType, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return backend.listUserGroups(authenticationToken, userStore, userUuid);
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getUserMetadataBackendCaller(final ResourceIdentifier userStore, final UUID userUuid) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<? extends EntityType, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return backend.getUserMetadata(authenticationToken, userStore, userUuid);
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> addUserMetadataBackendCaller(final ResourceIdentifier userStore, final UUID userUuid, final Map<String, ?> metadata) {
        final List<Metadata<?>> metadataList = new LinkedList<>();

        for (final Map.Entry<String, ?> entry : metadata.entrySet()) {
            metadataList.add(new Metadata<>(entry.getKey(), entry.getValue()));
        }

        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<? extends EntityType, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return backend.addUserMetadata(authenticationToken, userStore, userUuid, metadataList);
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> removeUserMetadataBackendCaller(final ResourceIdentifier userStore, final UUID userUuid, final String metadataKey) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<? extends EntityType, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return backend.removeUserMetadata(authenticationToken, userStore, userUuid, metadataKey);
            }
        };
    }
}
