/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserGroups {
    private final List<String> groups;
    private final List<String> directGroups;

    public UserGroups(
        @JsonProperty("groups") final List<String> groups,
        @JsonProperty("direct_groups") final List<String> directGroups
    ) {
        this.groups = groups;
        this.directGroups = directGroups;
    }
}
