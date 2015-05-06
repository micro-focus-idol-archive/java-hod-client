/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.job;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * The status of a job from HP Haven OnDemand
 * @param <T>
 */
@Data
public class JobStatus<T> {

    /**
     * @return The ID of the job
     */
    private final String jobId;

    /**
     * @return The status of the job
     */
    private final Status status;

    /**
     * @return A list of the actions which make up the job. Each action has its own status and result
     */
    private final List<? extends Action<T>> actions;

    @JsonCreator
    public JobStatus(@JsonProperty("jobID") final String jobId, @JsonProperty("status") final Status status, @JsonProperty("actions") final List<? extends Action<T>> actions) {
        this.jobId = jobId;
        this.status = status;
        this.actions = actions;
    }

}
