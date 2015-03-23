package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.error.IodErrorException;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

import java.util.List;
import java.util.Map;

public interface GetContentService {

    String URL = "/api/sync/getcontent/v1";

    @GET(URL)
    Documents getContent(
            @Query("apiKey") String apiKey,
            @Query("indexes") String indexes,
            @Query("index_reference") List<String> indexReference,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;
}
