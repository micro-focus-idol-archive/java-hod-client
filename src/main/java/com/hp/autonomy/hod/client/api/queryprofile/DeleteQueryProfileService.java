/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.http.DELETE;
import retrofit.http.Header;
import retrofit.http.Path;

/**
 * Interface representing the DeleteQueryProfile API.
 */
public interface DeleteQueryProfileService {

    String URL = "/2/api/sync/queryprofile/{queryProfileName}/v1";

    /**
     * Delete an existing query profile in HP Haven OnDemand using a token provided by a {@link retrofit.RequestInterceptor}
     * @param name The name of the query profile
     * @return The name of the deleted query profile
     */
    @DELETE(URL)
    QueryProfileStatusResponse deleteQueryProfile(
        @Path("queryProfileName") String name
    ) throws HodErrorException;

    /**
     * Delete an existing query profile in HP Haven OnDemand using the given token
     * @param token The API key to use to authenticate the request
     * @param name The name of the query profile
     * @return The name of the deleted query profile
     */
    @DELETE(URL)
    QueryProfileStatusResponse deleteQueryProfile(
        @Header("token") AuthenticationToken token,
        @Path("queryProfileName") String name
    ) throws HodErrorException;
}
