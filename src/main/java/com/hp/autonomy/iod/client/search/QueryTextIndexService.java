/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.search;

import java.util.List;
import java.util.Map;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import retrofit.mime.TypedInput;

public interface QueryTextIndexService {

    String URL = "/sync/querytextindex/v1";

    @GET(URL)
    Documents queryTextIndexWithText(@Query("apiKey") String apiKey, @Query("text") String text, @Query("indexes") List<String> indexes, @QueryMap Map<String, Object> params);

    @GET(URL)
    Documents queryTextIndexWithReference(@Query("apiKey") String apiKey, @Query("reference") String reference, @Query("indexes") List<String> indexes, @QueryMap Map<String, Object> params);

    @GET(URL)
    Documents queryTextIndexWithUrl(@Query("apiKey") String apiKey, @Query("url") String url, @Query("indexes") List<String> indexes, @QueryMap Map<String, Object> params);

    @Multipart
    @POST(URL)
    Documents queryTextIndexWithFile(@Part("apiKey") String apiKey, @Part("file") TypedInput file, @Part("indexes") List<String> indexes, @PartMap Map<String, Object> params);

}
