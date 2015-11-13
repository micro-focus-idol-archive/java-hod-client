/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */
package com.hp.autonomy.hod.client.api.queryprofile;

import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.HodErrorTester;
import com.hp.autonomy.hod.client.api.resource.ListResourcesRequestBuilder;
import com.hp.autonomy.hod.client.api.resource.Resource;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.api.resource.ResourceType;
import com.hp.autonomy.hod.client.api.resource.Resources;
import com.hp.autonomy.hod.client.api.resource.ResourcesService;
import com.hp.autonomy.hod.client.api.resource.ResourcesServiceImpl;
import com.hp.autonomy.hod.client.error.HodErrorCode;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collections;

import static com.hp.autonomy.hod.client.HodErrorTester.testErrorCode;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class RetrieveQueryProfileServiceSuiteChild extends AbstractQueryProfileSuiteChild {
    private static final ListResourcesRequestBuilder LIST_RESOURCES_REQUEST_BUILDER = new ListResourcesRequestBuilder()
        .setTypes(Collections.singleton(ResourceType.QUERY_PROFILE));

    private QueryProfileService service;
    private ResourcesService resourcesService;

    public RetrieveQueryProfileServiceSuiteChild(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        resourcesService = new ResourcesServiceImpl(getConfig());
        service = new QueryProfileServiceImpl(getConfig());
    }

    @Test
    public void createAndList() throws HodErrorException {
        final ResourceIdentifier profile = trackedCreateProfile().getProfile();
        final Resources output = resourcesService.list(getTokenProxy(), LIST_RESOURCES_REQUEST_BUILDER);

        boolean foundProfile = false;

        for (final Resource resource : output.getResources()) {
            if (profile.getName().equals(resource.getResource())) {
                foundProfile = true;
                break;
            }
        }

        assertTrue("List resources did not return created profile", foundProfile);
    }

    @Test
    public void createAndRetrieveDefault() throws HodErrorException {
        final ResourceIdentifier profileIdentifier = trackedCreateProfile().getProfile();
        final QueryProfile profile = service.retrieveQueryProfile(getTokenProxy(), profileIdentifier);

        assertThat(profile.getName(), is(profileIdentifier.getName()));
        assertThat(profile.getDescription(), is(nullValue()));

        assertThat(profile.getBlacklistsEnabled(), is(false));
        assertThat(profile.getPromotionsEnabled(), is(false));
        assertThat(profile.getSynonymsEnabled(), is(false));

        assertThat(profile.getBlacklistCategories(), is(empty()));
        assertThat(profile.getSynonymCategories(), is(empty()));
        assertThat(profile.getPromotionCategories(), is(empty()));

        assertThat(profile.getPromotionsIdentified(), is(true));
    }

    @Test
    public void createAndRetrieveCustom() throws HodErrorException {
        final String description = "My freshly created query profile";

        final QueryProfileRequestBuilder builder = new QueryProfileRequestBuilder()
            .setDescription(description)
            .setBlacklistsEnabled(true)
            .addBlacklistCategories("my_blacklists")
            .setSynonymsEnabled(false)
            .setPromotionsIdentified(true)
            .setPromotionsEnabled(true)
            .addPromotionCategories("promotions1", "promotions2");

        final ResourceIdentifier profileIdentifier = trackedCreateProfile(builder).getProfile();
        final QueryProfile profile = service.retrieveQueryProfile(getTokenProxy(), profileIdentifier);

        assertThat(profile.getName(), is(profileIdentifier.getName()));
        assertThat(profile.getDescription(), is(description));

        assertThat(profile.getBlacklistsEnabled(), is(true));
        assertThat(profile.getPromotionsEnabled(), is(true));
        assertThat(profile.getSynonymsEnabled(), is(false));

        assertThat(profile.getBlacklistCategories(), contains("my_blacklists"));
        assertThat(profile.getSynonymCategories(), empty());
        assertThat(profile.getPromotionCategories(), containsInAnyOrder("promotions1", "promotions2"));

        assertThat(profile.getPromotionsIdentified(), is(true));
    }

    @Test
    public void deleteAndList() throws HodErrorException {
        final ResourceIdentifier profileIdentifier = trackedCreateProfile().getProfile();
        trackedDeleteProfile(profileIdentifier);

        final Resources resources = resourcesService.list(getTokenProxy(), LIST_RESOURCES_REQUEST_BUILDER);

        for (final Resource resource : resources.getResources()) {
            if (profileIdentifier.getName().equals(resource.getResource())) {
                fail("Deleted profile returned from list resources: " + profileIdentifier);
            }
        }
    }

    @Test
    public void deleteAndRetrieve() throws HodErrorException {
        final ResourceIdentifier profileIdentifier = trackedCreateProfile().getProfile();
        trackedDeleteProfile(profileIdentifier);

        testErrorCode(HodErrorCode.QUERY_PROFILE_NAME_INVALID, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.retrieveQueryProfile(getTokenProxy(), profileIdentifier);
            }
        });
    }
}
