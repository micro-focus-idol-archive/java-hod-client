/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.content;

import com.hp.autonomy.hod.client.api.textindex.query.search.Print;
import com.hp.autonomy.hod.client.api.textindex.query.search.Summary;
import com.hp.autonomy.hod.client.util.MultiMap;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Helper class for building up optional parameters for the Get Content API. The default value for all parameters
 * is null. Null parameters will not be sent to HP Haven OnDemand
 */
@Setter
@Accessors(chain = true)
public class GetContentRequestBuilder {

    /**
     * @param highlightExpression Value for the highlight_expression parameter
     */
    private String highlightExpression;

    /**
     * @param startTag Value for the start_tag parameter
     */
    private String startTag;

    /**
     * @param endTag Value for the end_tag parameter
     */
    private String endTag;

    /**
     * @param printFields Value for the print_fields parameter. This list will be joined with commas before being sent
     * to the server
     */
    private List<String> printFields;

    /**
     * @param print Value for the print parameter
     */
    private Print print;

    /**
     * @param summary Value for the summary parameter
     */
    private Summary summary;

    /**
     * @return A map of query parameters suitable for use with {@link GetContentBackend}. get is NOT supported on
     * the resulting map
     */
    public Map<String, Object> build() {
        final Map<String, Object> map = new MultiMap<>();
        map.put("highlight_expression", highlightExpression);
        map.put("start_tag", startTag);
        map.put("end_tag", endTag);
        map.put("print", print);
        map.put("print_fields", StringUtils.join(printFields, ','));
        map.put("summary", summary);

        return map;
    }

}
