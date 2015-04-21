/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.error.IodErrorException;
import retrofit.http.GET;
import retrofit.http.Query;

public interface RetrieveQueryProfileService {

    String URL = "/api/sync/retrievequeryprofile/v1";

    @GET(URL)
    QueryProfile retrieveQueryProfile(
            @Query("apiKey") String apiKey,
            @Query("query_profile") String name
    ) throws IodErrorException;
}
