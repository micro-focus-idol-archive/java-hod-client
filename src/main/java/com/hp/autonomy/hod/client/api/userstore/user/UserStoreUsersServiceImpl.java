/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
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
import java.util.*;

@Slf4j
public class UserStoreUsersServiceImpl implements UserStoreUsersService {
    private static final TypeReference<List<Metadata<TreeNode>>> LIST_OF_METADATA_TYPE = new TypeReference<List<Metadata<TreeNode>>>() {};
    private static final TypeReference<ListUsersResponse<Void>> NO_METADATA_LIST_TYPE = new TypeReference<ListUsersResponse<Void>>() {};

    private final ObjectMapper objectMapper;
    private final Requester<?, TokenType.Simple> requester;
    private final UserStoreUsersBackend backend;
    private final TypeFactory typeFactory;

    public UserStoreUsersServiceImpl(final HodServiceConfig<?, TokenType.Simple> config) {
        objectMapper = config.getObjectMapper();
        requester = config.getRequester();
        backend = config.getRestAdapter().create(UserStoreUsersBackend.class);
        typeFactory = config.getObjectMapper().getTypeFactory();
    }

    @Override
    public List<User<Void>> list(final ResourceIdentifier userStore, final boolean includeAccounts, final boolean includeGroups) throws HodErrorException {
        return requester.makeRequest(NO_METADATA_LIST_TYPE, listBackendCaller(userStore, false, includeAccounts, includeGroups)).getUsers();
    }

    @Override
    public List<User<Void>> list(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final ResourceIdentifier userStore,
        final boolean includeAccounts,
        final boolean includeGroups
    ) throws HodErrorException {
        return requester.makeRequest(tokenProxy, NO_METADATA_LIST_TYPE, listBackendCaller(userStore, false, includeAccounts, includeGroups)).getUsers();
    }

    @Override
    public <T> List<User<T>> listWithMetadata(
        final ResourceIdentifier userStore,
        final Class<T> metadataType,
        final boolean includeAccounts,
        final boolean includeGroups
    ) throws HodErrorException {
        final JavaType responseType = typeFactory.constructParametrizedType(ListUsersResponse.class, ListUsersResponse.class, metadataType);
        return requester.unsafeMakeRequest(responseType, listBackendCaller(userStore, true, includeAccounts, includeGroups));
    }

    @Override
    public <T> List<User<T>> listWithMetaData(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final ResourceIdentifier userStore,
        final Class<T> metadataType,
        final boolean includeAccounts,
        final boolean includeGroups
    ) throws HodErrorException {
        final JavaType responseType = typeFactory.constructParametrizedType(ListUsersResponse.class, ListUsersResponse.class, metadataType);
        return requester.unsafeMakeRequest(tokenProxy, responseType, listBackendCaller(userStore, true, includeAccounts, includeGroups));
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
    public Map<String, Object> getUserMetadata(final ResourceIdentifier userStore, final UUID userUuid, final Map<String, Class<?>> metadataTypes) throws HodErrorException {
        final List<Metadata<TreeNode>> metadataList = requester.makeRequest(LIST_OF_METADATA_TYPE, getUserMetadataBackendCaller(userStore, userUuid));
        return parseMetadata(metadataList, metadataTypes);
    }

    @Override
    public Map<String, Object> getUserMetadata(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceIdentifier userStore, final UUID userUuid, final Map<String, Class<?>> metadataTypes) throws HodErrorException {
        final List<Metadata<TreeNode>> metadataList = requester.makeRequest(tokenProxy, LIST_OF_METADATA_TYPE, getUserMetadataBackendCaller(userStore, userUuid));
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

    private Map<String, Object> parseMetadata(final List<Metadata<TreeNode>> metadataList, final Map<String, Class<?>> metadataTypes) {
        final Map<String, Object> metadata = new HashMap<>();

        for (final Metadata<TreeNode> metadataItem : metadataList) {
            final String key = metadataItem.getKey();
            final Class<?> clazz = metadataTypes.get(key);

            if (clazz != null) {
                try {
                    final Object value = objectMapper.treeToValue(metadataItem.getValue(), clazz);
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
