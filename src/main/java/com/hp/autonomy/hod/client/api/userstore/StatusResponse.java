/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Basic API response just indicating if the action succeeded or failed.
 */
@Data
public class StatusResponse {
    private final boolean success;

    public StatusResponse(@JsonProperty("success") final boolean success) {
        this.success = success;
    }
}
