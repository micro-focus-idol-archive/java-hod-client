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

/**
 * Represents the type of a resource in HP Haven OnDemand.
 */
@Data
public class ResourceType implements Serializable {

    private static final long serialVersionUID = 5840146896341397940L;

    /**
     * The resource type of a text index
     */
    public static final ResourceType CONTENT = new ResourceType("content");

    /**
     * The resource type of a connector
     */
    public static final ResourceType CONNECTOR = new ResourceType("connector");

    /**
     * The resource type of a query profile
     */
    public static final ResourceType QUERY_PROFILE = new ResourceType("query_profile");

    private final String name;

    /**
     * Creates a new resource type with the given name. The static fields declared on this class should be used in
     * preference to this where possible.
     * @param name The name of the resource type
     */
    public ResourceType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * @return A set containing all the ResourceType instances declared in static fields
     */
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

    /**
     * Returns a set containing the given resource types
     * @param first The first resource type to add
     * @param rest The remaining resource types to add
     * @return A set containing the given resource types
     */
    public static Set<ResourceType> of(final ResourceType first, final ResourceType... rest) {
        final Set<ResourceType> set = new HashSet<>();
        set.add(first);
        set.addAll(Arrays.asList(rest));

        return set;
    }

}
