/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodError;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.Action;
import com.hp.autonomy.hod.client.job.JobId;
import com.hp.autonomy.hod.client.job.JobStatus;
import com.hp.autonomy.hod.client.job.Status;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

import java.util.List;
import java.util.Map;

/**
 * Interface representing the CreateTextIndex API.
 */
public interface CreateTextIndexService {

    String URL = "/2/api/async/textindex/{indexName}/v1";

    /**
     * Create a text index using a token provided by a {@link retrofit.RequestInterceptor}
     * @param index The name of the index
     * @param flavor The flavor of the index
     * @param params Additional parameters to be sent as part of the request
     * @return A response with the index created and a message related to the attempt to create the index
     */
    @POST(URL)
    JobId createTextIndex(
            @Path("indexName") String index,
            @Query("flavor") IndexFlavor flavor,
            @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Create a text index using the given token
     * @param token The token to use to authenticate the request
     * @param index The name of the index
     * @param flavor The flavor of the index
     * @param params Additional parameters to be sent as part of the request
     * @return A response with the index created and a message related to the attempt to create the index
     */
    @POST(URL)
    JobId createTextIndex(
            @Header("token") AuthenticationToken token,
            @Path("indexName") String index,
            @Query("flavor") IndexFlavor flavor,
            @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Get the status of an CreateTextIndex job using a token provided by a {@link retrofit.RequestInterceptor}
     * @param jobId The id of the job
     * @return An object containing the status of the job along with the result if the job has finished
     * @throws HodErrorException If an error occurred retrieving the status
     */
    @GET("/2/job/{jobId}/status")
    CreateTextIndexJobStatus getJobStatus(
            @Path("jobId") JobId jobId
    ) throws HodErrorException;

    /**
     * Get the status of an CreateTextIndex job using the given token
     * @param token The token to use to authenticate the request
     * @param jobId The id of the job
     * @return An object containing the status of the job along with the result if the job has finished
     * @throws HodErrorException If an error occurred retrieving the status
     */
    @GET("/2/job/{jobId}/status")
    CreateTextIndexJobStatus getJobStatus(
            @Header("token") AuthenticationToken token,
            @Path("jobId") JobId jobId
    ) throws HodErrorException;

    /**
     * Get the result of an CreateTextIndex job using a token provided by a {@link retrofit.RequestInterceptor}
     * @param jobId The id of the job
     * @return An object containing the result of the job
     * @throws HodErrorException If an error occurred retrieving the result
     */
    @GET("/2/job/{jobId}/result")
    CreateTextIndexJobStatus getJobResult(
            @Path("jobId") JobId jobId
    ) throws HodErrorException;

    /**
     * Get the result of an CreateTextIndex job using the given token
     * @param token The token to use to authenticate the request
     * @param jobId The id of the job
     * @return An object containing the result of the job
     * @throws HodErrorException If an error occurred retrieving the result
     */
    @GET("/2/job/{jobId}/result")
    CreateTextIndexJobStatus getJobResult(
            @Header("token") AuthenticationToken token,
            @Path("jobId") JobId jobId
    ) throws HodErrorException;

    /**
     * {@link JobStatus} subtype which encodes the generic type for JSON parsing
     */
    class CreateTextIndexJobStatus extends JobStatus<CreateTextIndexResponse> {

        public CreateTextIndexJobStatus(
                @JsonProperty("jobID") final String jobId,
                @JsonProperty("status") final Status status,
                @JsonProperty("actions") final List<CreateTextIndexJobAction> actions
        ) {
            super(jobId, status, actions);
        }
    }

    /**
     * {@link Action} subtype which encodes the generic type for JSON parsing
     */
    class CreateTextIndexJobAction extends Action<CreateTextIndexResponse> {
        // need these @JsonProperty or it doesn't work
        public CreateTextIndexJobAction(
                @JsonProperty("action") final String action,
                @JsonProperty("status") final Status status,
                @JsonProperty("errors") final List<HodError> errors,
                @JsonProperty("result") final CreateTextIndexResponse result,
                @JsonProperty("version") final String version
        ) {
            super(action, status, errors, result, version);
        }
    }
}
