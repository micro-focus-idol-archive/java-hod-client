/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.search;

import com.hp.autonomy.hod.client.util.MultiMap;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * Helper class for building up optional parameters for the Retrieve Index Fields API. The default value for all parameters
 * is null. Null parameters will not be sent to HP Haven OnDemand
 */
@Setter
@Accessors(chain = true)
public class RetrieveIndexFieldsRequestBuilder {

    /**
     * @param index The text index to use to perform the retrieval. Uses the wiki_eng index by default.
     */
    private String index;

    /**
     * @param groupFieldsByType Whether the fields should be shown grouped by field types.
     */
    private Boolean groupFieldsByType;

    /**
     * @param fieldType Describe the field type of field names to retrieve.
     */
    private FieldType fieldType;

    /**
     * @param maxValues The number of field names to display. Displays maximum 1000 tags by default.
     */
    private Integer maxValues;


    /**
     * @return A map of query parameters suitable for use with {@link RetrieveIndexFieldsService}. get is NOT supported on
     * the resulting map
     */
    public Map<String, Object> build() {
        final Map<String, Object> map = new MultiMap<>();
        map.put("index", index);
        map.put("group_fields_by_type", groupFieldsByType);
        map.put("fieldtype", fieldType);
        map.put("max_values", maxValues);

        return map;
    }

}
