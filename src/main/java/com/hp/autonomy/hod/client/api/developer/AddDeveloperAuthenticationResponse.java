/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.developer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.authentication.ApiKey;
import lombok.Data;

@Data
class AddDeveloperAuthenticationResponse {
    private final boolean success;
    private final Credentials credentials;

    AddDeveloperAuthenticationResponse(
        @JsonProperty("success") final boolean success,
        @JsonProperty("credentials") final Credentials credentials
    ) {
        this.success = success;
        this.credentials = credentials;
    }

    @Data
    static class Credentials {
        private final ApiKey apiKey;

        Credentials(@JsonProperty("apikey") final ApiKey apiKey) {
            this.apiKey = apiKey;
        }
    }
}
