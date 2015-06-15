/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.document;

import com.hp.autonomy.hod.client.converter.DoNotConvert;
import com.hp.autonomy.hod.client.util.MultiMap;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Setter
@Accessors(chain = true)
public class AddToTextIndexRequestBuilder {

    /**
     * @param duplicateMode The duplicate mode of the document
     */
    private DuplicateMode duplicateMode;

    private List<String> referencePrefixes = new ArrayList<>();

    private List<Object> additionalMetadata = new ArrayList<>();

    /**
     * Adds values to the referencePrefixes parameter
     * @param referencePrefix The first index
     * @param referencePrefixes The remaining referencePrefixes
     * @return this
     */
    public AddToTextIndexRequestBuilder addReferencePrefixes(final String referencePrefix, final String... referencePrefixes) {
        this.referencePrefixes.add(referencePrefix);
        this.referencePrefixes.addAll(Arrays.asList(referencePrefixes));

        return this;
    }

    /**
     * Sets the value of the referencePrefixes parameter
     * @param referencePrefixes The referencePrefixes to query
     * @return this
     */
    public AddToTextIndexRequestBuilder setReferencePrefixes(final List<String> referencePrefixes) {
        this.referencePrefixes = referencePrefixes;

        return this;
    }

    /**
     * Sets the value of the metadata parameter
     * @param metadatum The first index
     * @param metadata The remaining metadata
     * @return this
     */
    public AddToTextIndexRequestBuilder addAdditionalMetadata(final Object metadatum, final Object... metadata) {
        this.additionalMetadata.add(metadatum);
        this.additionalMetadata.addAll(Arrays.asList(metadata));

        return this;
    }

    /**
     * Sets the value of the metadata parameter
     * @param metadata The metadata to query
     * @return this
     */
    public AddToTextIndexRequestBuilder setAdditionalMetadata(final List<Object> metadata) {
        this.additionalMetadata = metadata;

        return this;
    }

    /**
     * @return A map of query parameters suitable for use with {@link AddToTextIndexBackend}. get is NOT supported on
     * the resulting map
     */
    public Map<String, Object> build() {
        final Map<String, Object> params = new MultiMap<>();

        params.put("duplicate_mode", duplicateMode);

        for (final String referencePrefix : referencePrefixes) {
            params.put("reference_prefix", referencePrefix);
        }

        for (final Object metadata : additionalMetadata) {
            params.put("additional_metadata", metadata);
        }

        return params;
    }

    /**
     * Enum representing all the possible values of the duplicate_mode parameter
     */
    @DoNotConvert
    public enum DuplicateMode {
        duplicate,
        replace
    }

}
