/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

@Data
@JsonDeserialize(builder = QueryProfile.Builder.class)
public class QueryProfile {

    /** The name of the Query Profile */
    @JsonProperty("query_profile")
    private final String name;

    /**
     * @return The name of the query manipulation index the profile applies to
     */
    private final String queryManipulationIndex;

    /**
     * @return True if promotions are enabled; false otherwise
     */
    private final Boolean promotionsEnabled;

    /**
     * @return The promotion categories that are included in this profile
     */
    private final List<String> promotionCategories;

    /**
     * @return True if promotions are identified; false otherwise
     */
    private final Boolean promotionsIdentified;

    /**
     * @return True if synonyms are enabled; false otherwise
     */
    private final Boolean synonymsEnabled;

    /**
     * @return The synonym categories that are included in this profile
     */
    private final List<String> synonymCategories;

    /**
     * @return True if blacklists are enabled; false otherwise
     */
    private final Boolean blacklistsEnabled;

    /**
     * @return The blacklist categories that are included in this profile
     */
    private final List<String> blacklistCategories;

    private QueryProfile(final Builder builder) {
        name = builder.name;
        queryManipulationIndex = builder.queryManipulationIndex;
        promotionsEnabled = builder.promotionsEnabled;
        promotionCategories = builder.promotionCategories;
        promotionsIdentified = builder.promotionsIdentified;
        synonymsEnabled = builder.synonymsEnabled;
        synonymCategories = builder.synonymCategories;
        blacklistsEnabled = builder.blacklistsEnabled;
        blacklistCategories = builder.blacklistCategories;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @Setter
    @Accessors(chain = true)
    public static class Builder {

        /** The name of the Query Profile */
        @JsonProperty("query_profile")
        private String name;

        @JsonProperty("query_manipulation_index")
        private String queryManipulationIndex;

        @JsonProperty("promotions_enabled")
        private Boolean promotionsEnabled = false;

        @JsonProperty("promotion_categories")
        private List<String> promotionCategories = Collections.emptyList();

        @JsonProperty("promotions_identified")
        private Boolean promotionsIdentified = false;

        @JsonProperty("synonyms_enabled")
        private Boolean synonymsEnabled = false;

        @JsonProperty("synonym_categories")
        private List<String> synonymCategories = Collections.emptyList();

        @JsonProperty("blacklists_enabled")
        private Boolean blacklistsEnabled = false;

        @JsonProperty("blacklist_categories")
        private List<String> blacklistCategories = Collections.emptyList();

        // TODO: this is a stop gap until HOD catches up with the documentation
        private Builder setConfig(final QueryProfile config) {
            setQueryManipulationIndex(config.getQueryManipulationIndex());
            setPromotionsEnabled(config.getPromotionsEnabled());
            setPromotionCategories(config.getPromotionCategories());
            setPromotionsIdentified(config.getPromotionsIdentified());
            setSynonymsEnabled(config.getSynonymsEnabled());
            setSynonymCategories(config.getSynonymCategories());
            setBlacklistsEnabled(config.getBlacklistsEnabled());
            setBlacklistCategories(config.getBlacklistCategories());

            return this;
        }

        public QueryProfile build() {
            return new QueryProfile(this);
        }
    }
}
