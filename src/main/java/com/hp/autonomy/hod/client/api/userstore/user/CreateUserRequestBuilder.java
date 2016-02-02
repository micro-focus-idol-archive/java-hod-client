/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.hp.autonomy.hod.client.api.developer.UserAuthMode;
import com.hp.autonomy.hod.client.util.MultiMap;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Helper class for building up optional parameters for the create user API. The default value
 * for all parameters is null. Null parameters will not be sent to HP Haven OnDemand
 */
@Data
@Accessors(chain = true)
public class CreateUserRequestBuilder {

    /**
     * @param userMessage The user_message create user parameter
     */
    private String userMessage;

    /**
     * @param userMode The mode by which users must authenticate
     */
    private UserAuthMode userMode;

    /**
     * @param metadata Metadata keys and values to associate with the user
     */
    private Map<String, ?> metadata;

    Map<String, Object> build() {
        final Map<String, Object> map = new MultiMap<>();
        map.put("user_message", userMessage);
        map.put("user_mode", userMode);

        if (metadata != null) {
            final List<Metadata<?>> metadataList = new LinkedList<>();

            for (final Map.Entry<String, ?> entry : metadata.entrySet()) {
                metadataList.add(new Metadata<>(entry.getKey(), entry.getValue()));
            }

            map.put("metadata", metadataList);
        }

        return map;
    }
}
