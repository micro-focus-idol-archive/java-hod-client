/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.fields;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.hp.autonomy.types.requests.idol.actions.tags.params.FieldTypeParam;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.ListUtils;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;


/**
 * Holds the response from the RetrieveIndexFields API
 */
@Data
@JsonDeserialize(builder = RetrieveIndexFieldsResponse.Builder.class)
public class RetrieveIndexFieldsResponse implements Serializable {

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
     * @return Map of fields by field type
     */
    private final Map<FieldTypeParam, List<String>> fields = new EnumMap<>(FieldTypeParam.class);

    private RetrieveIndexFieldsResponse(final Builder builder) {
        fieldTypeCounts = builder.fieldTypeCounts;
        totalFields = builder.totalFields;

        fields.put(FieldTypeParam.AutnRank, ListUtils.emptyIfNull(builder.autnRankTypeFields));
        fields.put(FieldTypeParam.NumericDate, ListUtils.emptyIfNull(builder.dateTypeFields));
        fields.put(FieldTypeParam.Index, ListUtils.emptyIfNull(builder.indexTypeFields));
        fields.put(FieldTypeParam.Numeric, ListUtils.emptyIfNull(builder.numericTypeFields));
        fields.put(FieldTypeParam.Parametric, ListUtils.emptyIfNull(builder.parametricTypeFields));
        fields.put(FieldTypeParam.Reference, ListUtils.emptyIfNull(builder.referenceTypeFields));
        fields.put(FieldTypeParam.All, ListUtils.emptyIfNull(builder.storedTypeFields));
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

