/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.job;

import com.hp.autonomy.hod.client.error.HodErrorCode;

/**
 * Callback used with the HP Haven OnDemand job API.
 * @param <T> The type of the result if the job is successful
 */
public interface HodJobCallback<T> {

    /**
     * Called with the result of a successful action
     * @param result The value returned by HP Haven OnDemand
     */
    void success(final T result);

    /**
     * Called when a job returns an error or an error occurs checking the job status
     * @param error The error returned by HP Haven OnDemand
     */
    void error(final HodErrorCode error);

    /**
     * Called when a job runtime exceeds the timeout duration specified in the configuration
     */
    void timeout();

    /**
     * Called if a RuntimeException is thrown while checking the job status
     * @param exception The exception that was thrown
     */
    void handleException(final RuntimeException exception);

}
