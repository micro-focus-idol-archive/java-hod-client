/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static com.hp.autonomy.hod.client.api.resource.ResourcesServiceITCase.ResourceMatcher.hasResourceWithIdentifier;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class ResourcesServiceITCase extends AbstractHodClientIntegrationTest {
    private ResourcesService resourcesService;

    public ResourcesServiceITCase(final Endpoint endpoint) {
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
                .setTypes(ResourceType.of(ResourceType.TEXT_INDEX));

        final List<Resource> resources = resourcesService.list(getTokenProxy(), parameters);

        final List<Resource> publicIndexes = resources.stream()
                .filter(resource -> ResourceIdentifier.PUBLIC_INDEXES_DOMAIN.equals(resource.getResource().getDomain()))
                .collect(Collectors.toList());

        assertThat(publicIndexes, is(not(empty())));

        for (final Resource publicResource : publicIndexes) {
            assertThat(publicResource.getType(), is(ResourceType.TEXT_INDEX));
            assertThat(publicResource.getResource(), is(not(nullValue())));
        }

        assertThat(resources, hasResourceWithIdentifier(ResourceIdentifier.WIKI_ENG));
        assertThat(resources, hasResourceWithIdentifier(getPrivateIndex()));
    }

    @Test
    public void listsAllResources() throws HodErrorException {
        resourcesService.list(getTokenProxy(), new ListResourcesRequestBuilder());
    }

    @Test
    public void listsDomainResource() throws HodErrorException {
        final String privateDomain = getPrivateIndex().getDomain();
        final List<Resource> resources = resourcesService.list(getTokenProxy(), new ListResourcesRequestBuilder().setDomains(Collections.singleton(privateDomain)));

        final Optional<Resource> maybeResource = resources.stream()
                .filter(resource -> !resource.getResource().getDomain().equals(privateDomain))
                .findFirst();

        assertThat("A resource with the wrong domain was returned", maybeResource, isEmpty());
    }

    static class ResourceMatcher extends BaseMatcher<List<Resource>> {
        private final ResourceIdentifier identifier;

        private ResourceMatcher(final ResourceIdentifier identifier) {
            this.identifier = identifier;
        }

        static ResourceMatcher hasResourceWithIdentifier(final ResourceIdentifier identifier) {
            return new ResourceMatcher(identifier);
        }

        @Override
        public boolean matches(final Object item) {
            if (!(item instanceof List)) {
                return false;
            }

            @SuppressWarnings("unchecked")
            final Optional<Resource> maybeResource = ((Collection<Resource>) item).stream()
                    .filter(resource -> identifier.equals(resource.getResource().getIdentifier()))
                    .findFirst();

            return maybeResource.isPresent();
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText("A list containing a resource with identifier: ").appendValue(identifier);
        }
    }
}