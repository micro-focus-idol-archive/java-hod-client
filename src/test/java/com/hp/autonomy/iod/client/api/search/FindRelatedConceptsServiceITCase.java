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
import retrofit.mime.TypedFile;

import java.io.File;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class FindRelatedConceptsServiceITCase extends AbstractIodClientIntegrationTest {

    private FindRelatedConceptsService findRelatedConceptsService;

    @Before
    public void setUp() {
        super.setUp();

        findRelatedConceptsService = getRestAdapter().create(FindRelatedConceptsService.class);
    }

    public FindRelatedConceptsServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testFindForText() throws IodErrorException {
        final Entities entities = findRelatedConceptsService.findRelatedConceptsWithText(endpoint.getApiKey(), "Hewlett", null);

        final List<Entity> entitiesList = entities.getEntities();

        assertThat(entitiesList.size(), is(greaterThan(0)));

        final Entity entity0 = entitiesList.get(0);

        assertThat(entity0.getOccurrences(), is(greaterThan(0)));
    }

    @Test
    public void testFindForFile() throws IodErrorException {
        final TypedFile file = new TypedFile("text/plain", new File("src/test/resources/com/hp/autonomy/iod/client/api/search/queryText.txt"));

        final Entities entities = findRelatedConceptsService.findRelatedConceptsWithFile(endpoint.getApiKey(), file, null);

        final List<Entity> entitiesList = entities.getEntities();

        assertThat(entitiesList.size(), is(greaterThan(0)));

        final Entity entity0 = entitiesList.get(0);

        assertThat(entity0.getOccurrences(), is(greaterThan(0)));
    }

}
