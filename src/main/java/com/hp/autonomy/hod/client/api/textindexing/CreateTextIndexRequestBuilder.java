/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindexing;

import com.hp.autonomy.hod.client.util.MultiMap;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * Request builder for the CreateTextIndex API
 */
@Setter
@Accessors(chain = true)
public class CreateTextIndexRequestBuilder {

    /**
     * @param description Value for the description parameter
     */
    private String description;

    /**
     * @return A map of query parameters suitable for use with {@link CreateTextIndexService}. get is NOT supported on
     * the resulting map
     */
    public Map<String, Object> build() {
        final Map<String, Object> map = new MultiMap<>();
        map.put("description", description);

        return map;
    }

}
