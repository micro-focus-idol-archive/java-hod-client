/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.job;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.Duration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Abstract service containing code common to all job services
 */
@Slf4j
public abstract class AbstractPollingService {

    @Getter(AccessLevel.PROTECTED)
    private final ScheduledExecutorService executorService;

    @Getter(AccessLevel.PROTECTED)
    private final Duration timeout;

    /**
     * Constructs a new service with the default executor service
     */
    public AbstractPollingService(final Duration timeout) {
        this(Executors.newScheduledThreadPool(8), timeout);
    }

    /**
     * Constructs a new service with the given executor service
     */
    public AbstractPollingService(final ScheduledExecutorService executorService, final Duration timeout) {
        this.executorService = executorService;
        this.timeout = timeout;
    }

    /**
     * Shuts down the executor service. This method should be called when the job service is no longer needed, if the
     * default executor service was used
     */
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

}
