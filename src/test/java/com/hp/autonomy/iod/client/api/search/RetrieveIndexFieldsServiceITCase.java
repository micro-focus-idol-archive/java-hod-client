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

import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class RetrieveIndexFieldsServiceITCase extends AbstractIodClientIntegrationTest {

    private RetrieveIndexFieldsService retrieveIndexFieldsService;

    @Before
    public void setUp() {
        super.setUp();

        retrieveIndexFieldsService = getRestAdapter().create(RetrieveIndexFieldsService.class);
    }

    public RetrieveIndexFieldsServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testRetrieveIndexFieldsGrouped() throws IodErrorException {
        final String wikiEngField = "drecontent";

        final Map<String,Object> params = new RetrieveIndexFieldsRequestBuilder()
                .setFieldType(FieldType.index)
                .setGroupFieldsByType(true)
                .build();

        final RetrieveIndexFieldsResponse response = retrieveIndexFieldsService.retrieveIndexFields(endpoint.getApiKey(), params);

        assertThat(response.getAllFields(), hasItem(wikiEngField));
        assertThat(response.getTotalFields(), greaterThan(0));
        assertThat(response.getFieldTypeCounts(), is(notNullValue()));
        assertThat(response.getIndexTypeFields(), hasItem(wikiEngField));
    }

}
