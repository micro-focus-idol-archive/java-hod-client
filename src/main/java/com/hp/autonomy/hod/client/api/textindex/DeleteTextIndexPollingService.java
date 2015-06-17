/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex;


import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.AbstractPollingService;
import com.hp.autonomy.hod.client.job.HodJobCallback;
import com.hp.autonomy.hod.client.job.JobId;
import com.hp.autonomy.hod.client.job.JobStatus;
import com.hp.autonomy.hod.client.job.PollingJobStatusRunnable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Service for managing jobs ids from the DeleteTextIndex API.
 * <p/>
 * The destroy method should be called when the service is no longer needed.
 */
@Slf4j
public class DeleteTextIndexPollingService extends AbstractPollingService {

    private final DeleteTextIndexService deleteTextIndexService;

    /**
     * Creates a new DeleteTextIndexPollingService
     * @param deleteTextIndexService The underlying service which will communicate with HP Haven OnDemand
     */
    public DeleteTextIndexPollingService(final DeleteTextIndexService deleteTextIndexService) {
        super();

        this.deleteTextIndexService = deleteTextIndexService;
    }

    /**
     * Creates a new DeleteTextIndexPollingService
     * @param deleteTextIndexService The underlying service which will communicate with HP Haven OnDemand
     * @param executorService The executor service to use while polling for status updates
     */
    public DeleteTextIndexPollingService(final DeleteTextIndexService deleteTextIndexService, final ScheduledExecutorService executorService) {
        super(executorService);

        this.deleteTextIndexService = deleteTextIndexService;
    }

    /**
     * Deletes the given text index using the given API key. This API handles the confirm token returned by HP Haven OnDemand
     * automatically.
     * @param token The token to use to authenticate the request
     * @param index The name of the index
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs
     */
    public void deleteTextIndex(
            final AuthenticationToken token,
            final String index,
            final HodJobCallback<DeleteTextIndexResponse> callback
    ) throws HodErrorException {
        final DeleteTextIndexResponse response = deleteTextIndexService.initialDeleteTextIndex(token, index);

        final JobId jobId = deleteTextIndexService.deleteTextIndex(token, index, response.getConfirm());

        getExecutorService().submit(new DeleteTextIndexPollingStatusRunnable(token, jobId, callback));
    }

    /**
     * Deletes the given text index using an API key provided by a {@link retrofit.RequestInterceptor}. This API handles
     * the confirm token returned by HP Haven OnDemand automatically.
     * @param index The name of the index
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs
     */
    public void deleteTextIndex(
            final String index,
            final HodJobCallback<DeleteTextIndexResponse> callback
    ) throws HodErrorException {
        final DeleteTextIndexResponse response = deleteTextIndexService.initialDeleteTextIndex(index);

        final JobId jobId = deleteTextIndexService.deleteTextIndex(index, response.getConfirm());

        getExecutorService().submit(new DeleteTextIndexPollingStatusRunnable(jobId, callback));
    }


    private class DeleteTextIndexPollingStatusRunnable extends PollingJobStatusRunnable<DeleteTextIndexResponse> {

        private DeleteTextIndexPollingStatusRunnable(final AuthenticationToken token, final JobId jobId, final HodJobCallback<DeleteTextIndexResponse> callback) {
            super(token, jobId, callback, getExecutorService());
        }

        private DeleteTextIndexPollingStatusRunnable(final JobId jobId, final HodJobCallback<DeleteTextIndexResponse> callback) {
            super(jobId, callback, getExecutorService());
        }

        @Override
        public JobStatus<DeleteTextIndexResponse> getJobStatus(final JobId jobId) throws HodErrorException {
            return deleteTextIndexService.getJobStatus(jobId);
        }

        @Override
        public JobStatus<DeleteTextIndexResponse> getJobStatus(final AuthenticationToken token, final JobId jobId) throws HodErrorException {
            return deleteTextIndexService.getJobStatus(token, jobId);
        }

    }

}
