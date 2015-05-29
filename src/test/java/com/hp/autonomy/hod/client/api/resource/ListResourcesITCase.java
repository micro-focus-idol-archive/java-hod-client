/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.EnumSet;
import java.util.Map;

import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ListResourcesITCase extends AbstractHodClientIntegrationTest {
    private ResourcesService resourcesService;

    public ListResourcesITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        resourcesService = getRestAdapter().create(ResourcesService.class);
    }

    @Test
    public void listsResources() {
        final Map<String, Object> parameters = new ListResourcesRequestBuilder()
                .setTypes(EnumSet.of(ResourceType.content))
                .build();

        final Resources resources = resourcesService.list(getToken(), parameters);

        assertThat(resources.getPublicResources(), is(not(empty())));

        for (final Resource publicResource : resources.getPublicResources()) {
            assertThat(publicResource.getType(), is(ResourceType.content));
            assertThat(publicResource.getResource(), is(not(nullValue())));
        }

        boolean found = false;

        for (final Resource privateResource : resources.getResources()) {
            if (privateResource.getResource().equals(getIndex())) {
                found = true;
                break;
            }
        }

        assertTrue("Test index not found in list resources output", found);
    }
}