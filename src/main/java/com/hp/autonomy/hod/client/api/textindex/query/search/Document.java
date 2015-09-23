/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A document returned from an HP Haven OnDemand index
 */
@Data
@JsonDeserialize(builder = Document.Builder.class)
public class Document implements Serializable {

    private static final long serialVersionUID = 7352690975010398089L;

    /**
     * @return The reference of the document
     * @serial The reference of the document
     */
    private final String reference;

    /**
     * @return The weight (relevance) of the document
     * @serial The weight (relevance) of the document
     */
    private final double weight;

    /**
     * @return The stemmed terms from the query which matched the document
     * @serial The stemmed terms from the query which matched the document
     */
    private final Set<String> links;

    /**
     * @return The index in which the document resides
     * @serial The index in which the document resides
     */
    private final String index;

    /**
     * @return The title of the document
     * @serial The title of the document
     */
    private final String title;

    /**
     * @return A summary of the document. If summaries were not requested, this will be the empty string
     * @serial A summary of the document. If summaries were not requested, this will be the empty string
     */
    private final String summary;

    /**
     * @return The content of the document. If content were not requested, this will be the empty string
     * @serial The content of the document. If content were not requested, this will be the empty string
     */
    private final String content;

    /**
     * @return A map containing any fields on the document which are not known ahead of time
     */
    private transient Map<String, Serializable> fields;

    /**
     * @return The section number of the result document
     * @serial The section number of the result document
     */
    private final Integer section;

    private Document(final Builder builder) {
        reference = builder.reference;
        weight = builder.weight;
        links = builder.links;
        index = builder.index;
        title = builder.title;
        summary = builder.summary;
        fields = builder.fields;
        content = builder.content;
        section = builder.section;
    }

    /**
     * @param objectOutputStream The output stream
     * @serialData Writes out the standard fields, then the number of non-standard fields {@code int}, followed by
     * the non-standard field names alternated with their values
     */
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        objectOutputStream.writeInt(fields.size());

        for(final Map.Entry<String, Serializable> entry : fields.entrySet()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }

    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        fields = new HashMap<>();

        final int fieldCount = objectInputStream.readInt();

        for(int i = 0; i < fieldCount; i++) {
            final String fieldName = (String) objectInputStream.readObject();
            final Serializable value = (Serializable) objectInputStream.readObject();
            fields.put(fieldName, value);
        }
    }

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {

        private String reference;
        private double weight;
        private Set<String> links;
        private String index;
        private String title;
        private Integer section;

        @SuppressWarnings("FieldMayBeFinal")
        private String content = "";

        private final Map<String, Serializable> fields = new HashMap<>();

        @SuppressWarnings("FieldMayBeFinal")
        private String summary = "";

        public Builder setLinks(final Set<String> links) {
            if (links != null) {
                this.links = new HashSet<>(links);
            }

            return this;
        }

        public Builder addField(final String key, final Serializable value) {
            fields.put(key, value);
            return this;
        }

        // Jackson can't convert to interfaces, so we need this helper method
        @JsonAnySetter
        Builder _addField(final String key, final Object value) {
            // Assume Jackson will give us a Serializable type
            this.addField(key, (Serializable) value);
            return this;
        }

        public Document build() {
            return new Document(this);
        }

    }

}
