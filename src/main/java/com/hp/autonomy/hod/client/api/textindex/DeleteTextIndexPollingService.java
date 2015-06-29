/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex;


import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
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
import lombok.extern.slf4j.Slf4j;
import retrofit.client.Response;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Implementation of DeleteTextIndexService which polls for job completion
 * <p/>
 * The destroy method should be called when the service is no longer needed.
 */
@Slf4j
public class DeleteTextIndexPollingService extends AbstractPollingService implements DeleteTextIndexService {

    private final DeleteTextIndexBackend deleteTextIndexBackend;
    private final Requester requester;
    private final JobService<? extends JobStatus<DeleteTextIndexResponse>> jobService;

    /**
     * Creates a new DeleteTextIndexPollingService
     * @param hodServiceConfig The configuration to use
     */
    public DeleteTextIndexPollingService(final HodServiceConfig hodServiceConfig) {
        super();

        deleteTextIndexBackend = hodServiceConfig.getRestAdapter().create(DeleteTextIndexBackend.class);
        requester = hodServiceConfig.getRequester();
        jobService = new JobServiceImpl<>(hodServiceConfig, DeleteTextIndexBackend.DeleteTextIndexJobStatus.class);
    }

    /**
     * Creates a new DeleteTextIndexPollingService
     * @param hodServiceConfig The configuration to use
     * @param executorService The executor service to use while polling for status updates
     */
    public DeleteTextIndexPollingService(final HodServiceConfig hodServiceConfig, final ScheduledExecutorService executorService) {
        super(executorService);

        deleteTextIndexBackend = hodServiceConfig.getRestAdapter().create(DeleteTextIndexBackend.class);
        requester = hodServiceConfig.getRequester();
        jobService = new JobServiceImpl<>(hodServiceConfig, DeleteTextIndexBackend.DeleteTextIndexJobStatus.class);
    }

    @Override
    public void deleteTextIndex(
        final ResourceIdentifier index,
        final HodJobCallback<DeleteTextIndexResponse> callback
    ) throws HodErrorException {
        final DeleteTextIndexResponse response = requester.makeRequest(DeleteTextIndexResponse.class, getInitialBackendCaller(index));

        final JobId jobId = requester.makeRequest(JobId.class, getDeletingBackendCaller(index, response));

        getExecutorService().submit(new PollingJobStatusRunnable<>(jobId, callback, getExecutorService(), jobService));
    }

    @Override
    public void deleteTextIndex(
        final TokenProxy tokenProxy,
        final ResourceIdentifier index,
        final HodJobCallback<DeleteTextIndexResponse> callback
    ) throws HodErrorException {
        final DeleteTextIndexResponse response = requester.makeRequest(tokenProxy, DeleteTextIndexResponse.class, getInitialBackendCaller(index));

        final JobId jobId = requester.makeRequest(tokenProxy, JobId.class, getDeletingBackendCaller(index, response));

        getExecutorService().submit(new PollingJobStatusRunnable<>(tokenProxy, jobId, callback, getExecutorService(), jobService));
    }

    private Requester.BackendCaller getInitialBackendCaller(final ResourceIdentifier index) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return deleteTextIndexBackend.initialDeleteTextIndex(authenticationToken, index);
            }
        };
    }

    private Requester.BackendCaller getDeletingBackendCaller(final ResourceIdentifier index, final DeleteTextIndexResponse response) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return deleteTextIndexBackend.deleteTextIndex(authenticationToken, index, response.getConfirm());
            }
        };
    }


}
