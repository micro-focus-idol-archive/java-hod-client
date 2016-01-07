/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication.tokeninformation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Information about an unbound token which can be obtained from the get token information API.
 */
@Data
public class UnboundTokenInformation {
    /**
     * @return Information about the authentication mechanism used to generate this token.
     */
    private final AuthenticationInformation authentication;

    public UnboundTokenInformation(@JsonProperty("auth") final AuthenticationInformation authentication) {
        this.authentication = authentication;
    }
}
