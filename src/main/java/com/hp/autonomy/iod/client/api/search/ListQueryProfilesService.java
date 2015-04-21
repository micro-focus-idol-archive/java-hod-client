/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.error.IodErrorException;
import retrofit.http.GET;
import retrofit.http.Query;

import java.util.List;

public interface ListQueryProfilesService {

    String URL = "/api/sync/listqueryprofiles/v1";

    @GET(URL)
    List<String> listQueryProfiles(
            @Query("apiKey") String apiKey
    ) throws IodErrorException;
}
