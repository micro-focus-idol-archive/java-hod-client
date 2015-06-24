/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.job;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

interface JobBackend {

    /**
     * Get the status of a job using the given token
     * @param token The token to use to authenticate the request
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
     * Get the result of a job using the given token
     * @param token The token to use to authenticate the request
     * @param jobId The id of the job
     * @return An object containing the result of the job
     * @throws HodErrorException If an error occurred retrieving the result
     */
    @GET("/2/job/{jobId}/result")
    Response getJobResult(
        @Header("token") AuthenticationToken token,
        @Path("jobId") JobId jobId
    ) throws HodErrorException;

}
