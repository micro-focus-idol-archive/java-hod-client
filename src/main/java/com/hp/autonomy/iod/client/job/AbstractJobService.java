/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.job;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class AbstractJobService {

    @Getter(AccessLevel.PROTECTED)
    private final ScheduledExecutorService executorService;

    public AbstractJobService() {
        this(Executors.newScheduledThreadPool(8));
    }

    public AbstractJobService(final ScheduledExecutorService executorService) {
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

}
