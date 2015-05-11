/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.search;

import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.error.HodErrorException;
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
public class FindRelatedConceptsServiceITCase extends AbstractHodClientIntegrationTest {

    private FindRelatedConceptsService findRelatedConceptsService;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        findRelatedConceptsService = getRestAdapter().create(FindRelatedConceptsService.class);
    }

    public FindRelatedConceptsServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testFindForText() throws HodErrorException {
        final Entities entities = findRelatedConceptsService.findRelatedConceptsWithText(getToken(), "Hewlett", null);

        final List<Entity> entitiesList = entities.getEntities();

        assertThat(entitiesList.size(), is(greaterThan(0)));

        final Entity entity0 = entitiesList.get(0);

        assertThat(entity0.getOccurrences(), is(greaterThan(0)));
    }

    @Test
    public void testFindForFile() throws HodErrorException {
        final TypedFile file = new TypedFile("text/plain", new File("src/test/resources/com/hp/autonomy/hod/client/api/search/queryText.txt"));

        final Entities entities = findRelatedConceptsService.findRelatedConceptsWithFile(getToken(), file, null);

        final List<Entity> entitiesList = entities.getEntities();

        assertThat(entitiesList.size(), is(greaterThan(0)));

        final Entity entity0 = entitiesList.get(0);

        assertThat(entity0.getOccurrences(), is(greaterThan(0)));
    }

}
