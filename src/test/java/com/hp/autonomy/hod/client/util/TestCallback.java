/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.util;

import com.hp.autonomy.hod.client.error.HodErrorCode;
import com.hp.autonomy.hod.client.job.HodJobCallback;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class TestCallback<T> implements HodJobCallback<T> {

    protected final CountDownLatch latch;

    private volatile T result;

    @Getter
    private volatile boolean timedOut;

    public TestCallback(final CountDownLatch latch) {
        this.latch = latch;
    }

    public T getResult() {
        return result;
    }

    @Override
    public void success(final T result) {
        log.debug("Result from HP Haven OnDemand: {}", result);

        this.result = result;

        latch.countDown();
    }

    @Override
    public void error(final HodErrorCode error) {
        log.error("Error code " + error + " returned from HP Haven OnDemand");

        latch.countDown();
    }

    @Override
    public void timeout()
    {
        log.error("Job timed out");

        timedOut = true;
        latch.countDown();
    }

    @Override
    public void handleException(final RuntimeException exception) {
        log.error("Runtime exception thrown", exception);

        latch.countDown();
    }
}
