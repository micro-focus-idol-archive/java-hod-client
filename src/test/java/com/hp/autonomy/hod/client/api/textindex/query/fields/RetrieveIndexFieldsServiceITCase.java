/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.fields;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.types.requests.idol.actions.tags.params.FieldTypeParam;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class RetrieveIndexFieldsServiceITCase extends AbstractHodClientIntegrationTest {
    private static final List<ResourceName> SAMPLE_INDEXES = Arrays.asList(ResourceName.WIKI_ENG, ResourceName.NEWS_ENG);
    private static final String WIKI_ENG_FIELD = "content";

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
    public void testRetrieveIndexFieldsCombined() throws HodErrorException {

        final RetrieveIndexFieldsRequestBuilder params = new RetrieveIndexFieldsRequestBuilder()
                .setFieldTypes(Collections.singletonList(FieldType.index));

        final RetrieveIndexFieldsResponse response = retrieveIndexFieldsService.retrieveIndexFields(
                getTokenProxy(),
                SAMPLE_INDEXES,
                params
        );

        validateResults(response);
    }

    @Test
    public void testRetrieveIndexFieldsPerIndex() throws HodErrorException {

        final RetrieveIndexFieldsRequestBuilder params = new RetrieveIndexFieldsRequestBuilder()
                .setFieldTypes(Collections.singletonList(FieldType.index));

        final Map<String, RetrieveIndexFieldsResponse> responses = retrieveIndexFieldsService.retrieveIndexFieldsByIndex(
                getTokenProxy(),
                SAMPLE_INDEXES,
                params
        );

        assertThat(responses.keySet(), hasSize(SAMPLE_INDEXES.size()));
        final RetrieveIndexFieldsResponse response = responses.get(ResourceName.WIKI_ENG.getName());
        validateResults(response);
    }

    private void validateResults(final RetrieveIndexFieldsResponse response) {
        assertThat(response.getTotalFields(), greaterThan(0));
        assertThat(response.getFieldTypeCounts(), is(notNullValue()));
        assertThat(response.getFields().get(FieldTypeParam.Index), hasItem(WIKI_ENG_FIELD));
    }

}
