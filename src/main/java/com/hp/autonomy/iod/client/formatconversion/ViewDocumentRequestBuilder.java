/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.formatconversion;

import com.hp.autonomy.iod.client.util.MultiMap;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Helper class for building up optional parameters for the Query Text Index API. The default value for all parameters
 * is null. Null parameters will not be sent to IDOL OnDemand
 */
@Setter
@Accessors(chain = true)
public class ViewDocumentRequestBuilder {

    private List<String> highlightExpressions =  new ArrayList<>();
    private List<String> startTags =  new ArrayList<>();
    private List<String> endTags =  new ArrayList<>();

    /**
     * Sets the value for the raw_html parameter. You should not need to set this unless using
     * {@link com.hp.autonomy.iod.client.formatconversion.ViewDocumentService#viewFileAsHtmlString}
     * @param rawHtml Value of the raw_html parameter
     */
    private Boolean rawHtml;

    /**
     * @param highlightExpressions Sets the value of the highlight_expressions parameter
     * @return this
     */
    public ViewDocumentRequestBuilder setHighlightExpressions(final List<String> highlightExpressions) {
        this.highlightExpressions = highlightExpressions;
        return this;
    }

    /**
     * Adds values to the highlight_expression parameter
     * @param highlightExpression The first highlight expression
     * @param highlightExpressions The remaining highlight expressions
     * @return this
     */
    public ViewDocumentRequestBuilder addHighlightExpressions(final String highlightExpression, final String... highlightExpressions) {
        this.highlightExpressions.add(highlightExpression);
        this.highlightExpressions.addAll(Arrays.asList(highlightExpressions));
        return this;
    }

    /**
     * @param startTags Sets the value of the start_tag parameter
     * @return this
     */
    public ViewDocumentRequestBuilder setStartTags(final List<String> startTags) {
        this.startTags = startTags;
        return this;
    }

    /**
     * Adds values to the start_tag parameter
     * @param startTag The first start tag
     * @param startTags The remaining start tags
     * @return this
     */
    public ViewDocumentRequestBuilder addStartTags(final String startTag, final String... startTags) {
        this.startTags.add(startTag);
        this.startTags.addAll(Arrays.asList(startTags));
        return this;
    }

    /**
     * @param endTags Sets the value of the end_tag parameter
     * @return this
     */
    public ViewDocumentRequestBuilder setEndTags(final List<String> endTags) {
        this.endTags = endTags;
        return this;
    }

    /**
     * Adds values to the end_tag parameter
     * @param endTag The first end tag
     * @param endTags The remaining end tags
     * @return this
     */
    public ViewDocumentRequestBuilder addEndTags(final String endTag, final String... endTags) {
        this.endTags.add(endTag);
        this.endTags.addAll(Arrays.asList(endTags));
        return this;
    }

    /**
     * @return A map of query parameters suitable for use with {@link ViewDocumentService}. get is NOT supported on
     * the resulting map
     */
    public Map<String, Object> build() {
        final Map<String, Object> params = new MultiMap<>();

        params.put("raw_html", rawHtml);

        for (final String highlightExpression : highlightExpressions) {
            params.put("highlight_expression", highlightExpression);
        }

        for (final String startTag : startTags) {
            params.put("start_tag", startTag);
        }

        for (final String endTag : endTags) {
            params.put("end_tag", endTag);
        }

        return params;
    }

}
