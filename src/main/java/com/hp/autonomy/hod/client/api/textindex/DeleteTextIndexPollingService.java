/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex;


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
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Implementation of DeleteTextIndexService which polls for job completion
 * <p/>
 * The destroy method should be called when the service is no longer needed.
 */
@Slf4j
public class DeleteTextIndexPollingService extends AbstractPollingService implements DeleteTextIndexService {

    private final DeleteTextIndexBackend deleteTextIndexBackend;
    private final Requester<?, TokenType.Simple> requester;
    private final JobService<? extends JobStatus<DeleteTextIndexResponse>> jobService;

    /**
     * Creates a new DeleteTextIndexPollingService
     * @param hodServiceConfig The configuration to use
     */
    public DeleteTextIndexPollingService(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig) {
        super(hodServiceConfig.getAsyncTimeout());

        deleteTextIndexBackend = hodServiceConfig.getRestAdapter().create(DeleteTextIndexBackend.class);
        requester = hodServiceConfig.getRequester();
        jobService = new JobServiceImpl<>(hodServiceConfig, DeleteTextIndexBackend.DeleteTextIndexJobStatus.class);
    }

    /**
     * Creates a new DeleteTextIndexPollingService
     * @param hodServiceConfig The configuration to use
     * @param executorService The executor service to use while polling for status updates
     */
    public DeleteTextIndexPollingService(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig, final ScheduledExecutorService executorService) {
        super(executorService, hodServiceConfig.getAsyncTimeout());

        deleteTextIndexBackend = hodServiceConfig.getRestAdapter().create(DeleteTextIndexBackend.class);
        requester = hodServiceConfig.getRequester();
        jobService = new JobServiceImpl<>(hodServiceConfig, DeleteTextIndexBackend.DeleteTextIndexJobStatus.class);
    }

    @Override
    public void deleteTextIndex(
        final ResourceName index,
        final HodJobCallback<DeleteTextIndexResponse> callback
    ) throws HodErrorException {
        final DeleteTextIndexResponse response = requester.makeRequest(DeleteTextIndexResponse.class, getInitialBackendCaller(index));

        final JobId jobId = requester.makeRequest(JobId.class, getDeletingBackendCaller(index, response));

        getExecutorService().submit(new PollingJobStatusRunnable<>(getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    @Override
    public void deleteTextIndex(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final ResourceName index,
        final HodJobCallback<DeleteTextIndexResponse> callback
    ) throws HodErrorException {
        final DeleteTextIndexResponse response = requester.makeRequest(tokenProxy, DeleteTextIndexResponse.class, getInitialBackendCaller(index));

        final JobId jobId = requester.makeRequest(tokenProxy, JobId.class, getDeletingBackendCaller(index, response));

        getExecutorService().submit(new PollingJobStatusRunnable<>(tokenProxy, getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getInitialBackendCaller(final ResourceName index) {
        return authenticationToken -> deleteTextIndexBackend.initialDeleteTextIndex(authenticationToken, index);
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getDeletingBackendCaller(final ResourceName index, final DeleteTextIndexResponse response) {
        return authenticationToken -> deleteTextIndexBackend.deleteTextIndex(authenticationToken, index, response.getConfirm());
    }


}
