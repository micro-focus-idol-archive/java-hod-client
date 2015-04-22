/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.AbstractIodClientIntegrationTest;
import com.hp.autonomy.iod.client.Endpoint;
import com.hp.autonomy.iod.client.error.IodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;

@RunWith(Parameterized.class)
public class CreateDeleteQueryProfileServiceSuiteChild extends AbstractIodClientIntegrationTest {

    private CreateQueryProfileService createQueryProfileService;
    private DeleteQueryProfileService deleteQueryProfileService;

    @Before
    public void setUp() {
        super.setUp();

        createQueryProfileService = getRestAdapter().create(CreateQueryProfileService.class);
        deleteQueryProfileService = getRestAdapter().create(DeleteQueryProfileService.class);
    }

    public CreateDeleteQueryProfileServiceSuiteChild(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testCreateDeleteQueryProfile() throws IodErrorException, InterruptedException {
        final String profileName = "iod_java_client_query_profile_test";

        final ArrayList<String> categories = new ArrayList<>();
        categories.add("Promotions");


        final QueryProfilePromotions promotions = new QueryProfilePromotions.Builder()
                .setEnabled(true)
                .setEveryPage(false)
                .setIdentified(false)
                .setCategories(categories)
                .build();

        final QueryProfileConfig queryProfile = new QueryProfileConfig.Builder()
                .setQueryManipulationIndex(getQueryManipulationIndex())
                .setPromotions(promotions)
                .build();

        final QueryProfileStatusResponse createResponse = createQueryProfileService.createQueryProfile(endpoint.getApiKey(), profileName, queryProfile);
        final QueryProfileStatusResponse deleteResponse = deleteQueryProfileService.deleteQueryProfile(endpoint.getApiKey(), profileName);

        assertThat(createResponse.getMessage(), is(notNullValue()));
        assertThat(createResponse.getQueryProfile(), is(profileName));
        assertThat(deleteResponse.getMessage(), is(notNullValue()));
        assertThat(deleteResponse.getQueryProfile(), is(profileName));
    }

}