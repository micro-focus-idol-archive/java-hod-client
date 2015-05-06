/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.search;

import com.hp.autonomy.hod.client.util.MultiMap;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Helper class for building up optional parameters for the Get Parametric Values API. The default value for all parameters
 * is null. Null parameters will not be sent to HP Haven OnDemand
 */
@Setter
@Accessors(chain = true)
public class GetParametricValuesRequestBuilder {

    private List<String> indexes = new ArrayList<>();

    private Number minScore;

    /**
     * @param text The value of the text parameter
     */
    private String text;

    /**
     * @param fieldText The value of the field_text parameter
     */
    private String fieldText;

    /**
     * @param maxValues The value of the max_values parameter
     */
    private Integer maxValues;

    /**
     * @param documentCount The value of the document_count parameter
     */
    private Boolean documentCount;

    /**
     * @param Sort The value of the sort parameter
     */
    private ParametricSort sort;

    /**
     * @param queryProfile The value of the query_profile parameter
     */
    private String queryProfile;

    /**
     * Adds indexes to the indexes parameter
     * @param index0 The first index
     * @param indexes The remaining indexes
     * @return this
     */
    public GetParametricValuesRequestBuilder addIndexes(final String index0, final String... indexes) {
        this.indexes.add(index0);
        this.indexes.addAll(Arrays.asList(indexes));

        return this;
    }

    /**
     * Sets indexes to the indexes parameter
     * @param indexes The indexes
     * @return this
     */
    public GetParametricValuesRequestBuilder setIndexes(final List<String> indexes) {
        this.indexes = indexes;

        return this;
    }

    /**
     * @param minScore Sets the value of minScore
     */
    public GetParametricValuesRequestBuilder setMinScore(final Double minScore) {
        this.minScore = minScore;

        return this;
    }

    /**
     * @param minScore Sets the value of minScore
     */
    public GetParametricValuesRequestBuilder setMinScore(final Integer minScore) {
        this.minScore = minScore;

        return this;
    }

    /**
     * @return A map of query parameters suitable for use with {@link GetParametricValuesService}. get is NOT supported on
     * the resulting map
     */
    public Map<String, Object> build() {
        final Map<String, Object> map = new MultiMap<>();
        map.put("text", text);
        map.put("field_text", fieldText);
        map.put("max_values", maxValues);
        map.put("min_score", minScore);
        map.put("document_count", documentCount);
        map.put("sort", sort);
        map.put("query_profile", queryProfile);

        for (final String index : indexes) {
            map.put("indexes", index);
        }

        return map;
    }


}
