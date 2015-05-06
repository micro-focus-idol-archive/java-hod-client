/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindexing;

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
 * Holds the indexes returned from HP Haven OnDemand
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = Indexes.Builder.class)
public class Indexes {
    /**
     * @return A List of private indexes
     */
    private final List<Index> indexes;

    /**
     * @return A List of public indexes. flavor and dateCreated will be null on these indexes.
     */
    private final List<Index> publicIndexes;

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {

        @SuppressWarnings("FieldMayBeFinal")
        @JsonProperty("index")
        private List<Index> indexes = Collections.emptyList();

        @SuppressWarnings("FieldMayBeFinal")
        @JsonProperty("public_index")
        private List<Index> publicIndexes = Collections.emptyList();

        public Indexes build() {
            return new Indexes(indexes, publicIndexes);
        }
    }
}
