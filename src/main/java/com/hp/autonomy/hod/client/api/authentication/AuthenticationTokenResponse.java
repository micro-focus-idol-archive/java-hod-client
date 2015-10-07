/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthenticationTokenResponse {
    private final AuthenticationToken.Json tokenJson;

    @JsonCreator
    AuthenticationTokenResponse(@JsonProperty("token") final AuthenticationToken.Json tokenJson) {
        this.tokenJson = tokenJson;
    }
}
