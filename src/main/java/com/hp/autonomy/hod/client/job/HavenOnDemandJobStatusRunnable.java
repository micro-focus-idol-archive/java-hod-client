/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.job;

import com.hp.autonomy.hod.client.api.HavenOnDemandService;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodErrorException;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/**
 * PollingJobStatusRunnable for use with {@link HavenOnDemandService}
 */
public class HavenOnDemandJobStatusRunnable extends PollingJobStatusRunnable<Map<String, Object>> {

    private final HavenOnDemandService havenOnDemandService;

    /**
     * Creates a new HavenOnDemandJobStatusRunnable using the given API key
     * @param token The token to submit the job
     * @param jobId The ID of the job
     * @param callback The callback that will be called with the result
     * @param executorService The executor service responsible for running the runnable
     */
    public HavenOnDemandJobStatusRunnable(final HavenOnDemandService havenOnDemandService, final AuthenticationToken token, final JobId jobId, final HodJobCallback<Map<String, Object>> callback, final ScheduledExecutorService executorService) {
        super(token, jobId, callback, executorService);

        this.havenOnDemandService = havenOnDemandService;
    }

    /**
     * Creates a new HavenOnDemandJobStatusRunnable using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param jobId The ID of the job
     * @param callback The callback that will be called with the result
     * @param executorService The executor service responsible for running the runnable
     */
    public HavenOnDemandJobStatusRunnable(final HavenOnDemandService havenOnDemandService, final JobId jobId, final HodJobCallback<Map<String, Object>> callback, final ScheduledExecutorService executorService) {
        super(jobId, callback, executorService);

        this.havenOnDemandService = havenOnDemandService;
    }

    @Override
    public JobStatus<Map<String, Object>> getJobStatus(final JobId jobId) throws HodErrorException {
        return havenOnDemandService.getJobStatus(jobId);
    }

    @Override
    public JobStatus<Map<String, Object>> getJobStatus(final AuthenticationToken token, final JobId jobId) throws HodErrorException {
        return havenOnDemandService.getJobStatus(token, jobId);
    }
}
