package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.error.IodErrorException;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

import java.util.List;
import java.util.Map;

/**
 * Interface representing the QueryTextIndex API.
 */
public interface GetContentService {

    String URL = "/api/sync/getcontent/v1";

    /**
     * Query IDOL OnDemand for documents matching query text
     * @param apiKey The API key to use to authenticate the request
     * @param indexReference The reference list of the documents you want to view
     * @param indexes The index the document resides in
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @GET(URL)
    Documents getContent(
            @Query("apiKey") String apiKey,
            @Query("index_reference") List<String> indexReference,
            @Query("indexes") String indexes,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;
}
