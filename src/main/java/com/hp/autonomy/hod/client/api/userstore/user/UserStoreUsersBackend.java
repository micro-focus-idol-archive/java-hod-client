/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.List;
import java.util.Map;
import java.util.UUID;

interface UserStoreUsersBackend {

    String USER_STORE_VARIABLE = "user_store";
    String USER_UUID_VARIABLE = "user_uuid";

    String BASE_PATH = "/2/api/sync/user_store/{" + USER_STORE_VARIABLE + "}/user";
    String USER_BASE_PATH = BASE_PATH + "/{" + USER_UUID_VARIABLE + '}';
    String METADATA_BASE_PATH = USER_BASE_PATH + "/metadata";

    String V1 = "/v1";
    String TOKEN_HEADER = "token";

    @GET(BASE_PATH + V1)
    Response list(
        @Header(TOKEN_HEADER) AuthenticationToken<?, ?> token,
        @Path(USER_STORE_VARIABLE) ResourceIdentifier userStore,
        @Query("include_metadata") boolean includeMetadata,
        @Query("include_accounts") boolean includeAccounts,
        @Query("include_groups") boolean includeGroups
    ) throws HodErrorException;

    @POST(BASE_PATH + V1)
    @Multipart
    Response create(
        @Header(TOKEN_HEADER) AuthenticationToken<?, ?> token,
        @Path(USER_STORE_VARIABLE) ResourceIdentifier userStore,
        @Part("user_email") String email,
        @Part("on_success") String onSuccess,
        @Part("on_error") String onError,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    @DELETE(USER_BASE_PATH + V1)
    Response delete(
        @Header(TOKEN_HEADER) AuthenticationToken<?, ?> token,
        @Path(USER_STORE_VARIABLE) ResourceIdentifier userStore,
        @Path(USER_UUID_VARIABLE) UUID userUuid
    ) throws HodErrorException;

    @POST(USER_BASE_PATH + "/reset" + V1)
    @Multipart
    Response resetAuthentication(
        @Header(TOKEN_HEADER) AuthenticationToken<?, ?> token,
        @Path(USER_STORE_VARIABLE) ResourceIdentifier userStore,
        @Path(USER_UUID_VARIABLE) UUID userUuid,
        @Part("on_success") String onSuccess,
        @Part("on_error") String onError
    ) throws HodErrorException;

    @GET(USER_BASE_PATH + "/group" + V1)
    Response listUserGroups(
        @Header(TOKEN_HEADER) AuthenticationToken<?, ?> token,
        @Path(USER_STORE_VARIABLE) ResourceIdentifier userStore,
        @Path(USER_UUID_VARIABLE) UUID userUuid
    ) throws HodErrorException;

    @GET(METADATA_BASE_PATH + V1)
    Response getUserMetadata(
        @Header(TOKEN_HEADER) AuthenticationToken<?, ?> token,
        @Path(USER_STORE_VARIABLE) ResourceIdentifier userStore,
        @Path(USER_UUID_VARIABLE) UUID userUuid
    ) throws HodErrorException;

    @PATCH(METADATA_BASE_PATH + V1)
    @Multipart
    Response addUserMetadata(
        @Header(TOKEN_HEADER) AuthenticationToken<?, ?> token,
        @Path(USER_STORE_VARIABLE) ResourceIdentifier userStore,
        @Path(USER_UUID_VARIABLE) UUID userUuid,
        @Part("metadata") List<Metadata<?>> metadata
    ) throws HodErrorException;

    @DELETE(METADATA_BASE_PATH + "/{metadata_key}" + V1)
    Response removeUserMetadata(
        @Header(TOKEN_HEADER) AuthenticationToken<?, ?> token,
        @Path(USER_STORE_VARIABLE) ResourceIdentifier userStore,
        @Path(USER_UUID_VARIABLE) UUID userUuid,
        @Path("metadata_key") String metadataKey
    ) throws HodErrorException;

}
