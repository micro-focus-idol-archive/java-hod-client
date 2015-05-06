/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindexing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * An index returned from HP Haven OnDemand
 */
@Data
@JsonDeserialize(builder = Index.Builder.class)
public class Index {
    /**
     * @return The name of the index
     */
    private final String index;

    /**
     * @return The type of the index
     */
    private final IndexType type;

    /**
     * @return The flavor of the index. May be null
     */
    private final IndexFlavor flavor;

    /**
     * @return The description of the index
     */
    private final String description;

    /**
     * @return The date that index or connector was created. May be null
     */
    private final String dateCreated;

    private Index(final Builder builder) {
        index = builder.index;
        type = builder.type;
        flavor = builder.flavor;
        description = builder.description;
        dateCreated = builder.dateCreated;
    }

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private String index;
        private IndexType type;
        private IndexFlavor flavor;
        private String description;

        @JsonProperty("date_created")
        private String dateCreated;

        public Index build() {
            return new Index(this);
        }

    }
}
