/*
 * Copyright 2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.analysis.autocomplete;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

interface AutocompleteBackend {

    @GET("/2/api/sync/autocomplete/v1")
    Response getSuggestions(
        @Header("token") final AuthenticationToken<?, ?> token,
        @Query("text") final String text
    ) throws HodErrorException;

}
