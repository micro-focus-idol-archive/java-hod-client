/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.error;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.hp.autonomy.iod.client.job.Action;
import com.hp.autonomy.iod.client.job.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@JsonDeserialize(builder = IodError.Builder.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IodError {

    private final int error;
    private final IodErrorCode errorCode;
    private final String reason;
    private final Object detail;
    private final List<Action<?>> actions;
    private final Status status;

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private int error;
        private IodErrorCode errorCode;
        private String reason;
        private Object detail;
        private List<Action<?>> actions;
        private Status status;

        public Builder setError(final int error) {
            this.error = error;
            this.errorCode = IodErrorCode.fromCode(error);
            return this;
        }

        public IodError build() {
            return new IodError(error, errorCode, reason, detail, actions, status);
        }
    }

}
