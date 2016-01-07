/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

@Data
class GetMetadataResponse {
    private final List<Metadata<JsonNode>> metadata;

    GetMetadataResponse(@JsonProperty("metadata") final List<Metadata<JsonNode>> metadata) {
        this.metadata = metadata;
    }
}
