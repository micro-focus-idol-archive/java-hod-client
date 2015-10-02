/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * Representation of a user as returned from the list users in a user store API.
 * @param <T> Type of the metadata associated with the user
 */
@Data
public class User<T> {
    /**
     * The UUID of the user
     */
    private final UUID uuid;

    /**
     * Groups directly associated with the user
     */
    private final List<String> directGroups;

    /**
     * Groups either directly or transitively associated with the user
     */
    private final List<String> groups;

    /**
     * Accounts associated with the user
     */
    private final List<Account> accounts;

    /**
     * Metadata associated with the user
     */
    private final T metadata;

    public User(
        @JsonProperty("uuid") final UUID uuid,
        @JsonProperty("direct_groups") final List<String> directGroups,
        @JsonProperty("groups") final List<String> groups,
        @JsonProperty("accounts") final List<Account> accounts,
        @JsonProperty("metadata") final T metadata
    ) {
        this.uuid = uuid;
        this.directGroups = directGroups;
        this.groups = groups;
        this.accounts = accounts;
        this.metadata = metadata;
    }
}
