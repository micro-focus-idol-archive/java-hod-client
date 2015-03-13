/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.hp.autonomy.iod.client.converter.DoNotConvert;
import com.hp.autonomy.iod.client.util.MultiMap;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Helper class for building up optional parameters for the Query Text Index API. The default value for all parameters
 * is null. Null parameters will not be sent to IDOL OnDemand
 */
@Setter
@Accessors(chain = true)
public class QueryTextIndexRequestBuilder {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("HH:mm:ss dd/MM/yyyy G");

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

    private List<String> indexes = new ArrayList<>();

    /**
     * Sets the value of the indexes parameter
     * @param index0 The first index
     * @param indexes The remaining indexes
     * @return this
     */
    public QueryTextIndexRequestBuilder setIndexes(final String index0, final String... indexes) {
        this.indexes.add(index0);
        this.indexes.addAll(Arrays.asList(indexes));

        return this;
    }

    /**
     * Sets the value of the indexes parameter
     * @param indexes The indexes to query
     * @return this
     */
    public QueryTextIndexRequestBuilder setIndexes(final List<String> indexes) {
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

        // prefer the DateTime over the numeric versions
        if(minDate != null) {
            map.put("min_date", DATE_FORMAT.print(minDate));
        }
        else if(minDateDays != null) {
            map.put("min_date", minDateDays);
        }
        else if(maxDateSeconds != null) {
            map.put("min_date", minDateSeconds + "s");
        }

        if(maxDate != null) {
            map.put("max_date", DATE_FORMAT.print(maxDate));
        }
        else if(maxDateDays != null) {
            map.put("max_date", maxDateDays);
        }
        else if(maxDateSeconds != null) {
            map.put("max_date", maxDateSeconds + "s");
        }

        for(final String index : indexes) {
            map.put("indexes", index);
        }

        return map;
    }

    /**
     * Enum type representing the possible options for the print parameter
     */
    @DoNotConvert
    public enum Print {
        all,
        all_sections,
        date,
        fields,
        none,
        no_results,
        parametric,
        reference
    }

    /**
     * Enum type representing the possible options for the highlight parameter
     */
    @DoNotConvert
    public enum Highlight {
        off,
        terms,
        sentences
    }

    /**
     * Enum type representing the possible options for the sort parameter
     */
    @DoNotConvert
    public enum Sort {
        autn_rank,
        date,
        off,
        relevance,
        reverse_date,
        reverse_relevance
    }

    /**
     * Enum type representing the possible options for the summary parameter
     */
    @DoNotConvert
    public enum Summary {
        context,
        concept,
        quick,
        off
    }

}
