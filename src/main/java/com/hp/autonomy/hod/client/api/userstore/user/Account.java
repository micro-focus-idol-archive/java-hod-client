/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Represents a Haven OnDemand account.
 */
@Data
public class Account {
    private final Type type;
    private final String account;
    private final Status status;
    private final boolean primary;

    public Account(
        @JsonProperty("type") final Type type,
        @JsonProperty("account") final String account,
        @JsonProperty("status") final Status status,
        @JsonProperty("is_primary") final boolean primary
    ) {
        this.type = type;
        this.account = account;
        this.status = status;
        this.primary = primary;
    }

    /**
     * Represents the type of a Haven OnDemand account.
     */
    @Data
    public static class Type implements Serializable {
        /**
         * The {@link Account#account} for a developer account is the developer UUID.
         */
        public static final Type DEVELOPER = new Type("developer");

        /**
         * The {@link Account#account} for an email account is the email address.
         */
        public static final Type EMAIL = new Type("email");

        private static final long serialVersionUID = -5875593359004072473L;

        private final String name;

        public Type(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Represents the status of a Haven OnDemand account.
     */
    @Data
    public static class Status {
        public static final Status PENDING = new Status("Pending");
        public static final Status CONFIRMED = new Status("Confirmed");
        public static final Status DISABLED = new Status("Disabled");

        private final String name;

        public Status(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
