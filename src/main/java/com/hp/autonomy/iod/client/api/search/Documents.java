/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

/**
 * Top level response from the QueryTextIndex API and FindSimilar API.
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = Documents.Builder.class)
public class Documents {

    /**
     * @returns The list of documents returned by IDOL OnDemand
     */
    private final List<Document> documents;

    /**
     * @returns The total number of results found by IDOL OnDemand. If the total_results parameter was not specified,
     * this will be null.
     */
    private final Integer totalResults;

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {

        @SuppressWarnings("FieldMayBeFinal")
        private List<Document> documents = Collections.emptyList();

        private Integer totalResults;

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
