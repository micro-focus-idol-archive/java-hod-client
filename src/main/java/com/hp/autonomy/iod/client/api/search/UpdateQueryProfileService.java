/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.error.IodErrorException;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

public interface UpdateQueryProfileService {

    String URL = "/api/sync/updatequeryprofile/v1";

    @POST(URL)
    @Multipart
    QueryProfileStatusResponse updateQueryProfile(
            @Part("apiKey") String apiKey,
            @Part("query_profile") String name,
            @Part("config") QueryProfileConfig config
    ) throws IodErrorException;
}
