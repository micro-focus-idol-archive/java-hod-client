/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.formatconversion;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;


/**
 * Holds the response from the ViewDocument API when not using raw HTML
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = ViewDocumentResponse.Builder.class)
public class ViewDocumentResponse {

    /**
     * @return A String containing an HTML representation of the document
     */
    private final String document;

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {

        private String document;

        public Builder setDocument(final String document) {
            this.document = StringUtils.newStringUtf8(Base64.decodeBase64(document));

            return this;
        }

        public ViewDocumentResponse build() {
            return new ViewDocumentResponse(document);
        }
    }

}
