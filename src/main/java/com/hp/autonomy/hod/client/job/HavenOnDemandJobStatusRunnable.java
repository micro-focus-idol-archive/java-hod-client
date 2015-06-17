/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.job;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.util.concurrent.ScheduledExecutorService;

/**
 * PollingJobStatusRunnable for use with {@link com.hp.autonomy.hod.client.api.HavenOnDemandService}
 */
public class HavenOnDemandJobStatusRunnable<ReturnType, JobStatusType extends JobStatus<ReturnType>> extends PollingJobStatusRunnable<ReturnType> {

    private final JobService<JobStatusType> jobService;

    /**
     * Creates a new HavenOnDemandJobStatusRunnable using the given API key
     * @param tokenProxy The token proxy used to submit the job
     * @param jobId The ID of the job
     * @param callback The callback that will be called with the result
     * @param executorService The executor service responsible for running the runnable
     */
    public HavenOnDemandJobStatusRunnable(final JobService<JobStatusType> jobService, final TokenProxy tokenProxy, final JobId jobId, final HodJobCallback<ReturnType> callback, final ScheduledExecutorService executorService) {
        super(tokenProxy, jobId, callback, executorService);

        this.jobService = jobService;
    }

    @Override
    public JobStatus<ReturnType> getJobStatus(final JobId jobId) throws HodErrorException {
        return jobService.getJobStatus(jobId);
    }

    @Override
    public JobStatus<ReturnType> getJobStatus(final AuthenticationToken token, final JobId jobId) throws HodErrorException {
        throw new UnsupportedOperationException();
    }

    @Override
    public JobStatus<ReturnType> getJobStatus(final TokenProxy tokenProxy, final JobId jobId) throws HodErrorException {
        return jobService.getJobStatus(tokenProxy, jobId);
    }
}
