/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Wrapper type for indexing JSON into HP Haven OnDemand
 * @param <T> The underlying object type, which will be converted to JSON. This type should have a "reference" property
 */
@Data
public class Documents<T> {

    private final List<T> documents;

    public Documents(final List<T> documents) {
        this.documents = documents;
    }

    @SafeVarargs
    public Documents(final T document0, final T... documents) {
        this.documents = new ArrayList<>();
        this.documents.add(document0);
        this.documents.addAll(Arrays.asList(documents));
    }

    /**
     * @return The list of objects represented by this object
     */
    @JsonProperty("document")
    public List<T> getDocuments() {
        return documents;
    }
}
