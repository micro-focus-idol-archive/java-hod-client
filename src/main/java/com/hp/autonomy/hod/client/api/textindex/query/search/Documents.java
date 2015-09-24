/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Top level response from the QueryTextIndex API and FindSimilar API.
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = Documents.Builder.class)
public class Documents implements Serializable {

    private static final long serialVersionUID = 7657511117355673864L;

    /**
     * @return The list of documents returned by HP Haven OnDemand
     * @serial The list of documents returned by HP Haven OnDemand
     */
    private final List<Document> documents;

    /**
     * @return The total number of results found by HP Haven OnDemand. If the total_results parameter was not specified,
     * this will be null.
     * @serial The total number of results found by HP Haven OnDemand. If the total_results parameter was not specified,
     * this will be null.
     */
    private final Integer totalResults;

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {

        @SuppressWarnings("FieldMayBeFinal")
        private List<Document> documents = Collections.emptyList();

        private Integer totalResults;

        public Builder setDocuments(final List<Document> documents) {
            if (documents != null) {
                this.documents = new ArrayList<>(documents);
            }

            return this;
        }

        @JsonProperty("totalhits")
        public Builder setTotalResults(final Integer totalResults) {
            this.totalResults = totalResults;
            return this;
        }

        public Documents build() {
            return new Documents(documents, totalResults);
        }

    }

}
