/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.job;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodError;
import com.hp.autonomy.hod.client.error.HodErrorCode;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Runnable which will poll the HP Haven OnDemand job status API until the job has finished or failed
 * @param <T> The type that will be returned if the job complete successfully
 */
@Slf4j
public abstract class PollingJobStatusRunnable<T> implements Runnable {

    private static final int MAX_TRIES = 3;
    private static final int WAIT_SECONDS = 2;

    private final Set<HodErrorCode> DO_NOT_RETRY_CODES = EnumSet.of(
            HodErrorCode.API_KEY_REQUIRED,
            HodErrorCode.INVALID_API_KEY,
            HodErrorCode.UNKNOWN_API_KEY,
            HodErrorCode.UNAUTHORIZED_API_KEY,
            HodErrorCode.USER_ACCOUNT_DISABLED,
            HodErrorCode.INVALID_JOB_ID
    );

    // TODO: remove this when all the APIs use TokenProxy
    private final AuthenticationToken token;

    private final TokenProxy tokenProxy;
    private final JobId jobId;
    private final HodJobCallback<T> callback;
    private final ScheduledExecutorService executorService;

    private final AtomicInteger tries = new AtomicInteger(0);

    /**
     * Creates a new PollingJobStatusRunnable using a token provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param jobId The ID of the job
     * @param callback The callback that will be called with the result
     * @param executorService The executor service responsible for running the runnable
     */
    public PollingJobStatusRunnable(final JobId jobId, final HodJobCallback<T> callback, final ScheduledExecutorService executorService) {
        this((TokenProxy) null, jobId, callback, executorService);
    }

    /**
     * Creates a new PollingJobStatusRunnable using the given token
     * @param token The token used to submit the job
     * @param jobId The ID of the job
     * @param callback The callback that will be called with the result
     * @param executorService The executor service responsible for running the runnable
     */
    @Deprecated
    public PollingJobStatusRunnable(final AuthenticationToken token, final JobId jobId, final HodJobCallback<T> callback, final ScheduledExecutorService executorService) {
        this.tokenProxy = null;
        this.token = token;
        this.jobId = jobId;
        this.callback = callback;
        this.executorService = executorService;
    }

    /**
     * Creates a new PollingJobStatusRunnable using the given token proxy
     * @param tokenProxy The token proxy used to submit the job
     * @param jobId The ID of the job
     * @param callback The callback that will be called with the result
     * @param executorService The executor service responsible for running the runnable
     */
    public PollingJobStatusRunnable(final TokenProxy tokenProxy, final JobId jobId, final HodJobCallback<T> callback, final ScheduledExecutorService executorService) {
        this.tokenProxy = tokenProxy;
        this.token = null;
        this.jobId = jobId;
        this.callback = callback;
        this.executorService = executorService;
    }

    /**
     * Fetches the status of a job  using a token proxy provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param jobId The ID of the job
     * @return A job status of the correct return type
     * @throws HodErrorException
     */
    public abstract JobStatus<T> getJobStatus(final JobId jobId) throws HodErrorException;

    /**
     * Fetches the status of a job using the given token
     * @param token The token used to submit the job
     * @param jobId The ID of the job
     * @return A job status of the correct return type
     * @throws HodErrorException
     */
     // TODO: remove this once everything is upgraded
    @Deprecated
    public abstract JobStatus<T> getJobStatus(final AuthenticationToken token, final JobId jobId) throws HodErrorException;

    /**
     * Fetches the status of a job using the given token proxy
     * @param tokenProxy The token proxy used to submit the job
     * @param jobId The ID of the job
     * @return A job status of the correct return type
     * @throws HodErrorException
     */
    // TODO: make this abstract once everything is upgraded
    public JobStatus<T> getJobStatus(final TokenProxy tokenProxy, final JobId jobId) throws HodErrorException {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks the status of the job. If the job has not finished, the runnable will schedule itself to run again after a
     * short wait
     */
    @Override
    public void run() {
        try {
            log.debug("About to check status for jobId {}", jobId);

            final JobStatus<T> jobStatus;

            if (tokenProxy != null) {
                jobStatus = getJobStatus(tokenProxy, jobId);
            }
            else if (token != null) {
                jobStatus = getJobStatus(token, jobId);
            }
            else {
                jobStatus = getJobStatus(jobId);
            }

            final Status jobStatusStatus = jobStatus.getStatus();

            if (jobStatusStatus == Status.FINISHED || jobStatusStatus == Status.FAILED) {
                for (final Action<T> action : jobStatus.getActions()) {
                    final Status status = action.getStatus();

                    if (status == Status.FINISHED) {
                        log.debug("Found a finished action, calling callback");

                        callback.success(action.getResult());
                    }
                    else if (status == Status.FAILED) {
                        log.debug("Found a failed action, calling callback");

                        for (final HodError error : action.getErrors()) {
                            log.debug("Error callback called with: {}", error);

                            callback.error(error.getErrorCode());
                        }
                    }
                }
            }
            else {
                log.debug("Not finished or failed, retrying");

                // we got a status successfully, so reset the counter
                tries.set(0);

                executorService.schedule(this, WAIT_SECONDS, TimeUnit.SECONDS);
            }
        } catch (final HodErrorException e) {
            log.error("Error retrieving job status for jobId: {}", jobId);
            log.error("Cause:", e);

            if (DO_NOT_RETRY_CODES.contains(e.getErrorCode())) {
                log.error("Unrecoverable error, will not retry");

                callback.error(e.getErrorCode());
            }
            else if (tries.get() >= MAX_TRIES) {
                log.error("Max retries reached, will not retry");

                callback.error(e.getErrorCode());
            }
            else {
                log.error("Retrying");

                tries.incrementAndGet();

                executorService.schedule(this, WAIT_SECONDS, TimeUnit.SECONDS);
            }
        } catch (final RuntimeException e) {
            log.error("Error retrieving job status for jobId: {}", jobId);
            log.error("Cause:", e);

            callback.handleException(e);
        }
    }
}