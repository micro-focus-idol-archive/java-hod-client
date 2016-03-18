/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.fields;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.hp.autonomy.types.requests.idol.actions.tags.TagResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


/**
 * Holds the response from the RetrieveIndexFields API
 */
@Data
@JsonDeserialize(builder = RetrieveIndexFieldsResponse.Builder.class)
public class RetrieveIndexFieldsResponse implements Serializable, TagResponse {

    private static final long serialVersionUID = -4764411121480400430L;
    /**
     * @return An object containing the frequency of each field type
     */
    private final FieldTypeCounts fieldTypeCounts;

    /**
     * @return The total number of indexed fields returned
     */
    private final Integer totalFields;

    /**
     * @return A list of the index type fields if the results
     * were grouped by type
     */
    private final List<String> indexTypeFields;

    /**
     * @return A list of the parametric type fields if the results
     * were grouped by type
     */
    private final List<String> parametricTypeFields;

    /**
     * @return A list of the numeric type fields if the results
     * were grouped by type
     */
    private final List<String> numericTypeFields;

    /**
     * @return A list of the autnrank type fields if the results
     * were grouped by type
     */
    private final List<String> autnRankTypeFields;

    /**
     * @return A list of the reference type fields if the results
     * were grouped by type
     */
    private final List<String> referenceTypeFields;

    /**
     * @return A list of the date type fields if the results
     * were grouped by type
     */
    private final List<String> dateTypeFields;

    /**
     * @return A list of the stored type fields if the results
     * were grouped by type
     */
    private final List<String> storedTypeFields;

    private RetrieveIndexFieldsResponse(final Builder builder) {
        fieldTypeCounts = builder.fieldTypeCounts;
        totalFields = builder.totalFields;
        indexTypeFields = serializableList(builder.indexTypeFields);
        parametricTypeFields = serializableList(builder.parametricTypeFields);
        numericTypeFields = serializableList(builder.numericTypeFields);
        autnRankTypeFields = serializableList(builder.autnRankTypeFields);
        referenceTypeFields = serializableList(builder.referenceTypeFields);
        dateTypeFields = serializableList(builder.dateTypeFields);
        storedTypeFields = serializableList(builder.storedTypeFields);
    }

    private List<String> serializableList(final List<String> input) {
        return input == null ? new LinkedList<String>() : new LinkedList<>(input);
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @Setter
    @Accessors(chain = true)
    public static class Builder {
        @JsonProperty("field_type_counts")
        private FieldTypeCounts fieldTypeCounts;

        @JsonProperty("total_fields")
        private Integer totalFields;

        @JsonProperty("index_type_fields")
        private List<String> indexTypeFields;

        @JsonProperty("parametric_type_fields")
        private List<String> parametricTypeFields;

        @JsonProperty("numeric_type_fields")
        private List<String> numericTypeFields;

        @JsonProperty("autnrank_type_fields")
        private List<String> autnRankTypeFields;

        @JsonProperty("reference_type_fields")
        private List<String> referenceTypeFields;

        @JsonProperty("date_type_fields")
        private List<String> dateTypeFields;

        @JsonProperty("stored_type_fields")
        private List<String> storedTypeFields;

        public RetrieveIndexFieldsResponse build() {
            return new RetrieveIndexFieldsResponse(this);
        }

    }

}

