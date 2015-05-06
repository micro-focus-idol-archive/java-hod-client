/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textanalysis;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Holds the aggregate sentiment
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = SentimentAnalysisAggregate.Builder.class)
public class SentimentAnalysisAggregate {

    /**
     * @return The aggregate sentiment of the text
     */
    private final Sentiment sentiment;

    /**
     * @return The aggregate sentiment score of the text
     */
    private final double score;

    @JsonPOJOBuilder(withPrefix = "set")
    @Setter
    @Accessors(chain = true)
    public static class Builder {

        private Sentiment sentiment;
        private double score;

        public SentimentAnalysisAggregate build() {
            return new SentimentAnalysisAggregate(sentiment, score);
        }

    }

}
