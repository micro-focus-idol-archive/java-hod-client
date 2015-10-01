/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// The useful information in the response to assigning a user to a group is contained in the "result" property
@Data
class AssignUserResponseWrapper {
    private final boolean success;
    private final AssignUserResponse result;

    public AssignUserResponseWrapper(
        @JsonProperty("success") final boolean success,
        @JsonProperty("result") final AssignUserResponse result
    ) {
        this.success = success;
        this.result = result;
    }
}
