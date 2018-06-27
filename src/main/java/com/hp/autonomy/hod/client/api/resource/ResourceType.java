/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the type of a resource in Micro Focus Haven OnDemand.
 */
@Data
public class ResourceType implements Serializable {
    private static final long serialVersionUID = 5840146896341397940L;

    /**
     * The resource type of a text index
     */
    public static final ResourceType TEXT_INDEX = new ResourceType("text_index");

    /**
     * The resource type of a connector
     */
    public static final ResourceType CONNECTOR = new ResourceType("connector");

    /**
     * The resource type of a query profile
     */
    public static final ResourceType QUERY_PROFILE = new ResourceType("query_profile");

    /**
     * The resource type of a language model
     */
    public static final ResourceType LANGUAGE_MODEL = new ResourceType("language_model");

    /**
     * The resource type of a user store
     */
    public static final ResourceType USER_STORE = new ResourceType("user_store");

    /**
     * The resource type of combinations
     */
    public static final ResourceType COMBINATIONS = new ResourceType("combinations");

    private final String name;

    /**
     * Creates a new resource type with the given name. The static fields declared on this class should be used in
     * preference to this where possible.
     *
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
                TEXT_INDEX,
                CONNECTOR,
                QUERY_PROFILE,
                LANGUAGE_MODEL,
                USER_STORE,
                COMBINATIONS
        ));
    }

    /**
     * Returns a set containing all the known resource types, except those is the given set
     *
     * @param resourceTypes The resource types which will not be included in the returned set
     * @return The complement of the given set of resource types
     */
    public static Set<ResourceType> complementOf(final Set<ResourceType> resourceTypes) {
        final Set<ResourceType> set = allOf();
        set.removeAll(resourceTypes);
        return set;
    }

    /**
     * Returns a set containing the given resource types
     *
     * @param first The first resource type to add
     * @param rest  The remaining resource types to add
     * @return A set containing the given resource types
     */
    public static Set<ResourceType> of(final ResourceType first, final ResourceType... rest) {
        final Set<ResourceType> set = new HashSet<>();
        set.add(first);
        set.addAll(Arrays.asList(rest));
        return set;
    }

}
