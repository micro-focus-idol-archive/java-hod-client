/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.fields;

import com.hp.autonomy.hod.client.util.MultiMap;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.Map;

/**
 * Helper class for building up optional parameters for the Retrieve Index Fields API. The default value for all parameters
 * is null. Null parameters will not be sent to HP Haven OnDemand
 */
@Data
@Accessors(chain = true)
public class RetrieveIndexFieldsRequestBuilder {
    /**
     * @param fieldType Describe the field type of field names to retrieve.
     */
    private Collection<FieldType> fieldTypes;

    /**
     * @param maxValues The number of field names to display. Displays maximum 1000 tags by default.
     */
    private Integer maxValues;

    /**
     * @return A map of query parameters suitable for use with {@link RetrieveIndexFieldsBackend}. get is NOT supported on
     * the resulting map
     */
    Map<String, Object> build() {
        final Map<String, Object> map = new MultiMap<>();
        for (final FieldType fieldType : fieldTypes) {
            map.put("field_types", fieldType);
        }
        map.put("max_values", maxValues);

        return map;
    }

}
