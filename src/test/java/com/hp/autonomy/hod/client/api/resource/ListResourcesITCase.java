/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.EnumSet;
import java.util.List;

import static com.hp.autonomy.hod.client.api.resource.ListResourcesITCase.ResourceWithNameMatcher.hasResourceWithName;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

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
        resourcesService = new ResourcesServiceImpl(getConfig());
    }

    @Test
    public void listsResources() throws HodErrorException {
        final ListResourcesRequestBuilder parameters = new ListResourcesRequestBuilder()
                .setTypes(ResourceType.of(ResourceType.CONTENT));

        final Resources resources = resourcesService.list(getTokenProxy(), parameters);

        assertThat(resources.getPublicResources(), is(not(empty())));

        for (final Resource publicResource : resources.getPublicResources()) {
            assertThat(publicResource.getType(), is(ResourceType.CONTENT));
            assertThat(publicResource.getResource(), is(not(nullValue())));
        }

        assertThat(resources.getResources(), hasResourceWithName(PRIVATE_INDEX.getName()));
    }

    @Test
    public void listsAllResources() throws HodErrorException {
        resourcesService.list(getTokenProxy(), new ListResourcesRequestBuilder());
    }

    static class ResourceWithNameMatcher extends BaseMatcher<List<Resource>> {

        private final String name;

        private ResourceWithNameMatcher(final String name) {
            this.name = name;
        }

        static ResourceWithNameMatcher hasResourceWithName(final String name) {
            return new ResourceWithNameMatcher(name);
        }

        @Override
        public boolean matches(final Object item) {
            if (!(item instanceof List)) {
                return false;
            }

            @SuppressWarnings("unchecked")
            final List<Resource> resources = (List<Resource>) item;

            boolean found = false;

            for (final Resource resource : resources) {
                if (resource.getResource().equals(name)) {
                    found = true;
                    break;
                }
            }

            return found;
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText("A list containing a resource with name: ").appendValue(name);
        }

    }
}