/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

public interface RetrieveQueryProfileService {

    String URL = "/2/api/sync/queryprofile/{queryProfileName}/v1";

    /**
     * Retrieves a query profile using a token provided by a {@link retrofit.RequestInterceptor}
     * @param name The name of the query profile
     * @return The query profile
     * @throws HodErrorException
     */
    @GET(URL)
    QueryProfile retrieveQueryProfile(
            @Path("queryProfileName") String name
    ) throws HodErrorException;

    /**
     * Retrieves a query profile using the given token
     * @param token The token to use to authenticate the request
     * @param name The name of the query profile
     * @return The query profile
     * @throws HodErrorException
     */
    @GET(URL)
    QueryProfile retrieveQueryProfile(
            @Header("token") AuthenticationToken token,
            @Path("queryProfileName") String name
    ) throws HodErrorException;
}
