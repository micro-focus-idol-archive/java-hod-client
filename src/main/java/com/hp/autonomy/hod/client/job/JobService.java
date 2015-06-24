/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.job;

import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

/**
 * Service for interacting with the job APIs
 * @param <T> The expected type of the result of jobs retrieved from this service
 */
public interface JobService<T extends JobStatus<?>> {

    /**
     * Retrieve the status of a job using a token proxy provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param jobId The id of the job
     * @return The status of the job
     * @throws HodErrorException
     */
    T getJobStatus(JobId jobId) throws HodErrorException;

    /**
     * Retrieve the status of a job using the given token proxy
     * @param jobId The id of the job
     * @param tokenProxy The token proxy to use
     * @return The status of the job
     * @throws HodErrorException
     */
    T getJobStatus(TokenProxy tokenProxy, JobId jobId) throws HodErrorException;

    /**
     * Retrieve the status of a job using a token proxy provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param jobId The id of the job
     * @return The status of the job
     * @throws HodErrorException
     */
    T getJobResult(JobId jobId) throws HodErrorException;

    /**
     * Retrieve the status of a job using the given token proxy
     * @param jobId The id of the job
     * @param tokenProxy The token proxy to use
     * @return The status of the job
     * @throws HodErrorException
     */
    T getJobResult(TokenProxy tokenProxy, JobId jobId) throws HodErrorException;

}
