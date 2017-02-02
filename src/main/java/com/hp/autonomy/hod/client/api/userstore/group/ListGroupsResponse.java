/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import lombok.Data;

import java.util.List;

@Data
class ListGroupsResponse {
    private final List<Group> groups;
    private final ResourceName userStore;

    ListGroupsResponse(
        @JsonProperty("groups") final List<Group> groups,
        @JsonProperty("user_store") final ResourceName userStore
    ) {
        this.groups = groups;
        this.userStore = userStore;
    }
}
