/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication.tokeninformation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Information about the developer component of an authentication token.
 */
@Data
public class DeveloperInformation implements Serializable {
    private static final long serialVersionUID = 7164183428302314301L;

    /**
     * @return The UUID of the tenant containing the entities associated with this token
     */
    private final UUID uuid;

    /**
     * @return The name of the developer
     */
    private final String name;

    /**
     * @return Information about the mechanism used to authenticate the developer
     */
    private final AuthenticationInformation authentication;

    public DeveloperInformation(
        @JsonProperty("uuid") final UUID uuid,
        @JsonProperty("name") final String name,
        @JsonProperty("auth") final AuthenticationInformation authentication
    ) {
        this.uuid = uuid;
        this.name = name;
        this.authentication = authentication;
    }
}
