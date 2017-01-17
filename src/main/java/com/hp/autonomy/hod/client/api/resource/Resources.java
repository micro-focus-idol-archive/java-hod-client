/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
class Resources {
    private final List<Resource> resources;

    @JsonCreator
    Resources(@JsonProperty("resources") final List<Resource> resources) {
        this.resources = new LinkedList<>(resources);
    }
}
