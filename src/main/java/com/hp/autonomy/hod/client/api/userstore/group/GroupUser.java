/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

/**
 * Representation of a user as returned from the get group info API.
 */
@Data
public class GroupUser {
    private final String name;
    private final UUID uuid;

    public GroupUser(
        @JsonProperty("name") final String name,
        @JsonProperty("uuid") final UUID uuid
    ) {
        this.name = name;
        this.uuid = uuid;
    }
}
