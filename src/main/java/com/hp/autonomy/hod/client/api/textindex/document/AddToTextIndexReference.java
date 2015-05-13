/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.hp.autonomy.hod.client.error.HodError;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A reference returned from the AddToTextIndexApi. This class will hold either:
 * <p>A reference and an id</p>
 * <p>or an HodError if something went wrong</p>
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = AddToTextIndexReference.Builder.class)
public class AddToTextIndexReference {

    /**
     * @return The reference of the document
     */
    private final String reference;

    /**
     * @return The id of the document
     */
    private final Integer id;

    /**
     * @return A HodError indicating something went wrong
     */
    private final HodError error;

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {

        private String reference;
        private Integer id;
        private HodError error;

        public AddToTextIndexReference build() {
            return new AddToTextIndexReference(reference, id, error);
        }

    }

}
