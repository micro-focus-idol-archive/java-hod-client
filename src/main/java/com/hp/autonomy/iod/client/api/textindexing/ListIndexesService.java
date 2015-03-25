package com.hp.autonomy.iod.client.api.textindexing;

import com.hp.autonomy.iod.client.error.IodErrorException;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

import java.util.List;
import java.util.Map;

public interface ListIndexesService {
    String URL = "/api/sync/listindexes/v1";

    @GET(URL)
    Indexes listIndexes(
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;

    @GET(URL)
    Indexes listIndexes(
            @Query("apiKey") String apiKey,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;

}
