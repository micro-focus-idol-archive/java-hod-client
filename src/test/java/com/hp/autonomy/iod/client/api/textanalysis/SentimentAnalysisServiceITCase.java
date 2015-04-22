/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.textanalysis;

import com.hp.autonomy.iod.client.AbstractIodClientIntegrationTest;
import com.hp.autonomy.iod.client.Endpoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class SentimentAnalysisServiceITCase extends AbstractIodClientIntegrationTest {

    private SentimentAnalysisService sentimentAnalysisService;

    public SentimentAnalysisServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Before
    public void setUp() {
        super.setUp();

        sentimentAnalysisService = getRestAdapter().create(SentimentAnalysisService.class);
    }

    @Test
    public void testSentimentAnalysisForText() {
        final String text = "The service at the restaurant was good. " +
                "The food at the restaurant was poor. " +
                "In conclusion, it was an average restaurant";

        final SentimentAnalysisResponse response = sentimentAnalysisService.analyzeSentimentForText(endpoint.getApiKey(), text, SentimentAnalysisLanguage.eng);

        assertThat(response.getPositive(), hasSize(1));
        assertThat(response.getNegative(), hasSize(1));

        final SentimentAnalysisAggregate aggregate = response.getAggregate();

        assertThat(aggregate.getSentiment(), is(Sentiment.neutral));

        final SentimentAnalysisEntity positive = response.getPositive().get(0);

        assertThat(positive.getSentiment(), is("good"));
        assertThat(positive.getTopic(), is("The service at the restaurant"));

        final SentimentAnalysisEntity negative = response.getNegative().get(0);

        assertThat(negative.getSentiment(), is("poor"));
        assertThat(negative.getTopic(), is("The food at the restaurant"));
    }

}
