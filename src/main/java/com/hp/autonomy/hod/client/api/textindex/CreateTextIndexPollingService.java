/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex;


import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
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
 * Service for managing jobs ids from the CreateTextIndex API.
 * <p/>
 * The destroy method should be called when the service is no longer needed.
 */
@Slf4j
public class CreateTextIndexPollingService extends AbstractPollingService implements CreateTextIndexService {

    private final CreateTextIndexBackend createTextIndexBackend;
    private final Requester requester;
    private final JobService<? extends JobStatus<CreateTextIndexResponse>> jobService;

    /**
     * Creates a new CreateTextIndexPollingService
     * @param hodServiceConfig The configuration to use
     */
    public CreateTextIndexPollingService(final HodServiceConfig hodServiceConfig) {
        super();

        this.createTextIndexBackend = hodServiceConfig.getRestAdapter().create(CreateTextIndexBackend.class);
        requester = hodServiceConfig.getRequester();
        jobService = new JobServiceImpl<>(hodServiceConfig, CreateTextIndexBackend.CreateTextIndexJobStatus.class);
    }

    /**
     * Creates a new CreateTextIndexPollingService
     * @param hodServiceConfig The configuration to use
     * @param executorService The executor service to use while polling for status updates
     */
    public CreateTextIndexPollingService(final HodServiceConfig hodServiceConfig, final ScheduledExecutorService executorService) {
        super(executorService);

        this.createTextIndexBackend = hodServiceConfig.getRestAdapter().create(CreateTextIndexBackend.class);
        requester = hodServiceConfig.getRequester();
        jobService = new JobServiceImpl<>(hodServiceConfig, CreateTextIndexBackend.CreateTextIndexJobStatus.class);
    }

    @Override
    public void createTextIndex(
        final String index,
        final IndexFlavor flavor,
        final CreateTextIndexRequestBuilder params,
        final HodJobCallback<CreateTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(JobId.class, getBackendCaller(index, flavor, params));

        getExecutorService().submit(new CreateTextIndexPollingStatusRunnable(jobId, callback));
    }

    @Override
    public void createTextIndex(
        final TokenProxy tokenProxy,
        final String index,
        final IndexFlavor flavor,
        final CreateTextIndexRequestBuilder params,
        final HodJobCallback<CreateTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(tokenProxy, JobId.class, getBackendCaller(index, flavor, params));

        getExecutorService().submit(new CreateTextIndexPollingStatusRunnable(tokenProxy, jobId, callback));
    }

    private Requester.BackendCaller getBackendCaller(final String index, final IndexFlavor flavor, final CreateTextIndexRequestBuilder params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return createTextIndexBackend.createTextIndex(authenticationToken, index, flavor, params.build());
            }
        };
    }

    private class CreateTextIndexPollingStatusRunnable extends PollingJobStatusRunnable<CreateTextIndexResponse> {

        private CreateTextIndexPollingStatusRunnable(final TokenProxy tokenProxy, final JobId jobId, final HodJobCallback<CreateTextIndexResponse> callback) {
            super(tokenProxy, jobId, callback, getExecutorService());
        }

        private CreateTextIndexPollingStatusRunnable(final JobId jobId, final HodJobCallback<CreateTextIndexResponse> callback) {
            super(jobId, callback, getExecutorService());
        }

        @Override
        public JobStatus<CreateTextIndexResponse> getJobStatus(final JobId jobId) throws HodErrorException {
            return jobService.getJobStatus(jobId);
        }

        @Override
        @Deprecated
        public JobStatus<CreateTextIndexResponse> getJobStatus(final AuthenticationToken token, final JobId jobId) throws HodErrorException {
            throw new UnsupportedOperationException();
        }

        @Override
        public JobStatus<CreateTextIndexResponse> getJobStatus(final TokenProxy tokenProxy, final JobId jobId) throws HodErrorException {
            return jobService.getJobStatus(tokenProxy, jobId);
        }
    }
}
