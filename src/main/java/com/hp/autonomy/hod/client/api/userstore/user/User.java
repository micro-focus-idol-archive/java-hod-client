/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class User<T> {
    private final UUID uuid;
    private final List<String> groups;
    private final List<Account> accounts;
    private final T metadata;

    public User(
        @JsonProperty("uuid") final UUID uuid,
        @JsonProperty("groups") final List<String> groups,
        @JsonProperty("accounts") final List<Account> accounts,
        @JsonProperty("metadata") final T metadata
    ) {
        this.uuid = uuid;
        this.groups = groups;
        this.accounts = accounts;
        this.metadata = metadata;
    }
}
