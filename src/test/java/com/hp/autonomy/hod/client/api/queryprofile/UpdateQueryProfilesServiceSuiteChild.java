/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */
package com.hp.autonomy.hod.client.api.queryprofile;

import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.AbstractQueryProfileIntegrationTest;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class UpdateQueryProfilesServiceSuiteChild extends AbstractQueryProfileIntegrationTest {

    // Service under test
    private QueryProfileService queryProfileService;

    public UpdateQueryProfilesServiceSuiteChild(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();

        queryProfileService = new QueryProfileServiceImpl(getConfig());
    }

    @Test
    public void updateQueryProfileTest() throws HodErrorException {
        // Create a query profile, then load it back and verify that it's what we created
        final QueryProfile originalQP = createQueryProfile("test001");
        final String qpName = originalQP.getName();
        final QueryProfile qpFromServer = queryProfileService.retrieveQueryProfile(getTokenProxy(), qpName);
        assertThat(qpFromServer, is(originalQP));

        // Create a new config, then change the query profile on the server.  Fetch it back and verify that it's changed
        // These values are deliberately different from the ones created in the superclass
        final QueryProfileRequestBuilder requestBuilder = new QueryProfileRequestBuilder()
            .setPromotionsEnabled(false)
            .setPromotionsIdentified(true)
            .addPromotionCategories("NotPromotions");

        queryProfileService.updateQueryProfile(getTokenProxy(), qpName, getQueryManipulationIndex(), requestBuilder);

        final QueryProfile newQPFromServer = queryProfileService.retrieveQueryProfile(getTokenProxy(), qpName);

        assertThat(newQPFromServer, is(new QueryProfile.Builder()
            .setName(qpName)
            .setQueryManipulationIndex(getQueryManipulationIndex())
            .setPromotionsEnabled(false)
            .setPromotionsIdentified(true)
            .setPromotionCategories(Collections.singletonList("notpromotions"))
            .build())
        );
    }
}
