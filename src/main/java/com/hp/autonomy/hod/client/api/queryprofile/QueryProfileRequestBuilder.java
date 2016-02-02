/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import com.hp.autonomy.hod.client.util.MultiMap;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Helper class for building up optional parameters for the CreateQueryProfile API and UpdateQueryProfile API. Null parameters
 * or empty collections will not be sent to HP Haven OnDemand.
 */
@Data
@Accessors(chain = true)
public class QueryProfileRequestBuilder {
    /**
     * @param description Sets the value of the description parameter
     */
    private String description;

    /**
     * @param promotionsEnabled Sets the value of the promotions_enabled parameter
     */
    private Boolean promotionsEnabled;

    /**
     * @param promotionsIdentified Sets the value of the promotions_identified parameter
     */
    private Boolean promotionsIdentified;

    /**
     * @param synonymsEnabled Sets the value of the synonyms_enabled parameter
     */
    private Boolean synonymsEnabled;

    /**
     * @param blacklistsEnabled Sets the value of the blacklists_enabled parameter
     */
    private Boolean blacklistsEnabled;

    /**
     * @param promotionCategories Sets the value of the promotion_categories parameter
     */
    private Collection<String> promotionCategories = new ArrayList<>();

    /**
     * @param synonymCategories Sets the value of the synonym_categories parameter
     */
    private Collection<String> synonymCategories = new ArrayList<>();

    /**
     * @param blacklistCategories Sets the value of the blacklist_categories parameter
     */
    private Collection<String> blacklistCategories = new ArrayList<>();

    /**
     * Adds categories to the promotion_categories parameter
     *
     * @param category0  The first category to add
     * @param categories Additional categories to add
     * @return this
     */
    public QueryProfileRequestBuilder addPromotionCategories(final String category0, final String... categories) {
        promotionCategories.add(category0);
        promotionCategories.addAll(Arrays.asList(categories));
        return this;
    }

    /**
     * Adds categories to the synonym_categories parameter
     *
     * @param category0  The first category to add
     * @param categories Additional categories to add
     * @return this
     */
    public QueryProfileRequestBuilder addSynonymCategories(final String category0, final String... categories) {
        synonymCategories.add(category0);
        synonymCategories.addAll(Arrays.asList(categories));
        return this;
    }

    /**
     * Adds categories to the blacklist_categories parameter
     *
     * @param category0  The first category to add
     * @param categories Additional categories to add
     * @return this
     */
    public QueryProfileRequestBuilder addBlacklistCategories(final String category0, final String... categories) {
        blacklistCategories.add(category0);
        blacklistCategories.addAll(Arrays.asList(categories));
        return this;
    }

    MultiMap<String, Object> build() {
        final MultiMap<String, Object> params = new MultiMap<>();
        params.put("description", description);
        params.put("promotions_enabled", promotionsEnabled);
        params.put("promotions_identified", promotionsIdentified);
        params.put("synonyms_enabled", synonymsEnabled);
        params.put("blacklists_enabled", blacklistsEnabled);

        for (final String category : promotionCategories) {
            params.put("promotion_categories", category);
        }

        for (final String category : synonymCategories) {
            params.put("synonym_categories", category);
        }

        for (final String category : blacklistCategories) {
            params.put("blacklist_categories", category);
        }

        return params;
    }

}
