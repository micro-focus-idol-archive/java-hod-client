/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Information about a user store.
 */
@Data
@Builder
@JsonDeserialize(builder = UserStoreInformation.UserStoreInformationBuilder.class)
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

    public ResourceIdentifier getIdentifier() {
        return new ResourceIdentifier(domain, name);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserStoreInformationBuilder {
        // TODO: Remove this once HOD-3394 has been resolved
        // Some APIs return domain_name instead of domain
        @JsonProperty("domain_name")
        public UserStoreInformationBuilder domainName(final String domainName) {
            domain = domainName;
            return this;
        }
    }
}
