/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Representation of a user as returned from the list users in a user store API.
 * @param <T> Type of metadata values associated with the user
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
    private final Map<String, T> metadata;

    public User(
        final UUID uuid,
        final List<String> directGroups,
        final List<String> groups,
        final List<Account> accounts,
        final Map<String, T> metadata
    ) {
        this.uuid = uuid;
        this.directGroups = directGroups;
        this.groups = groups;
        this.accounts = accounts;
        this.metadata = metadata;
    }

    User(final Json json, final Map<String, T> metadata) {
        this(json.uuid, json.directGroups, json.groups, json.accounts, metadata);
    }

    // Helper class so we can parse the metadata list returned from HOD into a map of user-defined types
    @Data
    static class Json {
        private final UUID uuid;
        private final List<String> directGroups;
        private final List<String> groups;
        private final List<Account> accounts;
        private final List<Metadata<JsonNode>> metadataList;

        Json(
            @JsonProperty("uuid") final UUID uuid,
            @JsonProperty("direct_groups") final List<String> directGroups,
            @JsonProperty("groups") final List<String> groups,
            @JsonProperty("accounts") final List<Account> accounts,
            @JsonProperty("metadata") final List<Metadata<JsonNode>> metadataList
        ) {
            this.uuid = uuid;
            this.directGroups = directGroups;
            this.groups = groups;
            this.accounts = accounts;
            this.metadataList = metadataList;
        }
    }
}
