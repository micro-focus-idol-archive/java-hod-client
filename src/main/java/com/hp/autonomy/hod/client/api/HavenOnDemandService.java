/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api;

import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.JobId;
import com.hp.autonomy.hod.client.job.JobStatus;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PartMap;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

import java.util.Map;

/**
 * Service for calling HP Haven OnDemand APIs which do not have a dedicated service. These methods provide no assistance
 * with calling HP Haven OnDemand, so prefer dedicated services where available
 */
public interface HavenOnDemandService {

    String SYNC_URL = "/1/api/sync/{api}/v1";
    String ASYNC_URL = "/1/api/async/{api}/v1";

    /**
     * Sends a GET request to the given API
     * @param api The name of the API
     * @param params The query parameters sent to the API. These should include an API key if not using a RequestInterceptor
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    @GET(SYNC_URL)
    Map<String, Object> get(@Path("api") String api, @QueryMap Map<String, Object> params) throws HodErrorException;

    /**
     * Sends a GET request to the given API asynchronously
     * @param api The name of the API
     * @param params The query parameters sent to the API. These should include an API key if not using a RequestInterceptor
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @GET(ASYNC_URL)
    JobId getAsync(@Path("api") String api, @QueryMap Map<String, Object> params) throws HodErrorException;

    /**
     * Sends a POST request to the given API
     * @param api The name of the API
     * @param params The query parameters sent to the API. These should include an API key if not using a RequestInterceptor
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    @POST(SYNC_URL)
    @Multipart
    Map<String, Object> post(@Path("api") String api, @PartMap Map<String, Object> params) throws HodErrorException;

    /**
     * Sends a POST request to the given API asynchronously
     * @param api The name of the API
     * @param params The query parameters sent to the API. These should include an API key if not using a RequestInterceptor
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    @POST(ASYNC_URL)
    @Multipart
    JobId postAsync(@Path("api") String api, @PartMap Map<String, Object> params) throws HodErrorException;

    /**
     * Gets that status of a job using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param jobId The ID of the job
     * @return An object containing the status of the job along with the result if the job has finished
     * @throws HodErrorException
     */
    @GET("/1/job/status/{jobId}")
    JobStatus<Map<String, Object>> getJobStatus(@Path("jobId") JobId jobId) throws HodErrorException;

    /**
     * Gets that status of a job using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param jobId The ID of the job
     * @return An object containing the status of the job along with the result if the job has finished
     * @throws HodErrorException
     */
    @GET("/1/job/status/{jobId}")
    JobStatus<Map<String, Object>> getJobStatus(@Query("apiKey") String apiKey, @Path("jobId") JobId jobId) throws HodErrorException;

    /**
     * Get the result of an AddToTextIndex job using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param jobId The id of the job
     * @return An object containing the result of the job
     * @throws HodErrorException If an error occurred retrieving the result
     */
    @GET("/1/job/result/{jobId}")
    JobStatus<Map<String, Object>> getJobResult(@Path("jobId") JobId jobId) throws HodErrorException;

    /**
     * Get the result of an AddToTextIndex job using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param apiKey The API key to use to authenticate the request
     * @param jobId The id of the job
     * @return An object containing the result of the job
     * @throws HodErrorException If an error occurred retrieving the result
     */
    @GET("/1/job/result/{jobId}")
    JobStatus<Map<String, Object>> getJobResult(@Query("apiKey") String apiKey, @Path("jobId") JobId jobId) throws HodErrorException;

}
