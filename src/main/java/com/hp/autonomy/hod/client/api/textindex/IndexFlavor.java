/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Type representing the flavor of an index. Known flavors are exposed as static constants.
 */
@Data
public class IndexFlavor implements Serializable {
    private static final long serialVersionUID = 4450074251836253214L;

    /**
     * The flavour of a standard text index
     */
    public static final IndexFlavor STANDARD = new IndexFlavor("standard");

    /**
     * The flavour of an explorer text index
     */
    public static final IndexFlavor EXPLORER = new IndexFlavor("explorer");

    /**
     * The flavour of a categorization text index
     */
    public static final IndexFlavor CATEGORIZATION = new IndexFlavor("categorization");

    /**
     * The flavour of a custom fields text index
     */
    public static final IndexFlavor CUSTOM_FIELDS = new IndexFlavor("custom_fields");

    /**
     * The flavour of a query manipulation text index
     */
    public static final IndexFlavor QUERY_MANIPULATION = new IndexFlavor("querymanipulation");

    /**
     * The flavour of a jumbo text index
     */
    public static final IndexFlavor JUMBO = new IndexFlavor("jumbo");

    private final String name;

    /**
     * Creates a new IndexFlavor with the given name
     * @param name The name of the IndexFlavor
     */
    public IndexFlavor(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * @return A set containing all the IndexFlavor instances declared in static fields
     */
    public static Set<IndexFlavor> allOf() {
        return new HashSet<>(Arrays.asList(
                STANDARD,
                EXPLORER,
                CATEGORIZATION,
                CUSTOM_FIELDS,
                QUERY_MANIPULATION,
                JUMBO
        ));
    }

    /**
     * Returns a set containing all the known index flavours, except those is the given set
     * @param indexFlavors The index flavours which will not be included in the returned set
     * @return The complement of the given set of index flavours
     */
    public static Set<IndexFlavor> complementOf(final Set<IndexFlavor> indexFlavors) {
        final Set<IndexFlavor> set = allOf();
        set.removeAll(indexFlavors);
        return set;
    }

    /**
     * Returns a set containing the given index flavours
     * @param first The first index flavour to add
     * @param rest The remaining index flavours to add
     * @return A set containing the given index flavours
     */
    public static Set<IndexFlavor> of(final IndexFlavor first, final IndexFlavor... rest) {
        final Set<IndexFlavor> set = new HashSet<>();
        set.add(first);
        set.addAll(Arrays.asList(rest));
        return set;
    }
}
