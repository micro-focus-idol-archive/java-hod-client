/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.AbstractIodClientIntegrationTest;
import com.hp.autonomy.iod.client.Endpoint;
import com.hp.autonomy.iod.client.error.IodErrorException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

@Slf4j
@RunWith(Parameterized.class)
public class GetParametricValuesServiceITCase extends AbstractIodClientIntegrationTest {

    private GetParametricValuesService getParametricValuesService;

    public GetParametricValuesServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Before
    public void setUp() {
        super.setUp();

        getParametricValuesService = getRestAdapter().create(GetParametricValuesService.class);
    }

    @Test
    public void testGetParametricValues() throws IodErrorException {
        final Map<String, Object> params = new GetParametricValuesRequestBuilder()
                .setSort(ParametricSort.alphabetical)
                .setMaxValues(15)
                .build();

        final FieldNames fieldNames = getParametricValuesService.getParametricValues(endpoint.getApiKey(), "wikipedia_type, person_profession", params);

        final Set<String> fieldNamesSet = fieldNames.getFieldNames();

        assertThat(fieldNamesSet, hasItems("wikipedia_type", "person_profession"));

        assertThat(fieldNames.getValuesForFieldName("person_profession").size(), is(lessThanOrEqualTo(15)));

        final List<FieldNames.ValueAndCount> wikipediaTypes = fieldNames.getValuesAndCountsForFieldName("wikipedia_type");

        final String first = wikipediaTypes.get(0).getValue();
        final String last = wikipediaTypes.get(wikipediaTypes.size() - 1).getValue();

        assertThat(first.compareTo(last), is(lessThan(0)));

    }
}
