/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.job;

import com.hp.autonomy.iod.client.error.IodErrorCode;

/**
 * Callback used with the IDOL OnDemand job API.
 * @param <T> The type of the result if the job is successful
 */
public interface IodJobCallback<T> {

    /**
     * Called with the result of a successful action
     * @param result The value returned by IDOL OnDemand
     */
    void success(final T result);

    /**
     * Called when a job returns an error or an error occurs checking the job status
     * @param error The error returned by IDOL OnDemand
     */
    void error(final IodErrorCode error);

    /**
     * Called if a RuntimeException is thrown while checking the job status
     * @param exception The exception that was thrown
     */
    void handleException(final RuntimeException exception);

}
