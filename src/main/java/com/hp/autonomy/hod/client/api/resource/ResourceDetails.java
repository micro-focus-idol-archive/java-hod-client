/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class ResourceDetails implements Serializable {
    private static final long serialVersionUID = -6529460305631289926L;

    /**
     * @return The name, domain and UUID of the resource
     */
    private final ResourceInformation resource;

    /**
     * @return The display name of the resource
     */
    private final String displayName;

    /**
     * @return The resource description
     */
    private final String description;

    /**
     * @return The resource type
     */
    private final ResourceType type;

    /**
     * @return The date created
     */
    private final Instant dateCreated;

    public ResourceDetails(
            @JsonProperty("resource") final ResourceInformation resource,
            @JsonProperty("description") final String description,
            @JsonProperty("type") final ResourceType type,
            @JsonProperty("date_created") final String dateCreated,
            @JsonProperty("display_name") final String displayName
    ) {
        this.resource = resource;
        this.description = description;
        this.type = type;
        this.displayName = displayName;

        this.dateCreated = dateCreated != null ? Instant.parse(dateCreated) : null;
    }
}
