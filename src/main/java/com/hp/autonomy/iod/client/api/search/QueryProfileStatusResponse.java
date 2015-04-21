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

/**
 * The response object for the Create, Delete and Update Query Profile APIs.
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = QueryProfileStatusResponse.Builder.class)
public class QueryProfileStatusResponse {

    /**
     * @return The message returned by the api
     */
    private final String message;

    /**
     * @return The name of the created query profile
     */
    private final String queryProfile;

    @JsonPOJOBuilder(withPrefix = "set")
    @Setter
    @Accessors(chain = true)
    public static class Builder {

        private String message;

        @JsonProperty("query_profile")
        private String queryProfile;

        public QueryProfileStatusResponse build() {
            return new QueryProfileStatusResponse(message, queryProfile);
        }

    }

}
