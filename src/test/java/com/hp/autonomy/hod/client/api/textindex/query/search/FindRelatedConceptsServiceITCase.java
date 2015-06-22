/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
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

    private FindRelatedConceptsBackend findRelatedConceptsBackend;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        findRelatedConceptsBackend = getRestAdapter().create(FindRelatedConceptsBackend.class);
    }

    public FindRelatedConceptsServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testFindForText() throws HodErrorException {
        final Entities entities = findRelatedConceptsBackend.findRelatedConceptsWithText(getToken(), "Hewlett", null);

        final List<Entity> entitiesList = entities.getEntities();

        assertThat(entitiesList.size(), is(greaterThan(0)));

        final Entity entity0 = entitiesList.get(0);

        assertThat(entity0.getOccurrences(), is(greaterThan(0)));
    }

    @Test
    public void testFindForFile() throws HodErrorException {
        final TypedFile file = new TypedFile("text/plain", new File("src/test/resources/com/hp/autonomy/hod/client/api/textindexing/query/queryText.txt"));

        final Entities entities = findRelatedConceptsBackend.findRelatedConceptsWithFile(getToken(), file, null);

        final List<Entity> entitiesList = entities.getEntities();

        assertThat(entitiesList.size(), is(greaterThan(0)));

        final Entity entity0 = entitiesList.get(0);

        assertThat(entity0.getOccurrences(), is(greaterThan(0)));
    }

}
