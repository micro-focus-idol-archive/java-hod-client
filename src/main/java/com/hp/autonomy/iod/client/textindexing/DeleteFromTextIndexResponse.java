/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.textindexing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Holds the response from the DeleteFromTextIndex API
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = DeleteFromTextIndexResponse.Builder.class)
public class DeleteFromTextIndexResponse {

    /**
     * @return The index containing the deleted documents
     */
    private final String index;

    /**
     * @return The number of deleted documents
     */
    private final int documentsDeleted;

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private String index;

        @JsonProperty("documents_deleted")
        private int documentsDeleted;

        public DeleteFromTextIndexResponse build() {
            return new DeleteFromTextIndexResponse(index, documentsDeleted);
        }
    }

}
