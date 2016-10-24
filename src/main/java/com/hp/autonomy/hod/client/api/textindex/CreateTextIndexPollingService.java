/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex;


import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
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
 * Implementation of CreateTextIndexService which polls for job completion.
 * <p/>
 * The destroy method should be called when the service is no longer needed.
 */
@Slf4j
public class CreateTextIndexPollingService extends AbstractPollingService implements CreateTextIndexService {

    private final CreateTextIndexBackend createTextIndexBackend;
    private final Requester<?, TokenType.Simple> requester;
    private final JobService<? extends JobStatus<CreateTextIndexResponse>> jobService;

    /**
     * Creates a new CreateTextIndexPollingService
     * @param hodServiceConfig The configuration to use
     */
    public CreateTextIndexPollingService(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig) {
        super(hodServiceConfig.getAsyncTimeout());

        this.createTextIndexBackend = hodServiceConfig.getRestAdapter().create(CreateTextIndexBackend.class);
        requester = hodServiceConfig.getRequester();
        jobService = new JobServiceImpl<>(hodServiceConfig, CreateTextIndexBackend.CreateTextIndexJobStatus.class);
    }

    /**
     * Creates a new CreateTextIndexPollingService
     * @param hodServiceConfig The configuration to use
     * @param executorService The executor service to use while polling for status updates
     */
    public CreateTextIndexPollingService(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig, final ScheduledExecutorService executorService) {
        super(executorService, hodServiceConfig.getAsyncTimeout());

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

        getExecutorService().submit(new PollingJobStatusRunnable<>(getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    @Override
    public void createTextIndex(
        final TokenProxy<?, TokenType.Simple> tokenProxy,
        final String index,
        final IndexFlavor flavor,
        final CreateTextIndexRequestBuilder params,
        final HodJobCallback<CreateTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(tokenProxy, JobId.class, getBackendCaller(index, flavor, params));

        getExecutorService().submit(new PollingJobStatusRunnable<>(tokenProxy, getTimeout(), jobId, callback, getExecutorService(), jobService));
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getBackendCaller(final String index, final IndexFlavor flavor, final CreateTextIndexRequestBuilder params) {
        return authenticationToken -> createTextIndexBackend.createTextIndex(authenticationToken, index, flavor, params.build());
    }

}
