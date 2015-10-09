/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.application;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;

interface ApplicationUsersBackend {

    @GET("/2/api/sync/application/user/v1")
    Response getUsers(@Header("token") AuthenticationToken<?, ?> token);

}
