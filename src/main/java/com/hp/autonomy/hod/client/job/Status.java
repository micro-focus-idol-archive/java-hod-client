/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.job;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.HashMap;
import java.util.Map;

/**
 * Possible job statuses that may be returned from HP Haven OnDemand
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
            LOOKUP.put(status.hodName, status);
        }
    }

    private final String hodName;

    @JsonCreator
    public static Status fromJsonString(final String value) {
        return LOOKUP.get(value);
    }

    Status(final String hodName) {
        this.hodName = hodName;
    }
}
