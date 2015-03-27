/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@EqualsAndHashCode
@ToString
@JsonDeserialize(builder = FieldNames.Builder.class)
public class FieldNames implements Iterable<FieldNames.ParametricValue> {

    private final Map<String, Map<String, Integer>> parametricValuesMap;

    private FieldNames(final Builder builder) {
        this.parametricValuesMap = builder.parametricValuesMap;
    }

    @Override
    public Iterator<ParametricValue> iterator() {
        return new ParametricValueIterator();
    }

    /**
     * Get the count for a parametric value given the field name it resides in
     * @param fieldName The name of the field
     * @param fieldValue The value of the field
     * @return count The number of documents that have the given value in the given field
     */
    public Integer getFieldValueCount(final String fieldName, final String fieldValue) {
        final Map<String, Integer> values = parametricValuesMap.get(fieldName);

        if(values != null) {
            return values.get(fieldValue);
        }

        return null;
    }

    /**
     * @return The field names for which we have values
     */
    @JsonIgnore
    public Set<String> getFieldNames() {
        return parametricValuesMap.keySet();
    }

    /**
     * @param fieldName The name of the field
     * @return A set of all the values for the given field name
     */
    public Set<String> getValuesForFieldName(final String fieldName) {
        final Map<String, Integer> map = parametricValuesMap.get(fieldName);

        if (map != null) {
            return map.keySet();
        }

        return Collections.emptySet();
    }

    /**
     * Get an array of values and counts for a given fieldName
     * @param fieldName The name of the field
     * @return an array of value and count types
     */
    public List<ValueAndCount> getValuesAndCountsForFieldName(final String fieldName) {
        final Map<String, Integer> map = parametricValuesMap.get(fieldName);

        if (map != null) {
            final List<ValueAndCount> counts = new ArrayList<>();

            for (final Map.Entry<String, Integer> entry : map.entrySet()) {
                counts.add(new ValueAndCount(entry.getKey(), entry.getValue()));
            }

            return counts;
        }

        return Collections.emptyList();
    }

    @JsonAnyGetter
    private Map<String, List<ValueAndCount>> getJson() {
        final Map<String, List<ValueAndCount>> map = new LinkedHashMap<>();

        for(final String fieldName : parametricValuesMap.keySet()) {
            map.put(fieldName, getValuesAndCountsForFieldName(fieldName));
        }

        return map;
    }

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private final Map<String, Map<String, Integer>> parametricValuesMap = new LinkedHashMap<>();

        @JsonAnySetter
        public Builder addParametricValue(final String key, final Map<String, Integer> value) {
            parametricValuesMap.put(key, value);
            return this;
        }

        public FieldNames build() {
            return new FieldNames(this);
        }

    }

    @Data
    public static class ParametricValue {
        private final String fieldName;
        private final String value;
        private final int count;
    }

    @Data
    public static class ValueAndCount {
        private final String value;
        private final int count;
    }

    private class ParametricValueIterator implements Iterator<ParametricValue> {

        private final Iterator<ParametricValue> inner;

        private ParametricValueIterator() {
            final List<ParametricValue> values = new ArrayList<>();

            for(final Map.Entry<String, Map<String, Integer>> fieldAndValues : parametricValuesMap.entrySet()) {
                final String fieldName = fieldAndValues.getKey();

                for(final Map.Entry<String, Integer> valueAndCount : fieldAndValues.getValue().entrySet()) {
                    final String fieldValue = valueAndCount.getKey();
                    final int count = valueAndCount.getValue();
                    values.add(new ParametricValue(fieldName, fieldValue, count));
                }
            }

            inner = values.iterator();
        }

        @Override
        public boolean hasNext() {
            return inner.hasNext();
        }

        @Override
        public ParametricValue next() {
            return inner.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
