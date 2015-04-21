/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.error.IodErrorException;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Query;

public interface UpdateQueryProfileService {

    String URL = "/api/sync/updatequeryprofile/v1";

    @POST(URL)
    @Multipart
    QueryProfileStatusResponse updateQueryProfile(
            @Query("apiKey") String apiKey,
            @Query("query_profile") String name,
            @Query("config") QueryProfileConfig config
    ) throws IodErrorException;
}
