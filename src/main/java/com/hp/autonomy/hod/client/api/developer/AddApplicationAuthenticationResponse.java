/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.developer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.authentication.ApiKey;
import lombok.Data;

@Data
class AddApplicationAuthenticationResponse {
    private final boolean success;
    private final Credentials credentials;

    AddApplicationAuthenticationResponse(
        @JsonProperty("success") final boolean success,
        @JsonProperty("credentials") final Credentials credentials
    ) {
        this.success = success;
        this.credentials = credentials;
    }

    @Data
    static class Credentials {
        private final ApiKey applicationApiKey;

        Credentials(@JsonProperty("applicationAPIKey") final ApiKey applicationApiKey) {
            this.applicationApiKey = applicationApiKey;
        }
    }
}
