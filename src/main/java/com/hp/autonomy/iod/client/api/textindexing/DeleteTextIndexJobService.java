/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.textindexing;


import com.hp.autonomy.iod.client.error.IodErrorException;
import com.hp.autonomy.iod.client.job.AbstractJobService;
import com.hp.autonomy.iod.client.job.IodJobCallback;
import com.hp.autonomy.iod.client.job.JobId;
import com.hp.autonomy.iod.client.job.JobStatus;
import com.hp.autonomy.iod.client.job.PollingJobStatusRunnable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Service for managing jobs ids from the DeleteTextIndex API.
 * <p/>
 * The destroy method should be called when the service is no longer needed.
 */
@Slf4j
public class DeleteTextIndexJobService extends AbstractJobService {

    private final DeleteTextIndexService deleteTextIndexService;

    /**
     * Creates a new DeleteTextIndexJobService
     * @param deleteTextIndexService The underlying service which will communicate with IDOL OnDemand
     */
    public DeleteTextIndexJobService(final DeleteTextIndexService deleteTextIndexService) {
        super();

        this.deleteTextIndexService = deleteTextIndexService;
    }

    /**
     * Creates a new DeleteTextIndexJobService
     * @param deleteTextIndexService The underlying service which will communicate with IDOL OnDemand
     * @param executorService The executor service to use while polling for status updates
     */
    public DeleteTextIndexJobService(final DeleteTextIndexService deleteTextIndexService, final ScheduledExecutorService executorService) {
        super(executorService);

        this.deleteTextIndexService = deleteTextIndexService;
    }

    /**
     * Deletes the given text index using the given API key. This API handles the confirm token returned by IDOL
     * OnDemand automatically.
     * @param apiKey The API key to use to authenticate the request
     * @param index The name of the index
     * @param callback Callback that will be called with the response
     * @throws IodErrorException If an error occurs
     */
    public void deleteTextIndex(
            final String apiKey,
            final String index,
            final IodJobCallback<DeleteTextIndexResponse> callback
    ) throws IodErrorException {
        final DeleteTextIndexResponse response = deleteTextIndexService.initialDeleteTextIndex(apiKey, index);

        final JobId jobId = deleteTextIndexService.deleteTextIndex(apiKey, index, response.getConfirm());

        getExecutorService().submit(new DeleteTextIndexPollingStatusRunnable(apiKey, jobId, callback));
    }

    /**
     * Deletes the given text index using an API key provided by a {@link retrofit.RequestInterceptor}. This API handles
     * the confirm token returned by IDOL OnDemand automatically.
     * @param index The name of the index
     * @param callback Callback that will be called with the response
     * @throws IodErrorException If an error occurs
     */
    public void deleteTextIndex(
            final String index,
            final IodJobCallback<DeleteTextIndexResponse> callback
    ) throws IodErrorException {
        final DeleteTextIndexResponse response = deleteTextIndexService.initialDeleteTextIndex(index);

        final JobId jobId = deleteTextIndexService.deleteTextIndex(index, response.getConfirm());

        getExecutorService().submit(new DeleteTextIndexPollingStatusRunnable(jobId, callback));
    }


    private class DeleteTextIndexPollingStatusRunnable extends PollingJobStatusRunnable<DeleteTextIndexResponse> {

        private DeleteTextIndexPollingStatusRunnable(final String apiKey, final JobId jobId, final IodJobCallback<DeleteTextIndexResponse> callback) {
            super(apiKey, jobId, callback, getExecutorService());
        }

        private DeleteTextIndexPollingStatusRunnable(final JobId jobId, final IodJobCallback<DeleteTextIndexResponse> callback) {
            super(jobId, callback, getExecutorService());
        }

        @Override
        public JobStatus<DeleteTextIndexResponse> getJobStatus(final JobId jobId) throws IodErrorException {
            return deleteTextIndexService.getJobStatus(jobId);
        }

        @Override
        public JobStatus<DeleteTextIndexResponse> getJobStatus(final String apiKey, final JobId jobId) throws IodErrorException {
            return deleteTextIndexService.getJobStatus(apiKey, jobId);
        }

    }

}
