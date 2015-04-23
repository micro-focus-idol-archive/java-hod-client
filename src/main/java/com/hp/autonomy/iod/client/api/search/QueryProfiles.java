/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */
package com.hp.autonomy.iod.client.api.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = QueryProfiles.Builder.class)
public class QueryProfiles {

    @JsonProperty("query_profiles")
    private final List<QueryProfileName> queryProfiles;

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {

        @SuppressWarnings("FieldMayBeFinal")
        @JsonProperty("query_profiles")
        private List<QueryProfileName> queryProfiles = Collections.emptyList();

        public QueryProfiles build() {
            return new QueryProfiles(queryProfiles);
        }
    }
}
