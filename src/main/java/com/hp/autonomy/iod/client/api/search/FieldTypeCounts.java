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


/**
 * Nested response as part of the Retrieve Index Fields API.
 * Contains a list of the frequency of each field type within an index.
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = FieldTypeCounts.Builder.class)
public class FieldTypeCounts {

    /**
     * @returns The count of index field type
     */
    private final Integer indexCount;

    /**
     * @returns The count of reference field type
     */
    private final Integer referenceCount;

    /**
     * @returns The count of date field type
     */
    private final Integer dateCount;

    /**
     * @returns The count of numeric field type
     */
    private final Integer numericCount;

    /**
     * @returns The count of parametric field type
     */
    private final Integer parametricCount;

    /**
     * @returns The count of stored field type
     */
    private final Integer storedCount;

    /**
     * @returns The count of autnrank field type
     */
    private final Integer autnRankCount;

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {

        @JsonProperty("index_count")
        private Integer indexCount;

        @JsonProperty("reference_count")
        private Integer referenceCount;

        @JsonProperty("date_count")
        private Integer dateCount;

        @JsonProperty("numeric_count")
        private Integer numericCount;

        @JsonProperty("parametric_count")
        private Integer parametricCount;

        @JsonProperty("stored_count")
        private Integer storedCount;

        @JsonProperty("autnrank_count")
        private Integer autnRankCount;

        public FieldTypeCounts build() {
            return new FieldTypeCounts(
                    indexCount,
                    referenceCount,
                    dateCount,
                    numericCount,
                    parametricCount,
                    storedCount,
                    autnRankCount
            );
        }

    }

}