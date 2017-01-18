/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Information about a HOD resource.
 */
@Data
@Builder
@JsonDeserialize(builder = ResourceInformation.ResourceInformationBuilder.class)
public class ResourceInformation implements Serializable {
    private static final long serialVersionUID = 7276817124651403883L;

    /**
     * @return The UUID of the resource
     */
    private final UUID uuid;

    /**
     * @return The name of the resource
     */
    private final String name;

    /**
     * @return The domain of the resource
     */
    private final String domain;

    public ResourceName getIdentifier() {
        return new ResourceName(domain, name);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class ResourceInformationBuilder {
        // TODO: Remove this once HOD-3394 has been resolved
        // Some APIs return domain_name instead of domain
        @JsonProperty("domain_name")
        public ResourceInformationBuilder domainName(final String domainName) {
            domain = domainName;
            return this;
        }
    }
}
