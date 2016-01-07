/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class FindRelatedConceptsServiceITCase extends AbstractHodClientIntegrationTest {

    private FindRelatedConceptsService findRelatedConceptsService;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        findRelatedConceptsService = new FindRelatedConceptsServiceImpl(getConfig());
    }

    public FindRelatedConceptsServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testFindForText() throws HodErrorException {
        final FindRelatedConceptsRequestBuilder params = new FindRelatedConceptsRequestBuilder()
            .setIndexes(Collections.singleton(ResourceIdentifier.WIKI_ENG));

        final List<Entity> entities = findRelatedConceptsService.findRelatedConceptsWithText(getTokenProxy(), "Hewlett", params);

        assertThat(entities.size(), is(greaterThan(0)));

        final Entity entity0 = entities.get(0);

        assertThat(entity0.getOccurrences(), is(greaterThan(0)));
        assertThat(entity0.getDocsWithPhrase(), is(greaterThan(0)));
        assertThat(entity0.getDocsWithAllTerms(), is(greaterThan(0)));
        assertThat(entity0.getCluster(), is(greaterThanOrEqualTo(0)));
    }

    @Test
    public void testFindForFile() throws HodErrorException {
        final File file =  new File("src/test/resources/com/hp/autonomy/hod/client/api/textindex/query/queryText.txt");

        final FindRelatedConceptsRequestBuilder params = new FindRelatedConceptsRequestBuilder()
            .setIndexes(Collections.singleton(ResourceIdentifier.WIKI_ENG));

        final List<Entity> entities = findRelatedConceptsService.findRelatedConceptsWithFile(getTokenProxy(), file, params);

        assertThat(entities.size(), is(greaterThan(0)));

        final Entity entity0 = entities.get(0);

        assertThat(entity0.getOccurrences(), is(greaterThan(0)));
        assertThat(entity0.getDocsWithPhrase(), is(greaterThan(0)));
        assertThat(entity0.getDocsWithAllTerms(), is(greaterThan(0)));
        assertThat(entity0.getCluster(), is(greaterThanOrEqualTo(0)));
    }

}
