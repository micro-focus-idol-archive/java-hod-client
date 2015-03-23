/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.util;

import com.hp.autonomy.iod.client.error.IodErrorCode;
import com.hp.autonomy.iod.client.job.IodJobCallback;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class TestCallback<T> implements IodJobCallback<T> {

    protected final CountDownLatch latch;

    private volatile T result;

    public TestCallback(final CountDownLatch latch) {
        this.latch = latch;
    }

    public T getResult() {
        return result;
    }

    @Override
    public void success(final T result) {
        log.debug("Result from IDOL OnDemand: {}", result);

        this.result = result;

        latch.countDown();
    }

    @Override
    public void error(final IodErrorCode error) {
        log.error("Error code " + error + " returned from IDOL OnDemand");

        latch.countDown();
    }

    @Override
    public void handleException(final RuntimeException exception) {
        log.error("Runtime exception thrown", exception);

        latch.countDown();
    }
}
