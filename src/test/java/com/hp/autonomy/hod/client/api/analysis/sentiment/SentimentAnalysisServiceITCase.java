/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.analysis.sentiment;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class SentimentAnalysisServiceITCase extends AbstractHodClientIntegrationTest {

    private SentimentAnalysisBackend sentimentAnalysisBackend;

    public SentimentAnalysisServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();

        sentimentAnalysisBackend = getRestAdapter().create(SentimentAnalysisBackend.class);
    }

    @Test
    public void testSentimentAnalysisForText() throws HodErrorException {
        final String text = "The service at the restaurant was good. " +
                "The food at the restaurant was poor. " +
                "In conclusion, it was an average restaurant";

        final SentimentAnalysisResponse response = sentimentAnalysisBackend.analyzeSentimentForText(getToken(), text, SentimentAnalysisLanguage.eng);

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
