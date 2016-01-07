/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Holds the response from the DeleteTextIndex API
 */
@EqualsAndHashCode
@ToString
@Data
@JsonDeserialize(builder = DeleteTextIndexResponse.Builder.class)
public class DeleteTextIndexResponse {
    /**
     * @return True if the index was deleted. False if a confirm code was returned
     */
    private final boolean deleted;

    /**
     * @return The name of the index. This will be null if deleted is false
     */
    private final String index;

    /**
     * @return The confirm code to use to delete the index. This will be null if deleted is true
     */
    private final String confirm;

    private DeleteTextIndexResponse(final Builder builder) {
        deleted = builder.deleted;
        index = builder.index;
        confirm = builder.confirm;
    }

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {

        private boolean deleted;
        private String index;
        private String confirm;

        public DeleteTextIndexResponse build() {
            return new DeleteTextIndexResponse(this);
        }
    }

}
