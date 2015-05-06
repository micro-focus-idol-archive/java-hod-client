/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindexing;


import com.hp.autonomy.hod.client.job.AbstractJobService;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.HodJobCallback;
import com.hp.autonomy.hod.client.job.JobId;
import com.hp.autonomy.hod.client.job.JobStatus;
import com.hp.autonomy.hod.client.job.PollingJobStatusRunnable;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Service for managing jobs ids from the CreateTextIndex API.
 * <p/>
 * The destroy method should be called when the service is no longer needed.
 */
@Slf4j
public class CreateTextIndexJobService extends AbstractJobService {

    private final CreateTextIndexService createTextIndexService;

    /**
     * Creates a new CreateTextIndexJobService
     * @param createTextIndexService The underlying service which will communicate with HP Haven OnDemand
     */
    public CreateTextIndexJobService(final CreateTextIndexService createTextIndexService) {
        super();

        this.createTextIndexService = createTextIndexService;
    }

    /**
     * Creates a new CreateTextIndexJobService
     * @param createTextIndexService The underlying service which will communicate with HP Haven OnDemand
     * @param executorService The executor service to use while polling for status updates
     */
    public CreateTextIndexJobService(final CreateTextIndexService createTextIndexService, final ScheduledExecutorService executorService) {
        super(executorService);

        this.createTextIndexService = createTextIndexService;
    }

    /**
     * Index a file into HP Haven OnDemand using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param index The name of the index
     * @param flavor The flavor of the index
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs
     */
    public void createTextIndex(
            final String apiKey,
            final String index,
            final IndexFlavor flavor,
            final Map<String, Object> params,
            final HodJobCallback<CreateTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = createTextIndexService.createTextIndex(apiKey, index, flavor, params);

        getExecutorService().submit(new CreateTextIndexPollingStatusRunnable(apiKey, jobId, callback));
    }

    /**
     * Index a file into HP Haven OnDemand using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param index The name of the index
     * @param flavor The flavor of the index
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs
     */
    public void createTextIndex(
            final String index,
            final IndexFlavor flavor,
            final Map<String, Object> params,
            final HodJobCallback<CreateTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = createTextIndexService.createTextIndex(index, flavor, params);

        getExecutorService().submit(new CreateTextIndexPollingStatusRunnable(jobId, callback));
    }

    private class CreateTextIndexPollingStatusRunnable extends PollingJobStatusRunnable<CreateTextIndexResponse> {

        private CreateTextIndexPollingStatusRunnable(final String apiKey, final JobId jobId, final HodJobCallback<CreateTextIndexResponse> callback) {
            super(apiKey, jobId, callback, getExecutorService());
        }

        private CreateTextIndexPollingStatusRunnable(final JobId jobId, final HodJobCallback<CreateTextIndexResponse> callback) {
            super(jobId, callback, getExecutorService());
        }

        @Override
        public JobStatus<CreateTextIndexResponse> getJobStatus(final JobId jobId) throws HodErrorException {
            return createTextIndexService.getJobStatus(jobId);
        }

        @Override
        public JobStatus<CreateTextIndexResponse> getJobStatus(final String apiKey, final JobId jobId) throws HodErrorException {
            return createTextIndexService.getJobStatus(apiKey, jobId);
        }

    }
}
