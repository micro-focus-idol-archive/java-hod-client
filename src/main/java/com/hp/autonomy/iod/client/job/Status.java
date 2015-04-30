/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.job;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.HashMap;
import java.util.Map;

/**
 * Possible job statuses that may be returned from IDOL OnDemand
 */
public enum Status {

    /**
     * The job is in the queue, awaiting processing
     */
    QUEUED("queued"),

    /**
     * The job is currently processing
     */
    IN_PROGRESS("in progress"),

    /**
     * The job finished processing successfully
     */
    FINISHED("finished"),

    /**
     * An error occurred while processing the job
     */
    FAILED("failed");

    private static final Map<String, Status> LOOKUP = new HashMap<>();

    static {
        for (final Status status : values()) {
            LOOKUP.put(status.iodName, status);
        }
    }

    private final String iodName;

    @JsonCreator
    public static Status fromJsonString(final String value) {
        return LOOKUP.get(value);
    }

    Status(final String iodName) {
        this.iodName = iodName;
    }
}
