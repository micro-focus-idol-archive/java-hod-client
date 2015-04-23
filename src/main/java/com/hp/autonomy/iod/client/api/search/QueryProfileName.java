/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */
package com.hp.autonomy.iod.client.api.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * Represents the name of a query profile.  Part of the response from ListQueryProfile.
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = QueryProfileName.Builder.class)
public class QueryProfileName {

    /**
     * The name of a query profile
     */
    @JsonProperty("name")
    private final String name;

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {

        @JsonProperty("name")
        private String name;

        public QueryProfileName build() {
            return new QueryProfileName(name);
        }
    }
}
