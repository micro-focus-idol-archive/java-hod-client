/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodError;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.Action;
import com.hp.autonomy.hod.client.job.JobId;
import com.hp.autonomy.hod.client.job.JobStatus;
import com.hp.autonomy.hod.client.job.Status;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Path;
import retrofit.mime.TypedOutput;

import java.util.List;
import java.util.Map;

/**
 * Interface representing the Add to Text Index API
 */
public interface AddToTextIndexBackend {

    String URL = "/2/api/async/textindex/{indexName}/document/v1";

    /**
     * Index JSON documents into HP Haven OnDemand using the given token
     * @param token The token to use to authenticate the request
     * @param documents A collection of objects to convert to JSON
     * @param indexName The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @POST(URL)
    @Multipart
    Response addJsonToTextIndex(
        @Header("token") AuthenticationToken token,
        @Part("json") Documents<?> documents,
        @Path("indexName") String indexName,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Index a file into HP Haven OnDemand using the given token
     * @param token The API key to use to authenticate the request
     * @param file A file containing the content of the document
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @POST(URL)
    @Multipart
    Response addFileToTextIndex(
        @Header("token") AuthenticationToken token,
        @Part("file") TypedOutput file,
        @Path("indexName") String index,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Index an object store object into HP Haven OnDemand using the given token
     * @param token The token to use to authenticate the request
     * @param reference An object store reference pointing at a file to be used for document content
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @POST(URL)
    @Multipart
    Response addReferenceToTextIndex(
        @Header("token") AuthenticationToken token,
        @Part("reference") String reference,
        @Path("indexName") String index,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Index a publicly accessible into HP Haven OnDemand using the given token
     * @param token The token to use to authenticate the request
     * @param url A publicly accessible url containing the document content
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @POST(URL)
    @Multipart
    Response addUrlToTextIndex(
        @Header("token") AuthenticationToken token,
        @Part("url") String url,
        @Path("indexName") String index,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Get the status of an AddToTextIndex job using a token provided by a {@link retrofit.RequestInterceptor}
     * @param jobId The id of the job
     * @return An object containing the status of the job along with the result if the job has finished
     * @throws HodErrorException If an error occurred retrieving the status
     */
    @GET("/2/job/{jobId}/status")
    Response getJobStatus(
        @Path("jobId") JobId jobId
    ) throws HodErrorException;

    /**
     * Get the status of an AddToTextIndex job using the given token
     * @param token The API key to use to authenticate the request
     * @param jobId The id of the job
     * @return An object containing the status of the job along with the result if the job has finished
     * @throws HodErrorException If an error occurred retrieving the status
     */
    @GET("/2/job/{jobId}/status")
    Response getJobStatus(
        @Header("token") AuthenticationToken token,
        @Path("jobId") JobId jobId
    ) throws HodErrorException;

    /**
     * Get the result of an AddToTextIndex job using a token provided by a {@link retrofit.RequestInterceptor}
     * @param jobId The id of the job
     * @return An object containing the result of the job
     * @throws HodErrorException If an error occurred retrieving the result
     */
    @GET("/2/job/{jobId}/result")
    Response getJobResult(
        @Path("jobId") JobId jobId
    ) throws HodErrorException;

    /**
     * Get the result of an AddToTextIndex job using the given token
     * @param token The API key to use to authenticate the request
     * @param jobId The id of the job
     * @return An object containing the result of the job
     * @throws HodErrorException If an error occurred retrieving the result
     */
    @GET("/2/job/{jobId}/result")
    Response getJobResult(
        @Header("token") AuthenticationToken token,
        @Path("jobId") JobId jobId
    ) throws HodErrorException;

    /**
     * {@link JobStatus} subtype which encodes the generic type for JSON parsing
     */
    class AddToTextIndexJobStatus extends JobStatus<AddToTextIndexResponse> {

        public AddToTextIndexJobStatus(
            @JsonProperty("jobID") final String jobId,
            @JsonProperty("status") final Status status,
            @JsonProperty("actions") final List<AddToTextIndexJobStatusAction> actions
        ) {
            super(jobId, status, actions);
        }
    }

    /**
     * {@link Action} subtype which encodes the generic type for JSON parsing
     */
    class AddToTextIndexJobStatusAction extends Action<AddToTextIndexResponse> {
        // need these @JsonProperty or it doesn't work
        public AddToTextIndexJobStatusAction(
            @JsonProperty("action") final String action,
            @JsonProperty("status") final Status status,
            @JsonProperty("errors") final List<HodError> errors,
            @JsonProperty("result") final AddToTextIndexResponse result,
            @JsonProperty("version") final String version
        ) {
            super(action, status, errors, result, version);
        }
    }

}
