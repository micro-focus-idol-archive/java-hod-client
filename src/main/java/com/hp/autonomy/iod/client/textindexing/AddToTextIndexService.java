/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.textindexing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.iod.client.error.IodError;
import com.hp.autonomy.iod.client.error.IodErrorException;
import com.hp.autonomy.iod.client.job.Action;
import com.hp.autonomy.iod.client.job.JobId;
import com.hp.autonomy.iod.client.job.JobStatus;
import com.hp.autonomy.iod.client.job.Status;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedInput;

import java.util.List;
import java.util.Map;

/**
 * Interface representing the Add to Text Index API
 */
public interface AddToTextIndexService {

    String URL = "/api/async/addtotextindex/v1";

    /**
     * Index JSON documents into IDOL OnDemand
     * @param apiKey The API key to use to authenticate the request
     * @param documents A collection of objects to convert to JSON
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @return The job ID of the request
     * @throws IodErrorException
     */
    @POST(URL)
    @Multipart
    JobId addJsonToTextIndex(
            @Part("apiKey") String apiKey,
            @Part("json") Documents<?> documents,
            @Part("index") String index,
            @PartMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Index JSON documents into IDOL OnDemand
     * @param apiKey The API key to use to authenticate the request
     * @param file A file containing the content of the document
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @return The job ID of the request
     * @throws IodErrorException
     */
    @POST(URL)
    @Multipart
    JobId addFileToTextIndex(
            @Part("apiKey") String apiKey,
            @Part("file") TypedInput file,
            @Part("index") String index,
            @PartMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Index JSON documents into IDOL OnDemand
     * @param apiKey The API key to use to authenticate the request
     * @param reference An object store reference pointing at a file to be used for document content
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @return The job ID of the request
     * @throws IodErrorException
     */
    @POST(URL)
    @Multipart
    JobId addReferenceToTextIndex(
            @Part("apiKey") String apiKey,
            @Part("reference") String reference,
            @Part("index") String index,
            @PartMap Map<String, Object> params
    ) throws IodErrorException;

    /**
     * Index JSON documents into IDOL OnDemand
     * @param apiKey The API key to use to authenticate the request
     * @param url A publicly accessible url containing the document content
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @return The job ID of the request
     * @throws IodErrorException
     */
    @POST(URL)
    @Multipart
    JobId addUrlToTextIndex(
            @Part("apiKey") String apiKey,
            @Part("url") String url,
            @Part("index") String index,
            @PartMap Map<String, Object> params
    ) throws IodErrorException;

    @GET("/job/status/{jobId}")
    AddToTextIndexJobStatus getJobStatus(
            @Query("apiKey") String apiKey,
            @Path("jobId") JobId jobId
    ) throws IodErrorException;

    @GET("/job/result/{jobId}")
    AddToTextIndexJobStatus getJobResult(
            @Query("apiKey") String apiKey,
            @Path("jobId") JobId jobId
    ) throws IodErrorException;

    class AddToTextIndexJobStatus extends JobStatus<AddToTextIndexResponse> {

        public AddToTextIndexJobStatus(
                @JsonProperty("jobID") final String jobId,
                @JsonProperty("status") final Status status,
                @JsonProperty("actions") final List<AddToTextIndexJobStatusAction> actions
        ) {
            super(jobId, status, actions);
        }
    }

    class AddToTextIndexJobStatusAction extends Action<AddToTextIndexResponse> {
        // need these @JsonProperty or it doesn't work
        public AddToTextIndexJobStatusAction(
                @JsonProperty("action") final String action,
                @JsonProperty("status") final Status status,
                @JsonProperty("errors") final List<IodError> errors,
                @JsonProperty("result") final AddToTextIndexResponse result,
                @JsonProperty("version") final String version
        ) {
            super(action, status, errors, result, version);
        }
    }

}
