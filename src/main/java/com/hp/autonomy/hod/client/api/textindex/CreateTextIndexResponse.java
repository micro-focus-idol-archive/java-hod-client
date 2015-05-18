/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Holds the response from the CreateTextIndex API
 */
@EqualsAndHashCode
@ToString
@Data
@JsonDeserialize(builder = CreateTextIndexResponse.Builder.class)
public class CreateTextIndexResponse {
    /**
     * @return The name of the index
     */
    private final String index;

    /**
     * @return A message related to the attempt to create the index
     */
    private final String message;

    private CreateTextIndexResponse(final Builder builder) {
        index = builder.index;
        message = builder.message;
    }

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {

        private String index;
        private String message;

        public CreateTextIndexResponse build() {
            return new CreateTextIndexResponse(this);
        }
    }

}
