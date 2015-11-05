/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.developer;

import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.http.*;

import java.util.UUID;

interface DeveloperBackend {

    String AUTH_MODE_PART = "mode";
    String DEVELOPER_UUID_VARIABLE = "developer_uuid";

    @POST("/2/developer/{" + DEVELOPER_UUID_VARIABLE + "}/authentication")
    @Multipart
    AddDeveloperAuthenticationResponse addAuthentication(
        @Header("token") String signature,
        @Path(DEVELOPER_UUID_VARIABLE) UUID developerUuid,
        @Part(AUTH_MODE_PART) String mode
    ) throws HodErrorException;

}
