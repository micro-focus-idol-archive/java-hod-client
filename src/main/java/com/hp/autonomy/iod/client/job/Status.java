/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.job;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.HashMap;
import java.util.Map;

public enum Status {
    QUEUED("queued"),
    IN_PROGRESS("in progress"),
    FINISHED("finished"),
    FAILED("failed");

    private static final Map<String, Status> LOOKUP = new HashMap<>();

    static {
        for(final Status status : values()) {
            LOOKUP.put(status.iodName, status);
        }
    }

    private final String iodName;

    @JsonCreator
    public static Status fromJsonString(final String value) {
        return LOOKUP.get(value);
    }

    private Status(final String iodName) {
        this.iodName = iodName;
    }
}
