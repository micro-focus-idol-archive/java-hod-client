/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex;

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

/**
 * Interface representing the DeleteTextIndex API.
 */
interface DeleteTextIndexBackend {

    String SYNC_URL = "/2/api/sync/textindex/{indexName}/v1";
    String ASYNC_URL = "/2/api/async/textindex/{indexName}/v1";

    /**
     * Delete a text index using a hash code obtained by queryDeleteTextIndex using the given token
     * @param token The token to use to authenticate the request
     * @param index The name of the index
     * @return A response relaying information about the attempt to delete the index
     */
    @DELETE(SYNC_URL)
    Response initialDeleteTextIndex(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("indexName") ResourceIdentifier index
    ) throws HodErrorException;

    /**
     * Delete a text index using a hash code obtained by queryDeleteTextIndex using the given token
     * @param token The token to use to authenticate the request
     * @param index The name of the index
     * @param confirm The hash code to confirm the deletion
     * @return A response relaying information about the attempt to delete the index
     */
    @DELETE(ASYNC_URL)
    Response deleteTextIndex(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("indexName") ResourceIdentifier index,
        @Query("confirm") String confirm
    ) throws HodErrorException;

    /**
     * {@link JobStatus} subtype which encodes the generic type for JSON parsing
     */
    class DeleteTextIndexJobStatus extends JobStatus<DeleteTextIndexResponse> {

        public DeleteTextIndexJobStatus(
            @JsonProperty("jobID") final String jobId,
            @JsonProperty("status") final Status status,
            @JsonProperty("actions") final List<DeleteTextIndexJobAction> actions
        ) {
            super(jobId, status, actions);
        }
    }

    /**
     * {@link Action} subtype which encodes the generic type for JSON parsing
     */
    class DeleteTextIndexJobAction extends Action<DeleteTextIndexResponse> {
        // need these @JsonProperty or it doesn't work
        public DeleteTextIndexJobAction(
            @JsonProperty("action") final String action,
            @JsonProperty("status") final Status status,
            @JsonProperty("errors") final List<HodError> errors,
            @JsonProperty("result") final DeleteTextIndexResponse result,
            @JsonProperty("version") final String version
        ) {
            super(action, status, errors, result, version);
        }
    }
}
