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
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.List;

public interface DeleteFromTextIndexService {

    String URL = "/api/async/deletefromtextindex/v1";

    @GET(URL)
    public JobId deleteReferencesFromTextIndex(
            @Query("apiKey") final String apiKey,
            @Query("index") final String index,
            @Query("index_reference") final List<String> references
    ) throws IodErrorException;

    @GET(URL + "?delete_all_documents=true")
    public JobId deleteAllDocumentsFromTextIndex(
            @Query("apiKey") final String apiKey,
            @Query("index") final String index
    ) throws IodErrorException;

    @GET("/job/status/{jobId}")
    DeleteFromTextIndexJobStatus getJobStatus(
            @Query("apiKey") String apiKey,
            @Path("jobId") JobId jobId
    ) throws IodErrorException;

    @GET("/job/result/{jobId}")
    DeleteFromTextIndexJobStatus getJobResult(
            @Query("apiKey") String apiKey,
            @Path("jobId") JobId jobId
    ) throws IodErrorException;

    class DeleteFromTextIndexJobStatus extends JobStatus<DeleteFromTextIndexResponse> {

        public DeleteFromTextIndexJobStatus(
                @JsonProperty("jobID") final String jobId,
                @JsonProperty("status") final Status status,
                @JsonProperty("actions") final List<DeleteFromTextIndexJobStatusAction> actions
        ) {
            super(jobId, status, actions);
        }
    }

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
