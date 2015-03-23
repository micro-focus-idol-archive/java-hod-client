/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.job;

import com.hp.autonomy.iod.client.error.IodError;
import com.hp.autonomy.iod.client.error.IodErrorCode;
import com.hp.autonomy.iod.client.error.IodErrorException;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Runnable which will poll the IDOL OnDemand job status API until the job has finished or failed
 * @param <T> The type that will be returned if the job complete successfully
 */
@Slf4j
public abstract class PollingJobStatusRunnable<T> implements Runnable {

    private static final int MAX_TRIES = 3;
    private static final int WAIT_SECONDS = 2;

    private final Set<IodErrorCode> DO_NOT_RETRY_CODES = EnumSet.of(
            IodErrorCode.API_KEY_REQUIRED,
            IodErrorCode.INVALID_API_KEY,
            IodErrorCode.UNKNOWN_API_KEY,
            IodErrorCode.UNAUTHORIZED_API_KEY,
            IodErrorCode.USER_ACCOUNT_DISABLED,
            IodErrorCode.INVALID_JOB_ID
    );

    private final String apiKey;
    private final JobId jobId;
    private final IodJobCallback<T> callback;
    private final ScheduledExecutorService executorService;

    private final AtomicInteger tries = new AtomicInteger(0);

    /**
     * Creates a new PollingJobStatusRunnable
     * @param apiKey The API key used to submit the job
     * @param jobId The ID of the job
     * @param callback The callback that will be called with the result
     * @param executorService The executor service responsible for running the runnable
     */
    public PollingJobStatusRunnable(final String apiKey, final JobId jobId, final IodJobCallback<T> callback, final ScheduledExecutorService executorService) {
        this.apiKey = apiKey;
        this.jobId = jobId;
        this.callback = callback;
        this.executorService = executorService;
    }

    /**
     *
     * @param apiKey The API key used to submit the job
     * @param jobId The ID of the job
     * @return A job status of the correct return type
     * @throws IodErrorException
     */
    public abstract JobStatus<T> getJobStatus(final String apiKey, final JobId jobId) throws IodErrorException;

    /**
     * Checks the status of the job. If the job has not finished, the runnable will schedule itself to run again after a
     * short wait
     */
    @Override
    public void run() {
        try {
            log.debug("About to check status for jobId {}", jobId);

            final JobStatus<T> jobStatus = getJobStatus(apiKey, jobId);
            final Status jobStatusStatus = jobStatus.getStatus();

            if(jobStatusStatus == Status.FINISHED || jobStatusStatus == Status.FAILED) {
                for(final Action<T> action : jobStatus.getActions()) {
                    final Status status = action.getStatus();

                    if(status == Status.FINISHED) {
                        log.debug("Found a finished action, calling callback");

                        callback.success(action.getResult());
                    }
                    else if(status == Status.FAILED) {
                        log.debug("Found a failed action, calling callback");

                        for(final IodError error : action.getErrors()) {
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
        } catch (final IodErrorException e) {
            log.error("Error retrieving job status for jobId: {}", jobId);
            log.error("Cause:", e);

            if(DO_NOT_RETRY_CODES.contains(e.getErrorCode())) {
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