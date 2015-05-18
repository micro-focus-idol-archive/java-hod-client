/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.analysis.sentiment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Holds the response from the SentimentAnalysis API
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = SentimentAnalysisResponse.Builder.class)
public class SentimentAnalysisResponse {

    /**
     * @return Entities containing positive sentiment
     */
    private final List<SentimentAnalysisEntity> positive;

    /**
     * @return Entities containing negative sentiment
     */
    private final List<SentimentAnalysisEntity> negative;

    /**
     * @return Aggregate sentiment of the text
     */
    private final SentimentAnalysisAggregate aggregate;

    @JsonPOJOBuilder(withPrefix = "set")
    @Setter
    @Accessors(chain = true)
    public static class Builder {

        private List<SentimentAnalysisEntity> positive;
        private List<SentimentAnalysisEntity> negative;
        private SentimentAnalysisAggregate aggregate;

        public SentimentAnalysisResponse build() {
            return new SentimentAnalysisResponse(positive, negative, aggregate);
        }

    }

}
