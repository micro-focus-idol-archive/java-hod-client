/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.document;

import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.AbstractPollingService;
import com.hp.autonomy.hod.client.job.HodJobCallback;
import com.hp.autonomy.hod.client.job.JobId;
import com.hp.autonomy.hod.client.job.JobService;
import com.hp.autonomy.hod.client.job.JobServiceImpl;
import com.hp.autonomy.hod.client.job.JobStatus;
import com.hp.autonomy.hod.client.job.PollingJobStatusRunnable;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.util.TypedByteArrayWithFilename;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import retrofit.mime.TypedFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Implementation of AddToTextIndexService which polls for job completion.
 * <p/>
 * The destroy method should be called when the service is no longer needed.
 */
@Slf4j
public class AddToTextIndexPollingService extends AbstractPollingService implements AddToTextIndexService {

    private final AddToTextIndexBackend addToTextIndexBackend;
    private final JobService<? extends JobStatus<AddToTextIndexResponse>> jobService;
    private final Requester<?, TokenType.Simple> requester;

    /**
     * Creates a new AddToTextIndexPollingService with a default ScheduledExecutorService
     * @param hodServiceConfig The configuration for the service
     */
    public AddToTextIndexPollingService(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig) {
        super(hodServiceConfig.getAsyncTimeout());

        addToTextIndexBackend = hodServiceConfig.getRestAdapter().create(AddToTextIndexBackend.class);
        jobService = new JobServiceImpl<>(hodServiceConfig, AddToTextIndexBackend.AddToTextIndexJobStatus.class);
        requester = hodServiceConfig.getRequester();
    }

    /**
     * Creates a new AddToTextIndexPollingService
     * @param hodServiceConfig The configuration for the service
     * @param executorService The executor service to use while polling for status updates
     */
    public AddToTextIndexPollingService(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig, final ScheduledExecutorService executorService) {
        super(executorService, hodServiceConfig.getAsyncTimeout());

        addToTextIndexBackend = hodServiceConfig.getRestAdapter().create(AddToTextIndexBackend.class);
        jobService = new JobServiceImpl<>(hodServiceConfig, AddToTextIndexBackend.AddToTextIndexJobStatus.class);
        requester = hodServiceConfig.getRequester();
    }

    @Override
    public void addJsonToTextIndex(
        final Documents<?> documents,
        final ResourceName index,
        final AddToTextIndexRequestBuilder params,
        final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(JobId.class, getTextBackendCaller(documents, index, params));

        getExecutorService().submit(new PollingJobStatusRunnable<>(getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    @Override
    public void addJsonToTextIndex(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final Documents<?> documents,
        final ResourceName index,
        final AddToTextIndexRequestBuilder params,
        final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(tokenProxy, JobId.class, getTextBackendCaller(documents, index, params));

        getExecutorService().submit(new PollingJobStatusRunnable<>(tokenProxy, getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    @Override
    public void addUrlToTextIndex(
        final String url,
        final ResourceName index,
        final AddToTextIndexRequestBuilder params,
        final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(JobId.class, getUrlBackendCaller(url, index, params));

        getExecutorService().submit(new PollingJobStatusRunnable<>(getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    @Override
    public void addUrlToTextIndex(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final String url,
        final ResourceName index,
        final AddToTextIndexRequestBuilder params,
        final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(tokenProxy, JobId.class, getUrlBackendCaller(url, index, params));

        getExecutorService().submit(new PollingJobStatusRunnable<>(tokenProxy, getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    @Override
    public void addReferenceToTextIndex(
        final String reference,
        final ResourceName index,
        final AddToTextIndexRequestBuilder params,
        final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(JobId.class, getReferenceBackendCaller(reference, index, params));

        getExecutorService().submit(new PollingJobStatusRunnable<>(getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    @Override
    public void addReferenceToTextIndex(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final String reference,
        final ResourceName index,
        final AddToTextIndexRequestBuilder params,
        final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(tokenProxy, JobId.class, getReferenceBackendCaller(reference, index, params));

        getExecutorService().submit(new PollingJobStatusRunnable<>(tokenProxy, getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    @Override
    public void addFileToTextIndex(final File file, final ResourceName index, final AddToTextIndexRequestBuilder params, final HodJobCallback<AddToTextIndexResponse> callback) throws HodErrorException {
        final JobId jobId = requester.makeRequest(JobId.class, getFileBackendCaller(file, index, params));

        getExecutorService().submit(new PollingJobStatusRunnable<>(getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    @Override
    public void addFileToTextIndex(final TokenProxy<?, TokenType.Simple> tokenProxy, final File file, final ResourceName index, final AddToTextIndexRequestBuilder params, final HodJobCallback<AddToTextIndexResponse> callback) throws HodErrorException {
        final JobId jobId = requester.makeRequest(tokenProxy, JobId.class, getFileBackendCaller(file, index, params));

        getExecutorService().submit(new PollingJobStatusRunnable<>(tokenProxy, getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    @Override
    public void addFileToTextIndex(final byte[] bytes, final ResourceName index, final AddToTextIndexRequestBuilder params, final HodJobCallback<AddToTextIndexResponse> callback) throws HodErrorException {
        final JobId jobId = requester.makeRequest(JobId.class, getByteArrayBackendCaller(bytes, index, params));

        getExecutorService().submit(new PollingJobStatusRunnable<>(getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    @Override
    public void addFileToTextIndex(final TokenProxy<?, TokenType.Simple> tokenProxy, final byte[] bytes, final ResourceName index, final AddToTextIndexRequestBuilder params, final HodJobCallback<AddToTextIndexResponse> callback) throws HodErrorException {
        final JobId jobId = requester.makeRequest(tokenProxy, JobId.class, getByteArrayBackendCaller(bytes, index, params));

        getExecutorService().submit(new PollingJobStatusRunnable<>(tokenProxy, getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    @Override
    public void addFileToTextIndex(final InputStream inputStream, final ResourceName index, final AddToTextIndexRequestBuilder params, final HodJobCallback<AddToTextIndexResponse> callback) throws HodErrorException {
        final JobId jobId = requester.makeRequest(JobId.class, getInputStreamBackendCaller(inputStream, index, params));

        getExecutorService().submit(new PollingJobStatusRunnable<>(getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    @Override
    public void addFileToTextIndex(final TokenProxy<?, TokenType.Simple> tokenProxy, final InputStream inputStream, final ResourceName index, final AddToTextIndexRequestBuilder params, final HodJobCallback<AddToTextIndexResponse> callback) throws HodErrorException {
        final JobId jobId = requester.makeRequest(tokenProxy, JobId.class, getInputStreamBackendCaller(inputStream, index, params));

        getExecutorService().submit(new PollingJobStatusRunnable<>(tokenProxy, getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getTextBackendCaller(final Documents<?> documents, final ResourceName index, final AddToTextIndexRequestBuilder params) {
        return authenticationToken -> addToTextIndexBackend.addJsonToTextIndex(authenticationToken, documents, index, params.build());
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getUrlBackendCaller(final String url, final ResourceName index, final AddToTextIndexRequestBuilder params) {
        return authenticationToken -> addToTextIndexBackend.addUrlToTextIndex(authenticationToken, url, index, params.build());
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getReferenceBackendCaller(final String reference, final ResourceName index, final AddToTextIndexRequestBuilder params) {
        return authenticationToken -> addToTextIndexBackend.addReferenceToTextIndex(authenticationToken, reference, index, params.build());
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getFileBackendCaller(final File file, final ResourceName index, final AddToTextIndexRequestBuilder params) {
        return authenticationToken -> addToTextIndexBackend.addFileToTextIndex(authenticationToken, new TypedFile("application/octet-stream", file), index, params.build());
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getByteArrayBackendCaller(final byte[] bytes, final ResourceName index, final AddToTextIndexRequestBuilder params) {
        return authenticationToken -> addToTextIndexBackend.addFileToTextIndex(authenticationToken, new TypedByteArrayWithFilename("application/octet-stream", bytes), index, params.build());
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getInputStreamBackendCaller(final InputStream inputStream, final ResourceName index, final AddToTextIndexRequestBuilder params) {
        return authenticationToken -> {
            try {
                return addToTextIndexBackend.addFileToTextIndex(authenticationToken, new TypedByteArrayWithFilename("application/octet-stream", IOUtils.toByteArray(inputStream)), index, params.build());
            } catch (final IOException e) {
                throw new RuntimeException("Error reading bytes from stream", e);
            }
        };
    }

}
