/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.textindexing;

import com.hp.autonomy.iod.client.util.MultiMap;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * Request builder for the DeleteTextIndex API
 */
@Setter
@Accessors(chain = true)
public class DeleteTextIndexRequestBuilder {

    /**
     * @param confirm Confirmation hash key returned after the first request
     */
    private String confirm;

    /**
     * @return A map of query parameters suitable for use with {@link DeleteTextIndexService}. get is NOT supported on
     * the resulting map
     */
    public Map<String, Object> build() {
        final Map<String, Object> map = new MultiMap<>();
        map.put("confirm", confirm);

        return map;
    }

}
