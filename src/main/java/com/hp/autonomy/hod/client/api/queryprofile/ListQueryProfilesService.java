/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.http.GET;
import retrofit.http.Header;

public interface ListQueryProfilesService {

    String URL = "/2/api/sync/listqueryprofiles/v1";

    /**
     * Gets the names of the query profiles that currently exist using a token provided by a {@link retrofit.RequestInterceptor}
     * @return  A QueryProfiles response object, that has a util method on it for getting the actual names.
     * @throws HodErrorException
     */
    @GET(URL)
    QueryProfiles listQueryProfiles() throws HodErrorException;

    /**
     * Gets the names of the query profiles that currently exist using the given token
     * @param token  The token to use to access IDOL OnDemand.
     * @return  A QueryProfiles response object, that has a util method on it for getting the actual names.
     * @throws HodErrorException
     */
    @GET(URL)
    QueryProfiles listQueryProfiles(
        @Header("token") AuthenticationToken token
    ) throws HodErrorException;
}
