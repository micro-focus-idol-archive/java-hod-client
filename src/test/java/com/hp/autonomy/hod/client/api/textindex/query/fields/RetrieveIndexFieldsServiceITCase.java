/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.fields;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class RetrieveIndexFieldsServiceITCase extends AbstractHodClientIntegrationTest {

    private RetrieveIndexFieldsService retrieveIndexFieldsService;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        retrieveIndexFieldsService = new RetrieveIndexFieldsServiceImpl(getConfig());
    }

    public RetrieveIndexFieldsServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testRetrieveIndexFieldsGrouped() throws HodErrorException {
        final String wikiEngField = "content";

        final RetrieveIndexFieldsRequestBuilder params = new RetrieveIndexFieldsRequestBuilder()
                .setFieldType(FieldType.index)
                .setGroupFieldsByType(true);

        final RetrieveIndexFieldsResponse response = retrieveIndexFieldsService.retrieveIndexFields(getTokenProxy(), "wiki_eng", params);

        assertThat(response.getAllFields(), hasItem(wikiEngField));
        assertThat(response.getTotalFields(), greaterThan(0));
        assertThat(response.getFieldTypeCounts(), is(notNullValue()));
        assertThat(response.getIndexTypeFields(), hasItem(wikiEngField));
    }

}
