/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.document;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.AbstractJobService;
import com.hp.autonomy.hod.client.job.HodJobCallback;
import com.hp.autonomy.hod.client.job.JobId;
import com.hp.autonomy.hod.client.job.JobStatus;
import com.hp.autonomy.hod.client.job.PollingJobStatusRunnable;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.util.TypedByteArrayWithFilename;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Service for managing jobs ids from the AddToTextIndex API.
 * <p/>
 * The destroy method should be called when the service is no longer needed.
 */
@Slf4j
public class AddToTextIndexJobService extends AbstractJobService implements AddToTextIndexService {

    private final AddToTextIndexBackend addToTextIndexBackend;
    private final Requester requester;

    /**
     * Creates a new AddToTextIndexJobService
     * @param hodServiceConfig The configuration for the service
     */
    public AddToTextIndexJobService(final HodServiceConfig hodServiceConfig) {
        super();

        addToTextIndexBackend = hodServiceConfig.getRestAdapter().create(AddToTextIndexBackend.class);
        requester = hodServiceConfig.getRequester();
    }

    /**
     * Creates a new AddToTextIndexJobService
     * @param hodServiceConfig The configuration for the service
     * @param executorService The executor service to use while polling for status updates
     */
    public AddToTextIndexJobService(final HodServiceConfig hodServiceConfig, final ScheduledExecutorService executorService) {
        super(executorService);

        addToTextIndexBackend = hodServiceConfig.getRestAdapter().create(AddToTextIndexBackend.class);
        requester = hodServiceConfig.getRequester();
    }

    @Override
    public void addJsonToTextIndex(
        final Documents<?> documents,
        final String index,
        final Map<String, Object> params,
        final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(JobId.class, getTextBackendCaller(documents, index, params));

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(jobId, callback));
    }

    @Override
    public void addJsonToTextIndex(
        final TokenProxy tokenProxy,
        final Documents<?> documents,
        final String index,
        final Map<String, Object> params,
        final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(tokenProxy, JobId.class, getTextBackendCaller(documents, index, params));

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(tokenProxy, jobId, callback));
    }

    @Override
    public void addUrlToTextIndex(
        final String url,
        final String index,
        final Map<String, Object> params,
        final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(JobId.class, getUrlBackendCaller(url, index, params));

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(jobId, callback));
    }

    @Override
    public void addUrlToTextIndex(
        final TokenProxy tokenProxy,
        final String url,
        final String index,
        final Map<String, Object> params,
        final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(tokenProxy, JobId.class, getUrlBackendCaller(url, index, params));

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(tokenProxy, jobId, callback));
    }

    @Override
    public void addReferenceToTextIndex(
        final String reference,
        final String index,
        final Map<String, Object> params,
        final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(JobId.class, getReferenceBackendCaller(reference, index, params));

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(jobId, callback));
    }

    @Override
    public void addReferenceToTextIndex(
        final TokenProxy tokenProxy,
        final String reference,
        final String index,
        final Map<String, Object> params,
        final HodJobCallback<AddToTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = requester.makeRequest(tokenProxy, JobId.class, getReferenceBackendCaller(reference, index, params));

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(tokenProxy, jobId, callback));
    }

    @Override
    public void addFileToTextIndex(final File file, final String index, final Map<String, Object> params, final HodJobCallback<AddToTextIndexResponse> callback) throws HodErrorException {
        final JobId jobId = requester.makeRequest(JobId.class, getFileBackendCaller(file, index, params));

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(jobId, callback));
    }

    @Override
    public void addFileToTextIndex(final TokenProxy tokenProxy, final File file, final String index, final Map<String, Object> params, final HodJobCallback<AddToTextIndexResponse> callback) throws HodErrorException {
        final JobId jobId = requester.makeRequest(tokenProxy, JobId.class, getFileBackendCaller(file, index, params));

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(tokenProxy, jobId, callback));
    }

    @Override
    public void addFileToTextIndex(final byte[] bytes, final String index, final Map<String, Object> params, final HodJobCallback<AddToTextIndexResponse> callback) throws HodErrorException {
        final JobId jobId = requester.makeRequest(JobId.class, getByteArrayBackendCaller(bytes, index, params));

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(jobId, callback));
    }

    @Override
    public void addFileToTextIndex(final TokenProxy tokenProxy, final byte[] bytes, final String index, final Map<String, Object> params, final HodJobCallback<AddToTextIndexResponse> callback) throws HodErrorException {
        final JobId jobId = requester.makeRequest(tokenProxy, JobId.class, getByteArrayBackendCaller(bytes, index, params));

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(tokenProxy, jobId, callback));
    }

    @Override
    public void addFileToTextIndex(final InputStream inputStream, final String index, final Map<String, Object> params, final HodJobCallback<AddToTextIndexResponse> callback) throws HodErrorException {
        final JobId jobId = requester.makeRequest(JobId.class, getInputStreamBackendCaller(inputStream, index, params));

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(jobId, callback));
    }

    @Override
    public void addFileToTextIndex(final TokenProxy tokenProxy, final InputStream inputStream, final String index, final Map<String, Object> params, final HodJobCallback<AddToTextIndexResponse> callback) throws HodErrorException {
        final JobId jobId = requester.makeRequest(tokenProxy, JobId.class, getInputStreamBackendCaller(inputStream, index, params));

        getExecutorService().submit(new AddToTextIndexPollingStatusRunnable(tokenProxy, jobId, callback));
    }

    private Requester.BackendCaller getTextBackendCaller(final Documents<?> documents, final String index, final Map<String, Object> params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return addToTextIndexBackend.addJsonToTextIndex(authenticationToken, documents, index, params);
            }
        };
    }

    private Requester.BackendCaller getUrlBackendCaller(final String url, final String index, final Map<String, Object> params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return addToTextIndexBackend.addUrlToTextIndex(authenticationToken, url, index, params);
            }
        };
    }

    private Requester.BackendCaller getReferenceBackendCaller(final String reference, final String index, final Map<String, Object> params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return addToTextIndexBackend.addReferenceToTextIndex(authenticationToken, reference, index, params);
            }
        };
    }

    private Requester.BackendCaller getFileBackendCaller(final File file, final String index, final Map<String, Object> params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return addToTextIndexBackend.addFileToTextIndex(authenticationToken, new TypedFile("application/octet-stream", file), index, params);
            }
        };
    }

    private Requester.BackendCaller getByteArrayBackendCaller(final byte[] bytes, final String index, final Map<String, Object> params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return addToTextIndexBackend.addFileToTextIndex(authenticationToken, new TypedByteArrayWithFilename("application/octet-stream", bytes), index, params);
            }
        };
    }

    private Requester.BackendCaller getInputStreamBackendCaller(final InputStream inputStream, final String index, final Map<String, Object> params) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                try {
                    return addToTextIndexBackend.addFileToTextIndex(authenticationToken, new TypedByteArrayWithFilename("application/octet-stream", IOUtils.toByteArray(inputStream)), index, params);
                } catch (final IOException e) {
                    throw new RuntimeException("Error reading bytes from stream", e);
                }
            }
        };
    }

    private class AddToTextIndexPollingStatusRunnable extends PollingJobStatusRunnable<AddToTextIndexResponse> {

        private AddToTextIndexPollingStatusRunnable(final JobId jobId, final HodJobCallback<AddToTextIndexResponse> callback) {
            super(jobId, callback, getExecutorService());
        }

        private AddToTextIndexPollingStatusRunnable(final TokenProxy token, final JobId jobId, final HodJobCallback<AddToTextIndexResponse> callback) {
            super(token, jobId, callback, getExecutorService());
        }

        @Override
        public JobStatus<AddToTextIndexResponse> getJobStatus(final JobId jobId) throws HodErrorException {
            return requester.makeRequest(AddToTextIndexBackend.AddToTextIndexJobStatus.class, getBackendCaller(jobId));
        }

        @Override
        public JobStatus<AddToTextIndexResponse> getJobStatus(final AuthenticationToken token, final JobId jobId) throws HodErrorException {
            throw new UnsupportedOperationException();
        }

        @Override
        public JobStatus<AddToTextIndexResponse> getJobStatus(final TokenProxy tokenProxy, final JobId jobId) throws HodErrorException {
            return requester.makeRequest(tokenProxy, AddToTextIndexBackend.AddToTextIndexJobStatus.class, getBackendCaller(jobId));
        }

        private Requester.BackendCaller getBackendCaller(final JobId jobId) {
            return new Requester.BackendCaller() {
                @Override
                public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                    return addToTextIndexBackend.getJobStatus(authenticationToken, jobId);
                }
            };
        }

    }
}
