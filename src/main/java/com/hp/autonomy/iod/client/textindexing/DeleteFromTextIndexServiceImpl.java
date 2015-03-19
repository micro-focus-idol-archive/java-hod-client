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

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public class DeleteFromTextIndexServiceImpl extends AbstractJobService {

    private final DeleteFromTextIndexService deleteFromTextIndexService;

    public DeleteFromTextIndexServiceImpl(final DeleteFromTextIndexService deleteFromTextIndexService) {
        super();

        this.deleteFromTextIndexService = deleteFromTextIndexService;
    }

    public DeleteFromTextIndexServiceImpl(final DeleteFromTextIndexService deleteFromTextIndexService, final ScheduledExecutorService executorService) {
        super(executorService);

        this.deleteFromTextIndexService = deleteFromTextIndexService;
    }

    public void deleteReferencesFromTextIndex(
            final String apiKey,
            final String index,
            final List<String> references,
            final IodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws IodErrorException {
        final JobId jobId = deleteFromTextIndexService.deleteReferencesFromTextIndex(apiKey, index, references);

        getExecutorService().submit(new DeleteFromTextIndexPollingStatusRunnable(apiKey, jobId, callback));
    }

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
