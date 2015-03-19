/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Wrapper class for an IDOL OnDemand job ID.
 */
@Data
@JsonDeserialize(builder = JobId.Builder.class)
public class JobId {

    /**
     * @return The job ID of the IDOL OnDemand job
     */
    private final String jobId;

    private JobId(final String jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return jobId;
    }

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {

        @JsonProperty("jobID")
        private String jobId;

        public JobId build() {
            return new JobId(jobId);
        }

    }

}
