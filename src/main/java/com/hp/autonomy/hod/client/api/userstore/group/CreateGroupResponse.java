/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import lombok.Data;

@Data
public class CreateGroupResponse {
    private final ResourceName userStore;
    private final String groupName;

    public CreateGroupResponse(
        @JsonProperty("user_store") final ResourceName userStore,
        @JsonProperty("group") final String groupName
    ) {
        this.userStore = userStore;
        this.groupName = groupName;
    }
}
