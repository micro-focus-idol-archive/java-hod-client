/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.util.MultiMap;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Helper class for building up optional parameters for the QueryTextIndex API and FindSimilar API. The default value
 * for all parameters is null. Null parameters will not be sent to IDOL OnDemand
 */
@Setter
@Accessors(chain = true)
public class QueryRequestBuilder {

    /**
     * @param maxDate A DateTime to use as the value for the max_date parameter. This parameter takes precedence over
     *                maxDateDays and maxDateSeconds
     */
    private DateTime maxDate;

    /**
     * @param minDate A DateTime to use as the value for the min_date parameter. This parameter takes precedence over
     *                minDateDays and minDateSeconds
     */
    private DateTime minDate;

    /**
     * @param maxDateDays A number of days to use as the value for the max_date parameter. This parameter takes
     *                    precedence over maxDateSeconds
     */
    private Long maxDateDays;

    /**
     * @param maxDateSeconds A number of seconds to use as the value for the max_date parameter.
     */
    private Long maxDateSeconds;

    /**
     * @param minDateDays A number of days to use as the value for the min_date parameter. This parameter takes
     *                    precedence over maxDateSeconds
     */
    private Long minDateDays;

    /**
     * @param minDateSeconds A number of seconds to use as the value for the min_date parameter.
     */
    private Long minDateSeconds;

    /**
     * @param endTag Value for the end_tag parameter
     */
    private String endTag;

    /**
     * @param fieldText Value for the field_text parameter
     */
    private String fieldText;

    /**
     * @param highlight Value for the highlight parameter
     */
    private Highlight highlight;

    /**
     * @param absoluteMaxResults Value for the absolute_max_results parameter
     */
    private Integer absoluteMaxResults;

    /**
     * @param maxPageResults Value for the max_page_results parameter
     */
    private Integer maxPageResults;

    /**
     * @param minScore Value for the min_score parameter
     */
    private Integer minScore;

    /**
     * @param print Value for the print parameter
     */
    private Print print;

    /**
     * @param printFields Value for the print_fields parameter. This list will be joined with commas before being sent
     *                    to the server
     */
    private List<String> printFields;

    /**
     * @param start Value for the start parameter
     */
    private Integer start;

    /**
     * @param sort Value for the sort parameter
     */
    private Sort sort;

    /**
     * @param startTag Value for the start_tag parameter
     */
    private String startTag;

    /**
     * @param summary Value for the summary parameter
     */
    private Summary summary;

    /**
     * @param totalResults Value for the total_results parameter
     */
    private Boolean totalResults;

    /**
     * @param queryProfile Value for the query_profile parameter
     */
    private String queryProfile;

    private List<String> indexes = new ArrayList<>();

    /**
     * Adds indexes to the indexes parameter
     * @param index0 The first index
     * @param indexes The remaining indexes
     * @return this
     */
    public QueryRequestBuilder addIndexes(final String index0, final String... indexes) {
        this.indexes.add(index0);
        this.indexes.addAll(Arrays.asList(indexes));

        return this;
    }

    /**
     * Sets the value of the indexes parameter
     * @param indexes The indexes to query
     * @return this
     */
    public QueryRequestBuilder setIndexes(final List<String> indexes) {
        this.indexes = indexes;

        return this;
    }

    /**
     * @return A map of query parameters suitable for use with {@link QueryTextIndexService}. get is NOT supported on
     * the resulting map
     */
    public Map<String, Object> build() {
        final Map<String, Object> map = new MultiMap<>();
        map.put("end_tag", endTag);
        map.put("field_text", fieldText);
        map.put("highlight", highlight);
        map.put("absolute_max_results", absoluteMaxResults);
        map.put("max_page_results", maxPageResults);
        map.put("min_score", minScore);
        map.put("print", print);
        map.put("print_fields", StringUtils.join(printFields, ','));
        map.put("sort", sort);
        map.put("start", start);
        map.put("start_tag", startTag);
        map.put("summary", summary);
        map.put("total_results", totalResults);
        map.put("query_profile", queryProfile);

        // prefer the DateTime over the numeric versions
        map.putAll(TimeSelector.max(maxDate, maxDateDays, maxDateSeconds));
        map.putAll(TimeSelector.min(minDate, minDateDays, minDateSeconds));

        for(final String index : indexes) {
            map.put("indexes", index);
        }

        return map;
    }

}
