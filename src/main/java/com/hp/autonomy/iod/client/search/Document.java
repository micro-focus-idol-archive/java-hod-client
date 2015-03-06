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

@Data
@JsonDeserialize(builder = Document.Builder.class)
public class Document {

    private final String reference;
    private final double weight;
    private final Set<String> links;
    private final String index;
    private final String title;
    private final String summary;
    private final String content;

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
