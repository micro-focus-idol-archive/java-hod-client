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
import retrofit.mime.TypedOutput;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Service for managing jobs ids from the AddToTextIndex API.
 *
 * The destroy method should be called when the service is no longer needed.
 */
@Slf4j
public class AddToTextIndexJobService extends AbstractJobService {

    private final AddToTextIndexService addToTextIndexService;

    /**
     * Creates a new AddToTextIndexJobService
     * @param addToTextIndexService The underlying service which will communicate with IDOL OnDemand
     */
    public AddToTextIndexJobService(final AddToTextIndexService addToTextIndexService) {
        super();

        this.addToTextIndexService = addToTextIndexService;
    }

    /**
     * Creates a new AddToTextIndexJobService
     * @param addToTextIndexService The underlying service which will communicate with IDOL OnDemand
     * @param executorService The executor service to use while polling for status updates
     */
    public AddToTextIndexJobService(final AddToTextIndexService addToTextIndexService, final ScheduledExecutorService executorService) {
        super(executorService);

        this.addToTextIndexService = addToTextIndexService;
    }

    /**
     * Index JSON documents into IDOL OnDemand
     * @param apiKey The API key to use to authenticate the request
     * @param documents A collection of objects to convert to JSON
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws IodErrorException If an error occurs indexing the documents
     */
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

    /**
     * Index a public accessible url into IDOL OnDemand
     * @param apiKey The API key to use to authenticate the request
     * @param url A publicly accessible url containing the document content
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws IodErrorException If an error occurs indexing the documents
     */
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

    /**
     * Index an object store object into IDOL OnDemand
     * @param apiKey The API key to use to authenticate the request
     * @param reference An object store reference pointing at a file to be used for document content
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws IodErrorException If an error occurs indexing the documents
     */
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

    /**
     * Index a file into IDOL OnDemand
     * @param apiKey The API key to use to authenticate the request
     * @param file A file containing the content of the document
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws IodErrorException If an error occurs indexing the documents
     */
    public void addFileToTextIndex(
            final String apiKey,
            final TypedOutput file,
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
        public JobStatus<AddToTextIndexResponse> getJobStatus(final JobId jobId) throws IodErrorException {
            return addToTextIndexService.getJobStatus(jobId);
        }

        @Override
        public JobStatus<AddToTextIndexResponse> getJobStatus(final String apiKey, final JobId jobId) throws IodErrorException {
            return addToTextIndexService.getJobStatus(apiKey, jobId);
        }

    }
}
