/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.analysis.sentiment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * The sentiment information for a portion of the text
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = SentimentAnalysisEntity.Builder.class)
public class SentimentAnalysisEntity {

    /**
     * @return The portion of the text which was sentimental. May be null
     */
    private final String sentiment;

    /**
     * @return The portion of the text about which the sentiment was expressed. May be null
     */
    private final String topic;

    /**
     * @return The sentiment score of the text
     */
    private final double score;

    /**
     * @return The original text that was analyzed
     */
    private final String originalText;

    /**
     * @return The original length of the text that was analyzed
     */
    private final int originalLength;

    /**
     * @return The text after character normalization
     */
    private final String normalizedText;

    /**
     * @return The length of the text after character normalization
     */
    private final int normalizedLength;


    @JsonPOJOBuilder(withPrefix = "set")
    @Setter
    @Accessors(chain = true)
    public static class Builder {

        private String sentiment;
        private String topic;
        private double score;

        @JsonProperty("original_text")
        private String originalText;

        @JsonProperty("original_length")
        private int originalLength;

        @JsonProperty("normalized_text")
        private String normalizedText;

        @JsonProperty("normalized_length")
        private int normalizedLength;

        public SentimentAnalysisEntity build() {
            return new SentimentAnalysisEntity(sentiment, topic, score, originalText, originalLength, normalizedText, normalizedLength);
        }

    }

}
