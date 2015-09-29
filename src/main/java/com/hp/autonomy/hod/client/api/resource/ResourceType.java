/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
public class ResourceType implements Serializable {

    private static final long serialVersionUID = 5840146896341397940L;

    public static final ResourceType CONTENT = new ResourceType("content");
    public static final ResourceType CONNECTOR = new ResourceType("connector");
    public static final ResourceType QUERY_PROFILE = new ResourceType("query_profile");

    private final String name;

    public ResourceType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Set<ResourceType> allOf() {
        return new HashSet<>(Arrays.asList(
            CONTENT,
            CONNECTOR,
            QUERY_PROFILE
        ));
    }

    public static Set<ResourceType> complementOf(final Set<ResourceType> resourceTypes) {
        final Set<ResourceType> set = allOf();

        set.removeAll(resourceTypes);

        return resourceTypes;
    }

    public static Set<ResourceType> of(final ResourceType first, final ResourceType... rest) {
        final Set<ResourceType> set = new HashSet<>();
        set.add(first);
        set.addAll(Arrays.asList(rest));

        return set;
    }

}
