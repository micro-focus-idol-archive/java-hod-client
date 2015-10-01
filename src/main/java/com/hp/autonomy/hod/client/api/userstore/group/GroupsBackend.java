/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.group;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.client.Response;
import retrofit.http.*;

import java.util.Map;

interface GroupsBackend {

    String USER_STORE_VARIABLE = "user_store";
    String BASE_PATH = "/2/api/sync/user_store/{" + USER_STORE_VARIABLE + "}/group";
    String V1 = "/v1";
    String TOKEN_HEADER = "token";

    @GET(BASE_PATH + V1)
    Response list(
        @Header(TOKEN_HEADER) AuthenticationToken token,
        @Path(USER_STORE_VARIABLE) ResourceIdentifier userStore
    ) throws HodErrorException;

    @GET(BASE_PATH + "/{group}" + V1)
    Response getInfo(
        @Header(TOKEN_HEADER) AuthenticationToken token,
        @Path(USER_STORE_VARIABLE) ResourceIdentifier userStore,
        @Path("group") String group
    ) throws HodErrorException;

    @POST(BASE_PATH + V1)
    @Multipart
    Response create(
        @Header(TOKEN_HEADER) AuthenticationToken token,
        @Path(USER_STORE_VARIABLE) ResourceIdentifier userStore,
        @Part("group_name") String name,
        @PartMap Map<String, Object> hierarchyParameters
    ) throws HodErrorException;

    @DELETE(BASE_PATH + "/{name}" + V1)
    Response delete(
        @Header(TOKEN_HEADER) AuthenticationToken token,
        @Path(USER_STORE_VARIABLE) ResourceIdentifier userStore,
        @Path("name") String name
    ) throws HodErrorException;

    @POST(BASE_PATH + "/{group}/user" + V1)
    @Multipart
    Response assignUser(
        @Header(TOKEN_HEADER) AuthenticationToken token,
        @Path(USER_STORE_VARIABLE) ResourceIdentifier userStore,
        @Path("group") String group,
        @Part("user_uuid") String userUuid
    ) throws HodErrorException;

    @DELETE(BASE_PATH + "/{group}/user/{user_uuid}" + V1)
    Response removeUser(
        @Header(TOKEN_HEADER) AuthenticationToken token,
        @Path(USER_STORE_VARIABLE) ResourceIdentifier userStore,
        @Path("group") String group,
        @Path("user_uuid") String userUuid
    ) throws HodErrorException;

    @POST(BASE_PATH + "/link" + V1)
    @Multipart
    Response link(
        @Header(TOKEN_HEADER) AuthenticationToken token,
        @Path(USER_STORE_VARIABLE) ResourceIdentifier userStore,
        @Part("parent_group_name") String parent,
        @Part("child_group_name") String child
    ) throws HodErrorException;

    @DELETE(BASE_PATH + "/{parent_group_name}/link" + V1)
    Response unlink(
        @Header(TOKEN_HEADER) AuthenticationToken token,
        @Path(USER_STORE_VARIABLE) ResourceIdentifier userStore,
        @Path("parent_group_name") String parent,
        @Query("child_group_name") String child
    ) throws HodErrorException;

}
