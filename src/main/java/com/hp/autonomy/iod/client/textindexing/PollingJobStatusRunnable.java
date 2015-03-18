/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.textindexing;

import com.hp.autonomy.iod.client.error.IodError;
import com.hp.autonomy.iod.client.error.IodErrorCode;
import com.hp.autonomy.iod.client.error.IodErrorException;
import com.hp.autonomy.iod.client.job.Action;
import com.hp.autonomy.iod.client.job.IodJobCallback;
import com.hp.autonomy.iod.client.job.JobId;
import com.hp.autonomy.iod.client.job.JobStatus;
import com.hp.autonomy.iod.client.job.Status;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public abstract class PollingJobStatusRunnable<T> implements Runnable {

    private static final int MAX_TRIES = 3;

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

    public PollingJobStatusRunnable(final String apiKey, final JobId jobId, final IodJobCallback<T> callback, final ScheduledExecutorService executorService) {
        this.apiKey = apiKey;
        this.jobId = jobId;
        this.callback = callback;
        this.executorService = executorService;
    }

    public abstract JobStatus<T> getJobStatus(final String apiKey, final JobId jobId) throws IodErrorException;

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

                executorService.schedule(this, 2, TimeUnit.SECONDS);
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

                executorService.schedule(this, 2, TimeUnit.SECONDS);
            }
        } catch (final RuntimeException e) {
            log.error("Error retrieving job status for jobId: {}", jobId);
            log.error("Cause:", e);

            callback.handleException(e);
        }
    }
}