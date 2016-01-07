/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
class Metadata<T> {
    private final String key;
    private final T value;

    Metadata(@JsonProperty("key") final String key, @JsonProperty("value") final T value) {
        this.key = key;
        this.value = value;
    }
}
