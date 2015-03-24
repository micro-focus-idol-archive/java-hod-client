package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.error.IodErrorException;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import retrofit.mime.TypedOutput;

import java.util.Map;

public interface FindRelatedConceptsService {
    String URL = "/api/sync/findrelatedconcepts/v1";

    /**
     * Query IDOL OnDemand for documents matching query text
     * @param apiKey The API key to use to authenticate the request
     * @param text The query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @GET(URL)
    Entities findRelatedConceptsWithText(
            @Query("apiKey") String apiKey,
            @Query("text") String text,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Query IDOL OnDemand for documents using query text from an object store object
     * @param apiKey The API key to use to authenticate the request
     * @param reference An IDOL OnDemand reference obtained from either the Expand Container or Store Object API.
     *                  The contents of the object will be used as the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @GET(URL)
    Entities findRelatedConceptsWithReference(
            @Query("apiKey") String apiKey,
            @Query("reference") String reference,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Query IDOL OnDemand for documents using query text from a url
     * @param apiKey The API key to use to authenticate the request
     * @param url A publicly accessible HTTP URL from which the query text can be retrieved
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @GET(URL)
    Entities findRelatedConceptsWithUrl(
            @Query("apiKey") String apiKey,
            @Query("url") String url,
            @QueryMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Query IDOL OnDemand for documents using query text in a file
     * @param apiKey The API key to use to authenticate the request
     * @param file A file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of documents that match the query text
     */
    @Multipart
    @POST(URL)
    Entities findRelatedConceptsWithFile(
            @Part("apiKey") String apiKey,
            @Part("file") TypedOutput file,
            @PartMap Map<String, Object> params
    ) throws IodErrorException;
}
