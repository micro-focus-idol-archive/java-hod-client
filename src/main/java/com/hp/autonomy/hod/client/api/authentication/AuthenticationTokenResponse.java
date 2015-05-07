/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Response containing a token from HP Haven OnDemand
 */
@Getter
@EqualsAndHashCode
@ToString
public class AuthenticationTokenResponse {

    /**
     * @return The token from HP Haven OnDemand
     */
    private final AuthenticationToken token;

    @JsonCreator
    AuthenticationTokenResponse(@JsonProperty("token") final AuthenticationToken token) {
        this.token = token;
    }
}
