/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.document;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
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
import retrofit.client.Response;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Implementation of DeleteFromTextIndexService which polls for job completion.
 * <p/>
 * The destroy method should be called when the service is no longer needed.
 */
public class DeleteFromTextIndexPollingService extends AbstractPollingService implements DeleteFromTextIndexService {

    private final DeleteFromTextIndexBackend deleteFromTextIndexBackend;
    private final Requester<?, TokenType.Simple> requester;
    private final JobService<? extends JobStatus<DeleteFromTextIndexResponse>> jobService;

    /**
     * Creates a new DeleteFromTextIndexPollingService with a default ScheduledExecutorService
     * @param hodServiceConfig The configuration to use
     */
    public DeleteFromTextIndexPollingService(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig) {
        super(hodServiceConfig.getAsyncTimeout());

        deleteFromTextIndexBackend = hodServiceConfig.getRestAdapter().create(DeleteFromTextIndexBackend.class);
        jobService = new JobServiceImpl<>(hodServiceConfig, DeleteFromTextIndexBackend.DeleteFromTextIndexJobStatus.class);
        requester = hodServiceConfig.getRequester();
    }

    /**
     * Creates a new DeleteFromTextIndexPollingService
     * @param hodServiceConfig The configuration to use
     * @param executorService The executor service to use while polling for status updates
     */
    public DeleteFromTextIndexPollingService(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig, final ScheduledExecutorService executorService) {
        super(executorService, hodServiceConfig.getAsyncTimeout());

        deleteFromTextIndexBackend = hodServiceConfig.getRestAdapter().create(DeleteFromTextIndexBackend.class);
        jobService = new JobServiceImpl<>(hodServiceConfig, DeleteFromTextIndexBackend.DeleteFromTextIndexJobStatus.class);
        requester = hodServiceConfig.getRequester();
    }

    @Override
    public void deleteReferencesFromTextIndex(
        final ResourceIdentifier index,
        final List<String> references,
        final HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(JobId.class, getDeleteReferencesBackendCaller(index, references));

        getExecutorService().submit(new PollingJobStatusRunnable<>(getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    @Override
    public void deleteReferencesFromTextIndex(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final ResourceIdentifier index,
        final List<String> references,
        final HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(tokenProxy, JobId.class, getDeleteReferencesBackendCaller(index, references));

        getExecutorService().submit(new PollingJobStatusRunnable<>(tokenProxy, getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    @Override
    public void deleteAllDocumentsFromTextIndex(
        final ResourceIdentifier index,
        final HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(JobId.class, getDeleteAllBackendCaller(index));

        getExecutorService().submit(new PollingJobStatusRunnable<>(getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    @Override
    public void deleteAllDocumentsFromTextIndex(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final ResourceIdentifier index,
        final HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(JobId.class, getDeleteAllBackendCaller(index));

        getExecutorService().submit(new PollingJobStatusRunnable<>(tokenProxy, getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getDeleteReferencesBackendCaller(final ResourceIdentifier index, final List<String> references) {
        return authenticationToken -> deleteFromTextIndexBackend.deleteReferencesFromTextIndex(authenticationToken, index, references);
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getDeleteAllBackendCaller(final ResourceIdentifier index) {
        return authenticationToken -> deleteFromTextIndexBackend.deleteAllDocumentsFromTextIndex(authenticationToken, index);
    }

}
