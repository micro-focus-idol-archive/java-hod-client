/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import com.hp.autonomy.hod.client.util.MultiMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ListResourcesRequestBuilder {
    @SuppressWarnings("TypeMayBeWeakened")
    private Set<ResourceType> types = new HashSet<>();

    @SuppressWarnings("TypeMayBeWeakened")
    private Set<String> domains = new HashSet<>();

    /**
     * @param types Resource type restriction
     */
    public ListResourcesRequestBuilder setTypes(final Set<ResourceType> types) {
        if (types != null) {
            this.types = new HashSet<>(types);
        }

        return this;
    }

    /**
     * @param domains Domain name restriction
     */
    public ListResourcesRequestBuilder setDomains(final Set<String> domains) {
        if (domains != null) {
            this.domains = new HashSet<>(domains);
        }

        return this;
    }

    Map<String, Object> build() {
        final Map<String, Object> parameters = new MultiMap<>();

        for (final ResourceType type : types) {
            parameters.put("type", type);
        }

        for (final String domain : domains) {
            parameters.put("domain", domain);
        }

        return parameters;
    }
}
