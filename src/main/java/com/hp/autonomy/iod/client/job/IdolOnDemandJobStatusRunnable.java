/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.job;

import com.hp.autonomy.iod.client.api.IdolOnDemandService;
import com.hp.autonomy.iod.client.error.IodErrorException;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/**
 * PollingJobStatusRunnable for use with {@link com.hp.autonomy.iod.client.api.IdolOnDemandService}
 */
public class IdolOnDemandJobStatusRunnable extends PollingJobStatusRunnable<Map<String, Object>> {

    private final IdolOnDemandService idolOnDemandService;

    /**
     * Creates a new IdolOnDemandJobStatusRunnable using the given API key
     * @param apiKey The API key used to submit the job
     * @param jobId The ID of the job
     * @param callback The callback that will be called with the result
     * @param executorService The executor service responsible for running the runnable
     */
    public IdolOnDemandJobStatusRunnable(final IdolOnDemandService idolOnDemandService, final String apiKey, final JobId jobId, final IodJobCallback<Map<String, Object>> callback, final ScheduledExecutorService executorService) {
        super(apiKey, jobId, callback, executorService);

        this.idolOnDemandService = idolOnDemandService;
    }

    /**
     * Creates a new IdolOnDemandJobStatusRunnable using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param jobId The ID of the job
     * @param callback The callback that will be called with the result
     * @param executorService The executor service responsible for running the runnable
     */
    public IdolOnDemandJobStatusRunnable(final IdolOnDemandService idolOnDemandService, final JobId jobId, final IodJobCallback<Map<String, Object>> callback, final ScheduledExecutorService executorService) {
        super(jobId, callback, executorService);

        this.idolOnDemandService = idolOnDemandService;
    }

    @Override
    public JobStatus<Map<String, Object>> getJobStatus(final JobId jobId) throws IodErrorException {
        return idolOnDemandService.getJobStatus(jobId);
    }

    @Override
    public JobStatus<Map<String, Object>> getJobStatus(final String apiKey, final JobId jobId) throws IodErrorException {
        return idolOnDemandService.getJobStatus(apiKey, jobId);
    }
}
