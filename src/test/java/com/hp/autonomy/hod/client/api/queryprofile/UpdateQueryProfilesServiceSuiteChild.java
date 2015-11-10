/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */
package com.hp.autonomy.hod.client.api.queryprofile;

import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.HodErrorTester;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.error.HodErrorCode;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static com.hp.autonomy.hod.client.HodErrorTester.testErrorCode;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class UpdateQueryProfilesServiceSuiteChild extends AbstractQueryProfileSuiteChild {
    private QueryProfileService service;

    public UpdateQueryProfilesServiceSuiteChild(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();

        service = new QueryProfileServiceImpl(getConfig());
    }

    @Test
    public void updateOptionsAndRetrieve() throws HodErrorException {
        final QueryProfileRequestBuilder createParameters = new QueryProfileRequestBuilder()
            .setSynonymsEnabled(true);

        final ResourceIdentifier profileIdentifier = trackedCreateProfile(createParameters).getProfile();

        final String description = "My freshly updated query profile";

        final QueryProfileRequestBuilder updateParameters = new QueryProfileRequestBuilder()
            .setDescription(description)
            .setBlacklistsEnabled(true)
            .setSynonymsEnabled(false)
            .addPromotionCategories("new_category");

        service.updateQueryProfile(getTokenProxy(), profileIdentifier, null, updateParameters);

        final QueryProfile profile = service.retrieveQueryProfile(getTokenProxy(), profileIdentifier);

        assertThat(profile.getName(), is(profileIdentifier.getName()));
        assertThat(profile.getDescription(), is(description));
        assertThat(profile.getQueryManipulationIndex(), is(QUERY_MANIPULATION_INDEX_NAME));
        assertThat(profile.getPromotionsIdentified(), is(true));

        assertThat(profile.getBlacklistsEnabled(), is(true));
        assertThat(profile.getSynonymsEnabled(), is(false));
        assertThat(profile.getPromotionsEnabled(), is(false));

        assertThat(profile.getPromotionCategories(), contains("new_category"));
        assertThat(profile.getBlacklistCategories(), empty());
        assertThat(profile.getSynonymCategories(), empty());
    }

    @Test
    public void updateNonExistentFails() {
        final QueryProfileRequestBuilder parameters = new QueryProfileRequestBuilder()
            .setPromotionsIdentified(true);

        testErrorCode(HodErrorCode.QUERY_PROFILE_NAME_INVALID, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.updateQueryProfile(getTokenProxy(), new ResourceIdentifier(getEndpoint().getDomainName(), uniqueName()), null, parameters);
            }
        });
    }

    @Test
    public void updateWithNonExistentIndexFails() throws HodErrorException {
        final ResourceIdentifier profileIdentifier = trackedCreateProfile().getProfile();

        testErrorCode(HodErrorCode.INDEX_NAME_INVALID, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.updateQueryProfile(getTokenProxy(), profileIdentifier, uniqueName(), null);
            }
        });
    }
}
