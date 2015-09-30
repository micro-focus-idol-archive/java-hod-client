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
public class ResourceFlavour implements Serializable {

    private static final long serialVersionUID = -1478092510670076594L;

    public static final ResourceFlavour STANDARD = new ResourceFlavour("standard");
    public static final ResourceFlavour EXPLORER = new ResourceFlavour("explorer");
    public static final ResourceFlavour CATEGORIZATION = new ResourceFlavour("categorization");
    public static final ResourceFlavour CUSTOM_FIELDS = new ResourceFlavour("custom_fields");
    public static final ResourceFlavour QUERY_MANIPULATION = new ResourceFlavour("querymanipulation");
    public static final ResourceFlavour WEB_CLOUD = new ResourceFlavour("web_cloud");
    public static final ResourceFlavour FILESYSTEM_ONSITE = new ResourceFlavour("filesystem_onsite");

    private final String name;

    public ResourceFlavour(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * @return A set containing all the ResourceFlavour instances declared in static fields
     */
    public static Set<ResourceFlavour> allOf() {
        return new HashSet<>(Arrays.asList(
            STANDARD,
            EXPLORER,
            CATEGORIZATION,
            CUSTOM_FIELDS,
            QUERY_MANIPULATION,
            WEB_CLOUD,
            FILESYSTEM_ONSITE
        ));
    }

    /**
     * Returns a set containing all the known resource flavours, except those is the given set
     * @param resourceFlavours The resource flavours which will not be included in the returned set
     * @return The complement of the given set of resource flavours
     */
    public static Set<ResourceFlavour> complementOf(final Set<ResourceFlavour> resourceFlavours) {
        final Set<ResourceFlavour> set = allOf();

        set.removeAll(resourceFlavours);

        return set;
    }

    /**
     * Returns a set containing the given resource flavours
     * @param first The first resource flavour to add
     * @param rest The remaining resource flavours to add
     * @return A set containing the given resource flavours
     */
    public static Set<ResourceFlavour> of(final ResourceFlavour first, final ResourceFlavour... rest) {
        final Set<ResourceFlavour> set = new HashSet<>();
        set.add(first);
        set.addAll(Arrays.asList(rest));

        return set;
    }
}
