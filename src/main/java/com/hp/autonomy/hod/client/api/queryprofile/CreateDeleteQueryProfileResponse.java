/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * Holds the response from the CreateQueryProfile API
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = CreateDeleteQueryProfileResponse.Builder.class)
public class CreateDeleteQueryProfileResponse {

    /**
     * @return The message returned by the api
     */
    private final String message;

    /**
     * @return The name of the created query profile
     */
    private final String queryProfileName;

    @JsonPOJOBuilder(withPrefix = "set")
    @Setter
    @Accessors(chain = true)
    public static class Builder {

        private String message;

        @JsonProperty("query_profile")
        private String queryProfileName;

        public CreateDeleteQueryProfileResponse build() {
            return new CreateDeleteQueryProfileResponse(message, queryProfileName);
        }
    }
}
