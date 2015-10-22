/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import lombok.Data;

import java.util.UUID;

/**
 * Represents the result from a request to assign a user to a group in a user store.
 */
@Data
public class AssignUserResponse {
    private final ResourceIdentifier userStore;
    private final String groupName;
    private final UUID userUuid;

    public AssignUserResponse(
        @JsonProperty("user_store") final ResourceIdentifier userStore,
        @JsonProperty("group") final String groupName,
        @JsonProperty("user_uuid") final UUID userUuid
    ) {
        this.userStore = userStore;
        this.groupName = groupName;
        this.userUuid = userUuid;
    }
}
