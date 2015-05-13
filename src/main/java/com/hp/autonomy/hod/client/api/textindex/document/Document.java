/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.document;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * Sample document for indexing documents into HP Haven OnDemand.
 */
@Data
public class Document {

    /**
     * @return The reference of the document
     */
    private final String reference;

    /**
     * @return The content of the document
     */
    private final String content;

    /**
     * @return The title of the document
     */
    private final String title;

    private final Map<String, Object> fields;

    private Document(final Builder builder) {
        this.reference = builder.reference;
        this.content = builder.content;
        this.title = builder.title;
        this.fields = builder.fields;
    }

    /**
     * @return A map of any additional fields on the document
     */
    @JsonAnyGetter
    public Map<String, Object> getFields() {
        return fields;
    }

    @Setter
    @Accessors(chain = true)
    public static class Builder {

        private String reference;
        private String content;
        private String title;

        private final Map<String, Object> fields = new HashMap<>();

        public Builder addField(final String fieldName, final Object value) {
            fields.put(fieldName, value);
            return this;
        }

        public Document build() {
            return new Document(this);
        }

    }

}
