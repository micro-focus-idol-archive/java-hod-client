/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class User {
    private final String name;
    private final UUID uuid;

    public User(
        @JsonProperty("name") final String name,
        @JsonProperty("uuid") final UUID uuid
    ) {
        this.name = name;
        this.uuid = uuid;
    }
}
