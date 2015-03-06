/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.search;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Data;
import lombok.Setter;

/**
 * A document returned from an IDOL OnDemand index
 */
@Data
@JsonDeserialize(builder = Document.Builder.class)
public class Document {

    /**
     * @returns The reference of the document
     */
    private final String reference;

    /**
     * @returns The weight (relevance) of the document
     */
    private final double weight;

    /**
     * @returns The stemmed terms from the query which matched the document
     */
    private final Set<String> links;

    /**
     * @returns The index in which the document resides
     */
    private final String index;

    /**
     * @returns The title of the document
     */
    private final String title;

    /**
     * @returns A summary of the document. If summaries were not requested, this will be the empty string
     */
    private final String summary;

    /**
     * @returns The content of the document. If content were not requested, this will be the empty string
     */
    private final String content;

    /**
     * @returns A map containing any fields on the document which are not known ahead of time
     */
    private final Map<String, Object> fields;

    private Document(final Builder builder) {
        reference = builder.reference;
        weight = builder.weight;
        links = builder.links;
        index = builder.index;
        title = builder.title;
        summary = builder.summary;
        fields = builder.fields;
        content = builder.content;
    }

    @Setter
    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {

        private String reference;
        private double weight;
        private Set<String> links;
        private String index;
        private String title;

        @SuppressWarnings("FieldMayBeFinal")
        private String content = "";

        private final Map<String, Object> fields = new HashMap<>();

        @SuppressWarnings("FieldMayBeFinal")
        private String summary = "";

        @JsonAnySetter
        public Builder addField(final String key, final Object value) {
            fields.put(key, value);
            return this;
        }

        public Document build() {
            return new Document(this);
        }

    }

}
