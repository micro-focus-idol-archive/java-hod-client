/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.textindexing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.iod.client.error.IodError;
import com.hp.autonomy.iod.client.error.IodErrorException;
import com.hp.autonomy.iod.client.job.Action;
import com.hp.autonomy.iod.client.job.JobId;
import com.hp.autonomy.iod.client.job.JobStatus;
import com.hp.autonomy.iod.client.job.Status;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.List;

public interface DeleteFromTextIndexService {

    String URL = "/api/async/deletefromtextindex/v1";

    /**
     * Deletes the documents with the given references
     * @param apiKey The API key to use to authenticate the request
     * @param index The index to delete from
     * @param references The references of the documents to delete
     * @return The job ID of the request
     * @throws IodErrorException If an error occurs with the request
     */
    @GET(URL)
    JobId deleteReferencesFromTextIndex(
            @Query("apiKey") final String apiKey,
            @Query("index") final String index,
            @Query("index_reference") final List<String> references
    ) throws IodErrorException;

    /**
     * Deletes all the documents from the given text index
     * @param apiKey The API key to use to authenticate the request
     * @param index The index to delete from
     * @return The job ID of the request
     * @throws IodErrorException If an error occurs with the request
     */
    @GET(URL + "?delete_all_documents=true")
    JobId deleteAllDocumentsFromTextIndex(
            @Query("apiKey") final String apiKey,
            @Query("index") final String index
    ) throws IodErrorException;

    /**
     * Get the status of an DeleteFromTextIndex job
     * @param apiKey The API key to use to authenticate the request
     * @param jobId The id of the job
     * @return An object containing the status of the job along with the result if the job has finished
     * @throws IodErrorException If an error occurred retrieving the status
     */
    @GET("/job/status/{jobId}")
    DeleteFromTextIndexJobStatus getJobStatus(
            @Query("apiKey") String apiKey,
            @Path("jobId") JobId jobId
    ) throws IodErrorException;

    /**
     * Get the result of an DeleteFromTextIndex job
     * @param apiKey The API key to use to authenticate the request
     * @param jobId The id of the job
     * @return An object containing the result of the job
     * @throws IodErrorException If an error occurred retrieving the result
     */
    @GET("/job/result/{jobId}")
    DeleteFromTextIndexJobStatus getJobResult(
            @Query("apiKey") String apiKey,
            @Path("jobId") JobId jobId
    ) throws IodErrorException;

    /**
     * {@link JobStatus} subtype which encodes the generic type for JSON parsing
     */
    class DeleteFromTextIndexJobStatus extends JobStatus<DeleteFromTextIndexResponse> {

        public DeleteFromTextIndexJobStatus(
                @JsonProperty("jobID") final String jobId,
                @JsonProperty("status") final Status status,
                @JsonProperty("actions") final List<DeleteFromTextIndexJobStatusAction> actions
        ) {
            super(jobId, status, actions);
        }
    }

    /**
     * {@link Action} subtype which encodes the generic type for JSON parsing
     */
    class DeleteFromTextIndexJobStatusAction extends Action<DeleteFromTextIndexResponse> {
        // need these @JsonProperty or it doesn't work
        public DeleteFromTextIndexJobStatusAction(
                @JsonProperty("action") final String action,
                @JsonProperty("status") final Status status,
                @JsonProperty("errors") final List<IodError> errors,
                @JsonProperty("result") final DeleteFromTextIndexResponse result,
                @JsonProperty("version") final String version
        ) {
            super(action, status, errors, result, version);
        }
    }

}
