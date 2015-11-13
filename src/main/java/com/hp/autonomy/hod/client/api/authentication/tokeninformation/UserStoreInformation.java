/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication.tokeninformation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Information about a user store associated with an authentication token.
 */
@Data
public class UserStoreInformation implements Serializable {
    private static final long serialVersionUID = 7276817124651403883L;

    /**
     * @return The UUID of the user store
     */
    private final UUID uuid;

    /**
     * @return The name of the user store
     */
    private final String name;

    /**
     * @return The domain of the user store
     */
    private final String domain;

    public UserStoreInformation(
        @JsonProperty("uuid") final UUID uuid,
        @JsonProperty("name") final String name,
        @JsonProperty("domain") final String domain
    ) {
        this.uuid = uuid;
        this.name = name;
        this.domain = domain;
    }

    public ResourceIdentifier getIdentifier() {
        return new ResourceIdentifier(domain, name);
    }
}
