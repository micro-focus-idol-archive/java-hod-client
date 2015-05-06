/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindexing;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Holds the response from the AddToTextIndex API
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = AddToTextIndexResponse.Builder.class)
public class AddToTextIndexResponse {

    /**
     * @return The index the documents were added to
     */
    private final String index;

    /**
     * @return A list containing the references of the indexed documents
     */
    private final List<AddToTextIndexReference> references;

    @JsonPOJOBuilder(withPrefix = "set")
    @Setter
    @Accessors(chain = true)
    public static class Builder {

        private String index;
        private List<AddToTextIndexReference> references;

        public AddToTextIndexResponse build() {
            return new AddToTextIndexResponse(index, references);
        }

    }

}
