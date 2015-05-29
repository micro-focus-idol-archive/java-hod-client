/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Resources {
    /**
     * @return A list of private resources
     */
    private final List<Resource> resources;

    /**
     * @return A list of public resources
     */
    private final List<Resource> publicResources;

    public Resources(
            @JsonProperty("private_resources") final List<Resource> resources,
            @JsonProperty("public_resources") final List<Resource> publicResources
    ) {
        this.resources = resources;
        this.publicResources = publicResources;
    }
}
