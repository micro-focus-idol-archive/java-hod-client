/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Top level response from the QueryTextIndex API and FindSimilar API.
 */
@Data
public class Documents<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 7657511117355673864L;

    /**
     * @return The list of documents returned by HP Haven OnDemand
     * @serial The list of documents returned by HP Haven OnDemand
     */
    private final List<T> documents;

    /**
     * @return The total number of results found by HP Haven OnDemand. If the total_results parameter was not specified,
     * this will be null.
     * @serial The total number of results found by HP Haven OnDemand. If the total_results parameter was not specified,
     * this will be null.
     */
    private final Integer totalResults;

    /**
     * @return The expanded query returned by HP Haven OnDemand. This will be null if no query profile was used.
     * @serial The expanded query returned by HP Haven OnDemand. This will be null if no query profile was used.
     */
    private final String expandedQuery;

    // We can't use a builder here because Jackson doesn't support Builders with generic types
    // https://github.com/FasterXML/jackson-databind/issues/921
    public Documents(
        @JsonProperty("documents") final List<T> documents,
        @JsonProperty("totalhits") final Integer totalResults,
        @JsonProperty("expandedQuery") final String expandedQuery
    ) {
        this.documents = documents;
        this.totalResults = totalResults;
        this.expandedQuery = expandedQuery;
    }

}
