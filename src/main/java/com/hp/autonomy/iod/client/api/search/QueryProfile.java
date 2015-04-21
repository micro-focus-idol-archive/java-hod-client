/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
public class QueryProfile {

    /** The name of the Query Profile */
    @JsonProperty("query_profile")
    private final String name;

    /** The Query Profile config */
    private final QueryProfileConfig config;

    private QueryProfile(final Builder builder) {
        name = builder.name;
        config = builder.config;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @Setter
    @Accessors(chain = true)
    public static class Builder {

        /** The name of the Query Profile */
        @JsonProperty("query_profile")
        private String name;

        /** The Query Profile config */
        private QueryProfileConfig config;

        public QueryProfile build() {
            return new QueryProfile(this);
        }
    }
}
