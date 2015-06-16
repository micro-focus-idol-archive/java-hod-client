/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.job;

import com.hp.autonomy.hod.client.api.HavenOnDemandBackend;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodErrorException;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/**
 * PollingJobStatusRunnable for use with {@link HavenOnDemandBackend}
 */
public class HavenOnDemandJobStatusRunnable extends PollingJobStatusRunnable<Map<String, Object>> {

    private final HavenOnDemandBackend havenOnDemandBackend;

    /**
     * Creates a new HavenOnDemandJobStatusRunnable using the given API key
     * @param token The token to submit the job
     * @param jobId The ID of the job
     * @param callback The callback that will be called with the result
     * @param executorService The executor service responsible for running the runnable
     */
    public HavenOnDemandJobStatusRunnable(final HavenOnDemandBackend havenOnDemandBackend, final AuthenticationToken token, final JobId jobId, final HodJobCallback<Map<String, Object>> callback, final ScheduledExecutorService executorService) {
        super(token, jobId, callback, executorService);

        this.havenOnDemandBackend = havenOnDemandBackend;
    }

    /**
     * Creates a new HavenOnDemandJobStatusRunnable using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param jobId The ID of the job
     * @param callback The callback that will be called with the result
     * @param executorService The executor service responsible for running the runnable
     */
    public HavenOnDemandJobStatusRunnable(final HavenOnDemandBackend havenOnDemandBackend, final JobId jobId, final HodJobCallback<Map<String, Object>> callback, final ScheduledExecutorService executorService) {
        super(jobId, callback, executorService);

        this.havenOnDemandBackend = havenOnDemandBackend;
    }

    @Override
    public JobStatus<Map<String, Object>> getJobStatus(final JobId jobId) throws HodErrorException {
        return havenOnDemandBackend.getJobStatus(jobId);
    }

    @Override
    public JobStatus<Map<String, Object>> getJobStatus(final AuthenticationToken token, final JobId jobId) throws HodErrorException {
        return havenOnDemandBackend.getJobStatus(token, jobId);
    }
}
