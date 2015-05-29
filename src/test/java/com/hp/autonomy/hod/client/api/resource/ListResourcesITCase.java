package com.hp.autonomy.hod.client.api.resource;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.EnumSet;

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
        final ListResourcesRequestBuilder builder = new ListResourcesRequestBuilder();
        builder.setTypes(EnumSet.of(ResourceType.content));
        final Resources resources = resourcesService.list(getToken(), builder.build());

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