/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * The promotion settings for a query profile
 */
@Data
@JsonDeserialize(builder = QueryProfile.Builder.class)
public class QueryProfilePromotions {
    /**
     * @return Whether promotions are enabled
     */
    private final Boolean enabled;

    /**
     * @return Whether the results should form part of every page
     */
    private final Boolean everyPage;

    /**
     * @return Whether the results should be identified as promotions
     */
    private final Boolean identified;

    /**
     * @return A list of categories to apply to this promotion
     */
    private final List<String> categories;


    private QueryProfilePromotions(final Builder builder) {
        enabled = builder.enabled;
        everyPage = builder.everyPage;
        identified = builder.identified;
        categories = builder.categories;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @Setter
    @Accessors(chain = true)
    public static class Builder {
        private Boolean enabled;

        @JsonProperty("every_page")
        private Boolean everyPage;

        private Boolean identified;

        private List<String> categories;

        public QueryProfilePromotions build() {
            return new QueryProfilePromotions(this);
        }

    }

    /**
     * @return Whether this profile's promotions should appear on every page
     */
    @JsonProperty("every_page")
    public Boolean getEveryPage() {
        return everyPage;
    }
}
