/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import com.hp.autonomy.hod.client.util.MultiMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ListResourcesRequestBuilder {
    private Set<ResourceType> types = new HashSet<>();
    private Set<ResourceFlavour> flavours = new HashSet<>();

    /**
     * @param types Resource type restriction
     */
    public ListResourcesRequestBuilder setTypes(final Set<ResourceType> types) {
        if (types != null) {
            this.types = types;
        }

        return this;
    }

    /**
     * @param flavours Resource flavour restriction
     */
    public ListResourcesRequestBuilder setFlavours(final Set<ResourceFlavour> flavours) {
        if (flavours != null) {
            this.flavours = flavours;
        }

        return this;
    }

    Map<String, Object> build() {
        final Map<String, Object> parameters = new MultiMap<>();

        for (final ResourceType type : types) {
            parameters.put("type", type);
        }

        for (final ResourceFlavour flavour : flavours) {
            parameters.put("flavor", flavour);
        }

        return parameters;
    }
}
