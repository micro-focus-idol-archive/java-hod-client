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
import lombok.extern.slf4j.Slf4j;
import retrofit.mime.TypedInput;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
public class AddToTextIndexServiceImpl extends AbstractJobService {

    private final AddToTextIndexService addToTextIndexService;

    public AddToTextIndexServiceImpl(final AddToTextIndexService addToTextIndexService) {
        super();

        this.addToTextIndexService = addToTextIndexService;
    }

    public AddToTextIndexServiceImpl(final AddToTextIndexService addToTextIndexService, final ScheduledExecutorService executorService) {
        super(executorService);

        this.addToTextIndexService = addToTextIndexService;
    }

    public void addJsonToTextIndex(
            final String apiKey,
            final Documents<?> documents,
            final String index,
            final Map<String, Object> params,
            final IodJobCallback<AddToTextIndexResponse> callback
    ) throws IodErrorException {
        final JobId jobId = addToTextIndexService.addJsonToTextIndex(apiKey, documents, index, params);

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(apiKey, jobId, callback));
    }

    public void addUrlToTextIndex(
            final String apiKey,
            final String url,
            final String index,
            final Map<String, Object> params,
            final IodJobCallback<AddToTextIndexResponse> callback
    ) throws IodErrorException {
        final JobId jobId = addToTextIndexService.addUrlToTextIndex(apiKey, url, index, params);

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(apiKey, jobId, callback));
    }

    public void addReferenceToTextIndex(
            final String apiKey,
            final String reference,
            final String index,
            final Map<String, Object> params,
            final IodJobCallback<AddToTextIndexResponse> callback
    ) throws IodErrorException {
        final JobId jobId = addToTextIndexService.addReferenceToTextIndex(apiKey, reference, index, params);

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(apiKey, jobId, callback));
    }

    public void addFileToTextIndex(
            final String apiKey,
            final TypedInput file,
            final String index,
            final Map<String, Object> params,
            final IodJobCallback<AddToTextIndexResponse> callback
    ) throws IodErrorException {
        final JobId jobId = addToTextIndexService.addFileToTextIndex(apiKey, file, index, params);

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(apiKey, jobId, callback));
    }

    private class AddToTextIndexPollingStatusRunnable extends PollingJobStatusRunnable<AddToTextIndexResponse> {

        private AddToTextIndexPollingStatusRunnable(final String apiKey, final JobId jobId, final IodJobCallback<AddToTextIndexResponse> callback) {
            super(apiKey, jobId, callback, getExecutorService());
        }

        @Override
        public JobStatus<AddToTextIndexResponse> getJobStatus(final String apiKey, final JobId jobId) throws IodErrorException {
            return addToTextIndexService.getJobStatus(apiKey, jobId);
        }

    }
}
