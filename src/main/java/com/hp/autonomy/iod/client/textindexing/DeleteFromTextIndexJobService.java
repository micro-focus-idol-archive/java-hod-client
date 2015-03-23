/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.textindexing;

import com.hp.autonomy.iod.client.error.IodErrorException;
import com.hp.autonomy.iod.client.job.AbstractJobService;
import com.hp.autonomy.iod.client.job.IodJobCallback;
import com.hp.autonomy.iod.client.job.JobId;
import com.hp.autonomy.iod.client.job.JobStatus;
import com.hp.autonomy.iod.client.job.PollingJobStatusRunnable;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Service for managing jobs ids from the DeleteFromTextIndex API
 *
 * The destroy method should be called when the service is no longer needed.
 */
public class DeleteFromTextIndexJobService extends AbstractJobService {

    private final DeleteFromTextIndexService deleteFromTextIndexService;

    /**
     * Creates a new DeleteFromTextIndexJobService
     * @param deleteFromTextIndexService The underlying service which will communicate with IDOL OnDemand
     */
    public DeleteFromTextIndexJobService(final DeleteFromTextIndexService deleteFromTextIndexService) {
        super();

        this.deleteFromTextIndexService = deleteFromTextIndexService;
    }

    /**
     * Creates a new DeleteFromTextIndexJobService
     * @param deleteFromTextIndexService The underlying service which will communicate with IDOL OnDemand
     * @param executorService The executor service to use while polling for status updates
     */
    public DeleteFromTextIndexJobService(final DeleteFromTextIndexService deleteFromTextIndexService, final ScheduledExecutorService executorService) {
        super(executorService);

        this.deleteFromTextIndexService = deleteFromTextIndexService;
    }

    /**
     * Deletes the documents with the given references
     * @param apiKey The API key to use to authenticate the request
     * @param index The index to delete from
     * @param references The references of the documents to delete
     * @param callback Callback that will be called with the response
     * @throws IodErrorException If an error occurs with the request
     */
    public void deleteReferencesFromTextIndex(
            final String apiKey,
            final String index,
            final List<String> references,
            final IodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws IodErrorException {
        final JobId jobId = deleteFromTextIndexService.deleteReferencesFromTextIndex(apiKey, index, references);

        getExecutorService().submit(new DeleteFromTextIndexPollingStatusRunnable(apiKey, jobId, callback));
    }

    /**
     * Deletes all the documents from the given text index
     * @param apiKey The API key to use to authenticate the request
     * @param index The index to delete from
     * @param callback Callback that will be called with the response
     * @throws IodErrorException If an error occurs with the request
     */
    public void deleteAllDocumentsFromTextIndex(
            final String apiKey,
            final String index,
            final IodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws IodErrorException {
        final JobId jobId = deleteFromTextIndexService.deleteAllDocumentsFromTextIndex(apiKey, index);

        getExecutorService().submit(new DeleteFromTextIndexPollingStatusRunnable(apiKey, jobId, callback));
    }

    private class DeleteFromTextIndexPollingStatusRunnable extends PollingJobStatusRunnable<DeleteFromTextIndexResponse> {

        public DeleteFromTextIndexPollingStatusRunnable(final String apiKey, final JobId jobId, final IodJobCallback<DeleteFromTextIndexResponse> callback) {
            super(apiKey, jobId, callback, getExecutorService());
        }

        @Override
        public JobStatus<DeleteFromTextIndexResponse> getJobStatus(final String apiKey, final JobId jobId) throws IodErrorException {
            return deleteFromTextIndexService.getJobStatus(apiKey, jobId);
        }
    }

}
