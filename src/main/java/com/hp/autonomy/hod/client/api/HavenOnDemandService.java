/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.JobId;
import com.hp.autonomy.hod.client.job.JobStatus;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
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

    String SYNC_URL_ONE = "/2/api/sync/{first}/v{version}";
    String SYNC_URL_TWO = "/2/api/sync/{first}/{second}/v{version}";
    String SYNC_URL_THREE = "/2/api/sync/{first}/{second}/{third}/v{version}";
    String ASYNC_URL_ONE = "/2/api/async/{first}/v{version}";
    String ASYNC_URL_TWO = "/2/api/async/{first}/{second}/v{version}";
    String ASYNC_URL_THREE = "/2/api/async/{first}/{second}/{third}/v{version}";

    /**
     * Sends a GET request to the given API
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token if not using a RequestInterceptor
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    @GET(SYNC_URL_ONE)
    Map<String, Object> get(
        @Path("first") String api,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a GET request to the given API
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token if not using a RequestInterceptor
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    @GET(SYNC_URL_TWO)
    Map<String, Object> get(
        @Path("first") String first,
        @Path("second") String second,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a GET request to the given API
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param third The third part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token if not using a RequestInterceptor
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    @GET(SYNC_URL_THREE)
    Map<String, Object> get(
        @Path("first") String first,
        @Path("second") String second,
        @Path("third") String third,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a GET request to the given API asynchronously
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @GET(ASYNC_URL_ONE)
    JobId getAsync(
        @Path("first") String api,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a GET request to the given API asynchronously
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @GET(ASYNC_URL_TWO)
    JobId getAsync(
        @Path("first") String first,
        @Path("second") String second,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a GET request to the given API asynchronously
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @GET(ASYNC_URL_THREE)
    JobId getAsync(
        @Path("first") String first,
        @Path("second") String second,
        @Path("third") String third,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a POST request to the given API
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    @POST(SYNC_URL_ONE)
    @Multipart
    Map<String, Object> post(
        @Path("first") String api,
        @Path("version") int version,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a POST request to the given API
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    @POST(SYNC_URL_TWO)
    @Multipart
    Map<String, Object> post(
        @Path("first") String first,
        @Path("second") String second,
        @Path("version") int version,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a POST request to the given API
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param third The third part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    @POST(SYNC_URL_THREE)
    @Multipart
    Map<String, Object> post(
        @Path("first") String first,
        @Path("second") String second,
        @Path("third") String third,
        @Path("version") int version,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a POST request to the given API asynchronously
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    @POST(ASYNC_URL_ONE)
    @Multipart
    JobId postAsync(
        @Path("api") String api,
        @Path("version") int version,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a POST request to the given API asynchronously
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    @POST(ASYNC_URL_TWO)
    @Multipart
    JobId postAsync(
        @Path("first") String first,
        @Path("second") String second,
        @Path("version") int version,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a POST request to the given API asynchronously
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param third The third part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    @POST(ASYNC_URL_THREE)
    @Multipart
    JobId postAsync(
        @Path("first") String first,
        @Path("second") String second,
        @Path("third") String third,
        @Path("version") int version,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a PUT request to the given API
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token if not using a RequestInterceptor
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    @PUT(SYNC_URL_ONE)
    Map<String, Object> put(
        @Path("first") String api,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a PUT request to the given API
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token if not using a RequestInterceptor
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    @PUT(SYNC_URL_TWO)
    Map<String, Object> put(
        @Path("first") String first,
        @Path("second") String second,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a PUT request to the given API
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param third The third part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token if not using a RequestInterceptor
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    @PUT(SYNC_URL_THREE)
    Map<String, Object> put(
        @Path("first") String first,
        @Path("second") String second,
        @Path("third") String third,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a PUT request to the given API asynchronously
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @PUT(ASYNC_URL_ONE)
    JobId putAsync(
        @Path("first") String api,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a PUT request to the given API asynchronously
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @PUT(ASYNC_URL_TWO)
    JobId putAsync(
        @Path("first") String first,
        @Path("second") String second,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a PUT request to the given API asynchronously
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param third The third part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @PUT(ASYNC_URL_THREE)
    JobId putAsync(
        @Path("first") String first,
        @Path("second") String second,
        @Path("third") String third,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a DELETE request to the given API
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token if not using a RequestInterceptor
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    @DELETE(SYNC_URL_ONE)
    Map<String, Object> delete(
        @Path("first") String api,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a DELETE request to the given API
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token if not using a RequestInterceptor
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    @DELETE(SYNC_URL_TWO)
    Map<String, Object> delete(
        @Path("first") String first,
        @Path("second") String second,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a DELETE request to the given API
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param third The third part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token if not using a RequestInterceptor
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    @DELETE(SYNC_URL_THREE)
    Map<String, Object> delete(
        @Path("first") String first,
        @Path("second") String second,
        @Path("third") String third,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a DELETE request to the given API asynchronously
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @DELETE(ASYNC_URL_ONE)
    JobId deleteAsync(
        @Path("first") String api,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a DELETE request to the given API asynchronously
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @DELETE(ASYNC_URL_TWO)
    JobId deleteAsync(
        @Path("first") String first,
        @Path("second") String second,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a DELETE request to the given API asynchronously
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @DELETE(ASYNC_URL_THREE)
    JobId deleteAsync(
        @Path("first") String first,
        @Path("second") String second,
        @Path("third") String third,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Gets that status of a job using a token provided by a {@link retrofit.RequestInterceptor}
     * @param jobId The ID of the job
     * @return An object containing the status of the job along with the result if the job has finished
     * @throws HodErrorException
     */
    @GET("/2/job/{jobId}/status")
    JobStatus<Map<String, Object>> getJobStatus(@Path("jobId") JobId jobId) throws HodErrorException;

    /**
     * Gets that status of a job using the given token
     * @param token The token to use to authenticate the request
     * @param jobId The ID of the job
     * @return An object containing the status of the job along with the result if the job has finished
     * @throws HodErrorException
     */
    @GET("/2/job/{jobId}/status")
    JobStatus<Map<String, Object>> getJobStatus(@Header("token") AuthenticationToken token, @Path("jobId") JobId jobId) throws HodErrorException;

    /**
     * Get the result of an AddToTextIndex job using a token provided by a {@link retrofit.RequestInterceptor}
     * @param jobId The id of the job
     * @return An object containing the result of the job
     * @throws HodErrorException If an error occurred retrieving the result
     */
    @GET("/2/job/{jobId}/result")
    JobStatus<Map<String, Object>> getJobResult(@Path("jobId") JobId jobId) throws HodErrorException;

    /**
     * Get the result of an AddToTextIndex job using an token provided by a {@link retrofit.RequestInterceptor}
     * @param token The token to use to authenticate the request
     * @param jobId The id of the job
     * @return An object containing the result of the job
     * @throws HodErrorException If an error occurred retrieving the result
     */
    @GET("/2/job/{jobId}/result")
    JobStatus<Map<String, Object>> getJobResult(@Query("token") AuthenticationToken token, @Path("jobId") JobId jobId) throws HodErrorException;

}
