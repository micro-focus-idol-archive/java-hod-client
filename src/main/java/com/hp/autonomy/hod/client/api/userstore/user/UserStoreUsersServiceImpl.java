/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.util.StatusResponse;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import retrofit.client.Response;

import java.net.URL;
import java.util.List;
import java.util.UUID;

public class UserStoreUsersServiceImpl implements UserStoreUsersService {
    private static final TypeReference<ListUsersResponse<Void>> NO_METADATA_LIST_TYPE = new TypeReference<ListUsersResponse<Void>>() {};

    private final Requester<?, TokenType.Simple> requester;
    private final UserStoreUsersBackend backend;
    private final TypeFactory typeFactory;

    public UserStoreUsersServiceImpl(final HodServiceConfig<?, TokenType.Simple> config) {
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
    ) throws HodErrorException{
        requester.makeRequest(tokenProxy, Void.class, deleteBackendCaller(userStore, userUuid));
    }

    @Override
    public void delete(
            final ResourceIdentifier userStore,
            final UUID userUuid
    ) throws HodErrorException{
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
}
