/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.parametric;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.hp.autonomy.types.requests.idol.actions.tags.QueryTagCountInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

@SuppressWarnings("WeakerAccess")
@EqualsAndHashCode
@ToString
@JsonDeserialize(builder = FieldNames.Builder.class)
public class FieldNames implements Iterable<FieldNames.ParametricValue>, Serializable {

    private static final long serialVersionUID = 2336751244628886019L;
    private static final Pattern CSV_SEPARATOR_PATTERN = Pattern.compile(",\\s*");
    private static final long MILLI_MULTIPLIER = 1000L;

    private transient Map<String, Map<String, Integer>> parametricValuesMap;

    private FieldNames(final Builder builder) {
        parametricValuesMap = builder.parametricValuesMap;
    }

    @Override
    public Iterator<ParametricValue> iterator() {
        return new ParametricValueIterator();
    }

    /**
     * Get the count for a parametric value given the field name it resides in
     *
     * @param fieldName  The name of the field
     * @param fieldValue The value of the field
     * @return count The number of documents that have the given value in the given field
     */
    public Integer getFieldValueCount(final String fieldName, final String fieldValue) {
        final Map<String, Integer> values = parametricValuesMap.get(fieldName);

        if (values != null) {
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
     *
     * @param fieldName The name of the field
     * @return an array of value and count types
     */
    public List<QueryTagCountInfo> getValuesAndCountsForFieldName(final String fieldName) {
        final Map<String, Integer> map = parametricValuesMap.get(fieldName);

        if (map != null) {
            final List<QueryTagCountInfo> counts = new ArrayList<>();

            for (final Map.Entry<String, Integer> entry : map.entrySet()) {
                counts.add(new QueryTagCountInfo(entry.getKey(), entry.getValue()));
            }

            return counts;
        }

        return Collections.emptyList();
    }

    /**
     * Get an array of values and counts for a given fieldName
     *
     * @param fieldName The name of the field
     * @return an array of value and count types
     */
    public List<QueryTagCountInfo> getValuesAndCountsForNumericField(final String fieldName) {
        final Map<String, Integer> map = parametricValuesMap.get(fieldName);

        if (map != null) {
            final Map<Double, Integer> countInfo = new TreeMap<>();
            for (final Map.Entry<String, Integer> entry : map.entrySet()) {
                final String[] csv = CSV_SEPARATOR_PATTERN.split(entry.getKey());
                final int count = entry.getValue();
                for (final String value : csv) {
                    final double numericValue = Double.parseDouble(value);
                    if (countInfo.containsKey(numericValue)) {
                        countInfo.put(numericValue, countInfo.get(numericValue) + count);
                    } else {
                        countInfo.put(numericValue, count);
                    }
                }
            }

            final List<QueryTagCountInfo> counts = new ArrayList<>();
            for (final Map.Entry<Double, Integer> entry : countInfo.entrySet()) {
                counts.add(new QueryTagCountInfo(entry.getKey().toString(), entry.getValue()));
            }

            return counts;
        }

        return Collections.emptyList();
    }

    @SuppressWarnings("unused")
    @JsonAnyGetter
    private Map<String, List<QueryTagCountInfo>> getJson() {
        final Map<String, List<QueryTagCountInfo>> map = new LinkedHashMap<>();

        for (final String fieldName : parametricValuesMap.keySet()) {
            map.put(fieldName, getValuesAndCountsForFieldName(fieldName));
        }

        return map;
    }

    /**
     * @param objectOutputStream The output stream
     * @serialData Writes out the number of fields {@code int}, then for each field, writes out the field name
     * {@code String}, the number of the values {@code int}, then writes out each value {@code String} and the count
     * {@code int}
     */
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        objectOutputStream.writeInt(parametricValuesMap.size());

        for (final Map.Entry<String, Map<String, Integer>> entry : parametricValuesMap.entrySet()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeInt(entry.getValue().size());

            for (final Map.Entry<String, Integer> valueCount : entry.getValue().entrySet()) {
                objectOutputStream.writeObject(valueCount.getKey());
                objectOutputStream.writeInt(valueCount.getValue());
            }
        }
    }

    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();

        final int fieldCount = objectInputStream.readInt();

        parametricValuesMap = new LinkedHashMap<>();

        for (int i = 0; i < fieldCount; i++) {
            final String fieldName = (String) objectInputStream.readObject();
            final int countsCount = objectInputStream.readInt();

            final Map<String, Integer> countsMap = new LinkedHashMap<>();

            for (int j = 0; j < countsCount; j++) {
                final String value = (String) objectInputStream.readObject();
                final int count = objectInputStream.readInt();

                countsMap.put(value, count);
            }

            parametricValuesMap.put(fieldName, countsMap);
        }
    }

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
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
    public static class ParametricValue implements Serializable {
        private static final long serialVersionUID = 656458301600512829L;
        private final String fieldName;
        private final String value;
        private final int count;
    }

    private class ParametricValueIterator implements Iterator<ParametricValue> {

        private final Iterator<ParametricValue> inner;

        private ParametricValueIterator() {
            final Collection<ParametricValue> values = new ArrayList<>();

            for (final Map.Entry<String, Map<String, Integer>> fieldAndValues : parametricValuesMap.entrySet()) {
                final String fieldName = fieldAndValues.getKey();

                for (final Map.Entry<String, Integer> valueAndCount : fieldAndValues.getValue().entrySet()) {
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
