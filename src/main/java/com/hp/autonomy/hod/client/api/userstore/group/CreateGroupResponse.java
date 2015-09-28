/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import lombok.Data;

@Data
public class CreateGroupResponse {
    private final boolean success;
    private final ResourceIdentifier userStore;
    private final String groupName;

    public CreateGroupResponse(
        @JsonProperty("success") final boolean success,
        @JsonProperty("user_store") final ResourceIdentifier userStore,
        @JsonProperty("group") final String groupName
    ) {
        this.success = success;
        this.userStore = userStore;
        this.groupName = groupName;
    }
}
