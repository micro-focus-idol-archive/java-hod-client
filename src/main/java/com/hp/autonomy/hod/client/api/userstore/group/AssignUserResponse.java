/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import lombok.Data;

@Data
public class AssignUserResponse {
    private final boolean success;
    private final Result result;

    public AssignUserResponse(
        @JsonProperty("success") final boolean success,
        @JsonProperty("result") final Result result
    ) {
        this.success = success;
        this.result = result;
    }

    @Data
    public static class Result {
        private final ResourceIdentifier userStore;
        private final String groupName;
        private final String userUuid;

        public Result(
            @JsonProperty("user_store") final ResourceIdentifier userStore,
            @JsonProperty("group") final String groupName,
            @JsonProperty("uuid") final String userUuid
        ) {
            this.userStore = userStore;
            this.groupName = groupName;
            this.userUuid = userUuid;
        }
    }
}
