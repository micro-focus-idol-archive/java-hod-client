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
import retrofit.mime.TypedInput;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class AddToTextIndexServiceImpl {

    private final AddToTextIndexService addToTextIndexService;

    private final ScheduledExecutorService executorService;

    public AddToTextIndexServiceImpl(final AddToTextIndexService addToTextIndexService) {
        this(addToTextIndexService, Executors.newScheduledThreadPool(8));
    }

    public AddToTextIndexServiceImpl(final AddToTextIndexService addToTextIndexService, final ScheduledExecutorService executorService) {
        this.addToTextIndexService = addToTextIndexService;
        this.executorService = executorService;
    }

    public void destroy() {
        log.debug("Shutting down executor service");

        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                log.debug("Timed out waiting for executor service to die, calling shutdownNow");
                executorService.shutdownNow();
            }
        } catch (final InterruptedException e) {
            log.debug("Interrupted waiting for executor service to die, calling shutdownNow");
            executorService.shutdownNow();
        }
    }

    public void addJsonToTextIndex(
            final String apiKey,
            final Documents<?> documents,
            final String index,
            final Map<String, Object> params,
            final IodJobCallback<AddToTextIndexResponse> callback
    ) throws IodErrorException {
        final JobId jobId = addToTextIndexService.addJsonToTextIndex(apiKey, documents, index, params);

        executorService.submit(new PollingStatusRunnable(apiKey, jobId, callback));
    }

    public void addUrlToTextIndex(
            final String apiKey,
            final String url,
            final String index,
            final Map<String, Object> params,
            final IodJobCallback<AddToTextIndexResponse> callback
    ) throws IodErrorException {
        final JobId jobId = addToTextIndexService.addUrlToTextIndex(apiKey, url, index, params);

        executorService.submit(new PollingStatusRunnable(apiKey, jobId, callback));
    }

    public void addReferenceToTextIndex(
            final String apiKey,
            final String reference,
            final String index,
            final Map<String, Object> params,
            final IodJobCallback<AddToTextIndexResponse> callback
    ) throws IodErrorException {
        final JobId jobId = addToTextIndexService.addReferenceToTextIndex(apiKey, reference, index, params);

        executorService.submit(new PollingStatusRunnable(apiKey, jobId, callback));
    }

    public void addFileToTextIndex(
            final String apiKey,
            final TypedInput file,
            final String index,
            final Map<String, Object> params,
            final IodJobCallback<AddToTextIndexResponse> callback
    ) throws IodErrorException {
        final JobId jobId = addToTextIndexService.addFileToTextIndex(apiKey, file, index, params);

        executorService.submit(new PollingStatusRunnable(apiKey, jobId, callback));
    }

    private class PollingStatusRunnable implements Runnable {

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
        private final IodJobCallback<AddToTextIndexResponse> callback;

        private final AtomicInteger tries = new AtomicInteger(0);

        private PollingStatusRunnable(final String apiKey, final JobId jobId, final IodJobCallback<AddToTextIndexResponse> callback) {
            this.apiKey = apiKey;
            this.jobId = jobId;
            this.callback = callback;
        }

        @Override
        public void run() {
            try {
                log.debug("About to check status for jobId {}", jobId);

                final JobStatus<AddToTextIndexResponse> jobStatus = addToTextIndexService.getJobStatus(apiKey, jobId);
                final Status jobStatusStatus = jobStatus.getStatus();

                if(jobStatusStatus == Status.FINISHED || jobStatusStatus == Status.FAILED) {
                    for(final Action<AddToTextIndexResponse> action : jobStatus.getActions()) {
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
}
