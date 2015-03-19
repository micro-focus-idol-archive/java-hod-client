/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.job;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.iod.client.error.IodError;
import lombok.Data;

import java.util.List;

@Data
public class Action<T> {

    private final String action;
    private final Status status;
    private final List<IodError> errors;
    private final T result;
    private final String version;

    @JsonCreator
    public Action(
            @JsonProperty("action") final String action,
            @JsonProperty("status") final Status status,
            @JsonProperty("errors") final List<IodError> errors,
            @JsonProperty("result") final T result,
            @JsonProperty("version") final String version
    ) {
        this.action = action;
        this.status = status;
        this.errors = errors;
        this.result = result;
        this.version = version;
    }

}
