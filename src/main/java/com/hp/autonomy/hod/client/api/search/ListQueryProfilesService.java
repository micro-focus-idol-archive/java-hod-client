/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.search;

import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ListQueryProfilesService {

    String URL = "/api/sync/listqueryprofiles/v1";

    /**
     * Gets the names of the query profiles that currently exist for the given API key.
     * @param apiKey  The API key to use to access IDOL OnDemand.
     * @return  A QueryProfiles response object, that has a util method on it for getting the actual names.
     * @throws HodErrorException
     */
    @GET(URL)
    QueryProfiles listQueryProfiles(
            @Query("apiKey") String apiKey
    ) throws HodErrorException;
}
