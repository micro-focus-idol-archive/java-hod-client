/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Group {
    private final String name;
    private final List<String> parents;
    private final List<String> children;

    public Group(
        @JsonProperty("name") final String name,
        @JsonProperty("parents") final List<String> parents,
        @JsonProperty("children") final List<String> children
    ) {
        this.name = name;
        this.parents = parents;
        this.children = children;
    }
}
