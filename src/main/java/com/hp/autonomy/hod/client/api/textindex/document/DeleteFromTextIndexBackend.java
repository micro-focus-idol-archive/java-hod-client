/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.error.HodError;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.Action;
import com.hp.autonomy.hod.client.job.JobStatus;
import com.hp.autonomy.hod.client.job.Status;
import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.List;

interface DeleteFromTextIndexBackend {

    String URL = "/2/api/async/textindex/{indexName}/document/v1";

    /**
     * Deletes the documents with the given references using the given token
     * @param token The token to use to authenticate the request
     * @param index The index to delete from
     * @param references The references of the documents to delete
     * @return The job ID of the request
     * @throws HodErrorException If an error occurs with the request
     */
    @DELETE(URL)
    Response deleteReferencesFromTextIndex(
        @Header("token") final AuthenticationToken<?, ?> token,
        @Path("indexName") final ResourceIdentifier index,
        @Query("index_reference") final List<String> references
    ) throws HodErrorException;

    /**
     * Deletes all the documents from the given text index using the given API key
     * @param token The token to use to authenticate the request
     * @param index The index to delete from
     * @return The job ID of the request
     * @throws HodErrorException If an error occurs with the request
     */
    @DELETE(URL + "?delete_all_documents=true")
    Response deleteAllDocumentsFromTextIndex(
        @Header("token") final AuthenticationToken<?, ?> token,
        @Path("indexName") final ResourceIdentifier index
    ) throws HodErrorException;

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
            @JsonProperty("errors") final List<HodError> errors,
            @JsonProperty("result") final DeleteFromTextIndexResponse result,
            @JsonProperty("version") final String version
        ) {
            super(action, status, errors, result, version);
        }
    }

}
