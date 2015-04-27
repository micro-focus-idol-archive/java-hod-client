/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */
package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.AbstractQueryProfileIntegrationTest;
import com.hp.autonomy.iod.client.Endpoint;
import com.hp.autonomy.iod.client.error.IodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class UpdateQueryProfilesServiceSuiteChild extends AbstractQueryProfileIntegrationTest {

    public UpdateQueryProfilesServiceSuiteChild(Endpoint endpoint) {
        super(endpoint);
    }

    // Not under test
    private RetrieveQueryProfileService retrieveQueryProfileService;

    // Service under test
    private UpdateQueryProfileService updateQueryProfileService;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        updateQueryProfileService = getRestAdapter().create(UpdateQueryProfileService.class);
        retrieveQueryProfileService = getRestAdapter().create(RetrieveQueryProfileService.class);
    }

    @Test
    public void updateQueryProfileTest() throws IodErrorException {
        // Create a query profile, then load it back and verify that it's what we created
        final QueryProfile originalQP = createQueryProfile("test001");
        final String qpName = originalQP.getName();
        final QueryProfile qpFromServer = retrieveQueryProfileService.retrieveQueryProfile(endpoint.getApiKey(), qpName);
        assertThat(qpFromServer.getConfig(), is(originalQP.getConfig()));

        // Create a new config, then change the query profile on the server.  Fetch it back and verify that it's changed
        // These values are deliberately different from the ones created in the superclass
        final List<String> categories = new ArrayList<>();
        categories.add("NotPromotions");

        final QueryProfilePromotions promotions = new QueryProfilePromotions.Builder()
                .setEnabled(false)
                .setEveryPage(true)
                .setIdentified(true)
                .setCategories(categories)
                .build();

        final QueryProfileConfig newConfig = new QueryProfileConfig.Builder()
                .setQueryManipulationIndex(getQueryManipulationIndex())
                .setPromotions(promotions)
                .build();

        updateQueryProfileService.updateQueryProfile(endpoint.getApiKey(), qpName, newConfig);

        final QueryProfile newQPFromServer = retrieveQueryProfileService.retrieveQueryProfile(endpoint.getApiKey(), qpName);

        assertThat(newQPFromServer.getConfig(), is(newConfig));
    }
}
