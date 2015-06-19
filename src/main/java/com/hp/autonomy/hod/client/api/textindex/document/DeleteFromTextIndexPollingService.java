/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.document;

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
import retrofit.client.Response;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Service for managing jobs ids from the DeleteFromTextIndex API
 * <p/>
 * The destroy method should be called when the service is no longer needed.
 */
public class DeleteFromTextIndexPollingService extends AbstractPollingService implements DeleteFromTextIndexService {

    private final DeleteFromTextIndexBackend deleteFromTextIndexBackend;
    private final Requester requester;
    private final JobService<? extends JobStatus<DeleteFromTextIndexResponse>> jobService;

    /**
     * Creates a new DeleteFromTextIndexPollingService
     * @param hodServiceConfig The configuration to use
     */
    public DeleteFromTextIndexPollingService(final HodServiceConfig hodServiceConfig) {
        super();

        deleteFromTextIndexBackend = hodServiceConfig.getRestAdapter().create(DeleteFromTextIndexBackend.class);
        jobService = new JobServiceImpl<>(hodServiceConfig, DeleteFromTextIndexBackend.DeleteFromTextIndexJobStatus.class);
        requester = hodServiceConfig.getRequester();
    }

    /**
     * Creates a new DeleteFromTextIndexPollingService
     * @param hodServiceConfig The configuration to use
     * @param executorService The executor service to use while polling for status updates
     */
    public DeleteFromTextIndexPollingService(final HodServiceConfig hodServiceConfig, final ScheduledExecutorService executorService) {
        super(executorService);

        deleteFromTextIndexBackend = hodServiceConfig.getRestAdapter().create(DeleteFromTextIndexBackend.class);
        jobService = new JobServiceImpl<>(hodServiceConfig, DeleteFromTextIndexBackend.DeleteFromTextIndexJobStatus.class);
        requester = hodServiceConfig.getRequester();
    }

    @Override
    public void deleteReferencesFromTextIndex(
        final String index,
        final List<String> references,
        final HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(JobId.class, getDeleteReferencesBackendCaller(index, references));

        getExecutorService().submit(new DeleteFromTextIndexPollingStatusRunnable(jobId, callback));
    }

    @Override
    public void deleteReferencesFromTextIndex(
        final TokenProxy tokenProxy,
        final String index,
        final List<String> references,
        final HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(tokenProxy, JobId.class, getDeleteReferencesBackendCaller(index, references));

        getExecutorService().submit(new DeleteFromTextIndexPollingStatusRunnable(tokenProxy, jobId, callback));
    }

    @Override
    public void deleteAllDocumentsFromTextIndex(
        final String index,
        final HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(JobId.class, getDeleteAllBackendCaller(index));

        getExecutorService().submit(new DeleteFromTextIndexPollingStatusRunnable(jobId, callback));
    }

    @Override
    public void deleteAllDocumentsFromTextIndex(
        final TokenProxy tokenProxy,
        final String index,
        final HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(JobId.class, getDeleteAllBackendCaller(index));

        getExecutorService().submit(new DeleteFromTextIndexPollingStatusRunnable(tokenProxy, jobId, callback));
    }

    private Requester.BackendCaller getDeleteReferencesBackendCaller(final String index, final List<String> references) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return deleteFromTextIndexBackend.deleteReferencesFromTextIndex(authenticationToken, index, references);
            }
        };
    }

    private Requester.BackendCaller getDeleteAllBackendCaller(final String index) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return deleteFromTextIndexBackend.deleteAllDocumentsFromTextIndex(authenticationToken, index);
            }
        };
    }

    private class DeleteFromTextIndexPollingStatusRunnable extends PollingJobStatusRunnable<DeleteFromTextIndexResponse> {

        public DeleteFromTextIndexPollingStatusRunnable(final JobId jobId, final HodJobCallback<DeleteFromTextIndexResponse> callback) {
            super(jobId, callback, getExecutorService());
        }

        public DeleteFromTextIndexPollingStatusRunnable(final TokenProxy token, final JobId jobId, final HodJobCallback<DeleteFromTextIndexResponse> callback) {
            super(token, jobId, callback, getExecutorService());
        }

        @Override
        public JobStatus<DeleteFromTextIndexResponse> getJobStatus(final JobId jobId) throws HodErrorException {
            return jobService.getJobStatus(jobId);
        }

        @Override
        @Deprecated
        public JobStatus<DeleteFromTextIndexResponse> getJobStatus(final AuthenticationToken token, final JobId jobId) throws HodErrorException {
            throw new UnsupportedOperationException();
        }

        @Override
        public JobStatus<DeleteFromTextIndexResponse> getJobStatus(final TokenProxy tokenProxy, final JobId jobId) throws HodErrorException {
            return jobService.getJobStatus(tokenProxy, jobId);
        }
    }

}
