/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication.tokeninformation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationType;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Information about the mechanism used to authenticate an entity associated with an authentication token.
 */
@Data
public class AuthenticationInformation implements Serializable {
    private static final long serialVersionUID = -9123269747470387420L;

    /**
     * @return The UUID of the authentication
     */
    private final UUID uuid;

    /**
     * @return The type of the authentication
     */
    private final AuthenticationType type;

    public AuthenticationInformation(
        @JsonProperty("uuid") final UUID uuid,
        @JsonProperty("type") final AuthenticationType type
    ) {
        this.uuid = uuid;
        this.type = type;
    }
}
