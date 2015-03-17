/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.job;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class JobStatus<T> {

    private final String jobId;
    private final Status status;
    private final List<? extends Action<T>> actions;

    @JsonCreator
    public JobStatus(@JsonProperty("jobID") final String jobId, @JsonProperty("status") final Status status, @JsonProperty("actions") final List<? extends Action<T>> actions) {
        this.jobId = jobId;
        this.status = status;
        this.actions = actions;
    }

}
