/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.error;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.hp.autonomy.hod.client.job.Action;
import com.hp.autonomy.hod.client.job.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@JsonDeserialize(builder = HodError.Builder.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HodError {

    private final int error;
    private final HodErrorCode errorCode;
    private final String reason;
    private final Object detail;
    private final List<Action<?>> actions;
    private final Status status;
    private final String message;

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private int error;
        private HodErrorCode errorCode;
        private String reason;
        private Object detail;
        private List<Action<?>> actions;
        private Status status;
        private String message;

        public Builder setError(final int error) {
            this.error = error;
            this.errorCode = HodErrorCode.fromCode(error);
            return this;
        }

        public HodError build() {
            return new HodError(error, errorCode, reason, detail, actions, status, message);
        }
    }

}
