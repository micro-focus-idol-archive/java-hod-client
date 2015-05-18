/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class CreateDeleteQueryProfileServiceSuiteChild extends AbstractHodClientIntegrationTest {

    private CreateQueryProfileService createQueryProfileService;
    private DeleteQueryProfileService deleteQueryProfileService;

    @Override
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
    public void testCreateDeleteQueryProfile() throws HodErrorException, InterruptedException {
        final String profileName = "iod_java_client_query_profile_test";

        final QueryProfileRequestBuilder builder = new QueryProfileRequestBuilder()
            .setPromotionsEnabled(true)
            .setPromotionsIdentified(false)
            .addPromotionCategories("Promotions");

        final QueryProfileStatusResponse createResponse = createQueryProfileService.createQueryProfile(getToken(), profileName, getQueryManipulationIndex(), builder.build());
        final QueryProfileStatusResponse deleteResponse = deleteQueryProfileService.deleteQueryProfile(getToken(), profileName);

        assertThat(createResponse.getMessage(), is(notNullValue()));
        assertThat(createResponse.getQueryProfile(), is(profileName));

        assertThat(deleteResponse.getMessage(), is(notNullValue()));
        assertThat(deleteResponse.getQueryProfile(), is(profileName));
    }

}
