/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.search;

import com.hp.autonomy.hod.client.util.MultiMap;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Helper class for building up optional parameters for the CreateQueryProfile API and UpdateQueryProfile API. The default value
 * for all parameters is null. Null parameters will not be sent to HP Haven OnDemand
 */
@Setter
@Accessors(chain = true)
public class QueryProfileRequestBuilder {

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
    private List<String> promotionCategories = new ArrayList<>();

    /**
     * @param synonymCategories Sets the value of the synonym_categories parameter
     */
    private List<String> synonymCategories = new ArrayList<>();

    /**
     * @param blacklistCategories Sets the value of the blacklist_categories parameter
     */
    private List<String> blacklistCategories = new ArrayList<>();

    /**
     * Adds categories to the promotion_categories parameter
     * @param category0 The first category to add
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
     * @param category0 The first category to add
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
     * @param category0 The first category to add
     * @param categories Additional categories to add
     * @return this
     */
    public QueryProfileRequestBuilder addBlacklistCategories(final String category0, final String... categories) {
        blacklistCategories.add(category0);
        blacklistCategories.addAll(Arrays.asList(categories));
        return this;
    }

    /**
     * @return A map of query parameters suitable for use with {@link CreateQueryProfileService} and {@link UpdateQueryProfileService}. get is NOT supported on
     * the resulting map
     */
    public Map<String, Object> build() {
        final Map<String, Object> params = new MultiMap<>();
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
