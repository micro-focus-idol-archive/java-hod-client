/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.hp.autonomy.hod.client.util.MultiMap;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

/**
 * Helper class for building up optional parameters for the Find Related Concepts API. The default value for all parameters
 * is null. Null parameters will not be sent to HP Haven OnDemand
 */
@Setter
@Accessors(chain = true)
public class FindRelatedConceptsRequestBuilder {

    /**
     * @param fieldText Value for the field_text parameter
     */
    private String fieldText;

    /**
     * @param maxDate A DateTime to use as the value for the max_date parameter. This parameter takes precedence over
     * maxDateDays and maxDateSeconds
     */
    private DateTime maxDate;

    /**
     * @param minDate A DateTime to use as the value for the min_date parameter. This parameter takes precedence over
     * minDateDays and minDateSeconds
     */
    private DateTime minDate;

    /**
     * @param maxDateDays A number of days to use as the value for the max_date parameter. This parameter takes
     * precedence over maxDateSeconds
     */
    private Long maxDateDays;

    /**
     * @param maxDateSeconds A number of seconds to use as the value for the max_date parameter.
     */
    private Long maxDateSeconds;

    /**
     * @param minDateDays A number of days to use as the value for the min_date parameter. This parameter takes
     * precedence over maxDateSeconds
     */
    private Long minDateDays;

    /**
     * @param minDateSeconds A number of seconds to use as the value for the min_date parameter.
     */
    private Long minDateSeconds;

    /**
     * @param minScore Value for the min_score parameter
     */
    private Integer minScore;

    /**
     * @param printFields Value for the print_fields parameter. This list will be joined with commas before being sent
     * to the server
     */
    private List<String> printFields;

    /**
     * @param indexes Value for the indexes parameter. This list will be sent as separate parameters,
     * e.g. indexes=wiki_eng&indexes=news_eng&...
     */
    private List<String> indexes;

    /**
     * @param maxResults Value for the max_results parameter.
     */
    private Integer maxResults;

    /**
     * @param sampleSize Value for the sample_size parameter.
     */
    private Integer sampleSize;

    /**
     * @return A map of query parameters suitable for use with {@link FindRelatedConceptsService}. get is NOT supported on
     * the resulting map
     */
    public Map<String, Object> build() {
        final Map<String, Object> map = new MultiMap<>();
        map.put("field_text", fieldText);
        map.put("min_score", minScore);
        map.put("print_fields", StringUtils.join(printFields, ','));
        map.put("max_results", maxResults);
        map.put("sample_size", sampleSize);

        // prefer the DateTime over the numeric versions
        map.putAll(TimeSelector.max(maxDate, maxDateDays, maxDateSeconds));
        map.putAll(TimeSelector.min(minDate, minDateDays, minDateSeconds));

        for (final String index : indexes) {
            map.put("indexes", index);
        }

        return map;
    }

}
