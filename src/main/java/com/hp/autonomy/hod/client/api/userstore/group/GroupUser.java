/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
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
class GroupUser {
    private final UUID uuid;

    GroupUser(@JsonProperty("uuid") final UUID uuid) {
        this.uuid = uuid;
    }
}
