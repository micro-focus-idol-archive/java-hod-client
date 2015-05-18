/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * An entity returned from an HP Haven OnDemand index
 */
@Data
@JsonDeserialize(builder = Entity.Builder.class)
public class Entity {
    /**
     * @return The name of the concept found
     */
    private final String text;

    /**
     * @return The number of documents with this concept
     */
    private final Integer docsWithPhrase;

    /**
     * @return The total number of occurrences of this element in the results set
     */
    private final Integer occurrences;

    /**
     * @return The number of documents of the results set in which all terms of this element appear.
     */
    private final Integer docsWithAllTerms;

    /**
     * @return The cluster into which the phrase has been grouped
     */
    private final Integer cluster;

    private Entity(final Builder builder) {
        text = builder.text;
        docsWithPhrase = builder.docsWithPhrase;
        occurrences = builder.occurrences;
        docsWithAllTerms = builder.docsWithAllTerms;
        cluster = builder.cluster;
    }

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private String text;
        private Integer docsWithPhrase;
        private Integer occurrences;
        private Integer docsWithAllTerms;
        private Integer cluster;

        public Entity build() {
            return new Entity(this);
        }

    }
}
