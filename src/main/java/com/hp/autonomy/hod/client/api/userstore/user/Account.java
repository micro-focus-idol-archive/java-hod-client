/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Represents a Haven OnDemand account.
 */
@Data
public class Account {
    private final Type type;
    private final String value;
    private final boolean primary;

    public Account(
        @JsonProperty("type") final Type type,
        @JsonProperty("value") final String value,
        @JsonProperty("primary") final boolean primary
    ) {
        this.type = type;
        this.value = value;
        this.primary = primary;
    }

    @Data
    public static class Type {
        /**
         * The {@link Account#value} for a developer account is the developer UUID.
         */
        public static final Type DEVELOPER = new Type("developer");

        /**
         * The {@link Account#value} for an email account is the email address.
         */
        public static final Type EMAIL = new Type("email");

        private final String name;

        public Type(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
