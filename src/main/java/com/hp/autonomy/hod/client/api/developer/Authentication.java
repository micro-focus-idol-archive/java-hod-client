/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.developer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import java.util.UUID;

@Data
@JsonDeserialize(builder = Authentication.Builder.class)
public class Authentication {
    private final UUID uuid;
    private final ApplicationAuthMode mode;
    private final DateTime createdAt;
    private final boolean active;

    private Authentication(final Builder builder) {
        uuid = builder.uuid;
        mode = builder.mode;
        active = builder.active;
        createdAt = builder.createdAt;
    }

    @Data
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private UUID uuid;
        private ApplicationAuthMode mode;
        private boolean active;
        private DateTime createdAt;

        public Builder setCreatedAt(final String createdAt) {
            this.createdAt = new DateTime(createdAt);
            return this;
        }

        public Builder setCreatedAt(final DateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Authentication build() {
            return new Authentication(this);
        }
    }
}
