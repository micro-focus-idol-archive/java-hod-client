/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.document;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.AbstractJobService;
import com.hp.autonomy.hod.client.job.HodJobCallback;
import com.hp.autonomy.hod.client.job.JobId;
import com.hp.autonomy.hod.client.job.JobStatus;
import com.hp.autonomy.hod.client.job.PollingJobStatusRunnable;
import lombok.extern.slf4j.Slf4j;
import retrofit.mime.TypedOutput;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Service for managing jobs ids from the AddToTextIndex API.
 * <p/>
 * The destroy method should be called when the service is no longer needed.
 */
@Slf4j
public class AddToTextIndexJobService extends AbstractJobService {

    private final AddToTextIndexService addToTextIndexService;

    /**
     * Creates a new AddToTextIndexJobService
     * @param addToTextIndexService The underlying service which will communicate with HP Haven OnDemand
     */
    public AddToTextIndexJobService(final AddToTextIndexService addToTextIndexService) {
        super();

        this.addToTextIndexService = addToTextIndexService;
    }

    /**
     * Creates a new AddToTextIndexJobService
     * @param addToTextIndexService The underlying service which will communicate with HP Haven OnDemand
     * @param executorService The executor service to use while polling for status updates
     */
    public AddToTextIndexJobService(final AddToTextIndexService addToTextIndexService, final ScheduledExecutorService executorService) {
        super(executorService);

        this.addToTextIndexService = addToTextIndexService;
    }

    /**
     * Index JSON documents into HP Haven OnDemand using a token
     * provided by a {@link retrofit.RequestInterceptor}
     * @param documents A collection of objects to convert to JSON
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     */
    public void addJsonToTextIndex(
            final Documents<?> documents,
            final String index,
            final Map<String, Object> params,
            final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = addToTextIndexService.addJsonToTextIndex(documents, index, params);

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(jobId, callback));
    }

    /**
     * Index JSON documents into HP Haven OnDemand using the given token
     * @param token The token to use to authenticate the request
     * @param documents A collection of objects to convert to JSON
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     */
    public void addJsonToTextIndex(
            final AuthenticationToken token,
            final Documents<?> documents,
            final String index,
            final Map<String, Object> params,
            final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = addToTextIndexService.addJsonToTextIndex(token, documents, index, params);

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(token, jobId, callback));
    }

    /**
     * Index a public accessible url into HP Haven OnDemand using a token
     * provided by a {@link retrofit.RequestInterceptor}
     * @param url A publicly accessible url containing the document content
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     */
    public void addUrlToTextIndex(
            final String url,
            final String index,
            final Map<String, Object> params,
            final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = addToTextIndexService.addUrlToTextIndex(url, index, params);

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(jobId, callback));
    }

    /**
     * Index a public accessible url into HP Haven OnDemand using the given token
     * @param token The token to use to authenticate the request
     * @param url A publicly accessible url containing the document content
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     */
    public void addUrlToTextIndex(
            final AuthenticationToken token,
            final String url,
            final String index,
            final Map<String, Object> params,
            final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = addToTextIndexService.addUrlToTextIndex(token, url, index, params);

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(token, jobId, callback));
    }

    /**
     * Index an object store object into HP Haven OnDemand using a token
     * provided by a {@link retrofit.RequestInterceptor}
     * @param reference An object store reference pointing at a file to be used for document content
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     */
    public void addReferenceToTextIndex(
            final String reference,
            final String index,
            final Map<String, Object> params,
            final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = addToTextIndexService.addReferenceToTextIndex(reference, index, params);

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(jobId, callback));
    }

    /**
     * Index an object store object into HP Haven OnDemand using the given token
     * @param token The token to use to authenticate the request
     * @param reference An object store reference pointing at a file to be used for document content
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     */
    public void addReferenceToTextIndex(
            final AuthenticationToken token,
            final String reference,
            final String index,
            final Map<String, Object> params,
            final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = addToTextIndexService.addReferenceToTextIndex(token, reference, index, params);

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(token, jobId, callback));
    }

    /**
     * Index a file into HP Haven OnDemand using a token
     * provided by a {@link retrofit.RequestInterceptor}
     * @param file A file containing the content of the document
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     */
    public void addFileToTextIndex(
            final TypedOutput file,
            final String index,
            final Map<String, Object> params,
            final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = addToTextIndexService.addFileToTextIndex(file, index, params);

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(jobId, callback));
    }

    /**
     * Index a file into HP Haven OnDemand using the given token
     * @param token The token to use to authenticate the request
     * @param file A file containing the content of the document
     * @param index The index to add to
     * @param params Additional parameters to be sent as part of the request
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs indexing the documents
     */
    public void addFileToTextIndex(
            final AuthenticationToken token,
            final TypedOutput file,
            final String index,
            final Map<String, Object> params,
            final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = addToTextIndexService.addFileToTextIndex(token, file, index, params);

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(token, jobId, callback));
    }

    private class AddToTextIndexPollingStatusRunnable extends PollingJobStatusRunnable<AddToTextIndexResponse> {

        private AddToTextIndexPollingStatusRunnable(final JobId jobId, final HodJobCallback<AddToTextIndexResponse> callback) {
            super(jobId, callback, getExecutorService());
        }

        private AddToTextIndexPollingStatusRunnable(final AuthenticationToken token, final JobId jobId, final HodJobCallback<AddToTextIndexResponse> callback) {
            super(token, jobId, callback, getExecutorService());
        }

        @Override
        public JobStatus<AddToTextIndexResponse> getJobStatus(final JobId jobId) throws HodErrorException {
            return addToTextIndexService.getJobStatus(jobId);
        }

        @Override
        public JobStatus<AddToTextIndexResponse> getJobStatus(final AuthenticationToken token, final JobId jobId) throws HodErrorException {
            return addToTextIndexService.getJobStatus(token, jobId);
        }

    }
}
