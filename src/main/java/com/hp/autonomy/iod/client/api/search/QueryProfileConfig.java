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

/**
 * A query profile config to send to IDOL OnDemand for use with other actions
 */
@Data
public class QueryProfileConfig {
    /**
     * @return The name of the query manipulation index to use
     */
    @JsonProperty("querymanipulationindex")
    private final String queryManipulationIndex;

    /**
     * @return The promotions settings for this query profile
     */
    private final QueryProfilePromotions promotions;


    private QueryProfileConfig(final Builder builder) {
        queryManipulationIndex = builder.queryManipulationIndex;
        promotions = builder.promotions;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @Setter
    @Accessors(chain = true)
    public static class Builder {

        @JsonProperty("querymanipulationindex")
        private String queryManipulationIndex;

        private QueryProfilePromotions promotions;

        public QueryProfileConfig build() {
            return new QueryProfileConfig(this);
        }

    }
}
