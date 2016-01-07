/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.parametric;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.types.requests.idol.actions.tags.QueryTagCountInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

@Slf4j
@RunWith(Parameterized.class)
public class GetParametricValuesServiceITCase extends AbstractHodClientIntegrationTest {

    private GetParametricValuesService getParametricValuesService;

    public GetParametricValuesServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();

        getParametricValuesService = new GetParametricValuesServiceImpl(getConfig());
    }

    @Test
    public void testGetParametricValues() throws HodErrorException {
        final GetParametricValuesRequestBuilder params = new GetParametricValuesRequestBuilder()
                .setSort(ParametricSort.alphabetical)
                .setMaxValues(15);

        final FieldNames fieldNames = getParametricValuesService.getParametricValues(
                getTokenProxy(),
                Arrays.asList("wikipedia_type", "person_profession"),
                Collections.singletonList(ResourceIdentifier.WIKI_ENG),
                params
        );

        final Set<String> fieldNamesSet = fieldNames.getFieldNames();

        assertThat(fieldNamesSet, hasItems("wikipedia_type", "person_profession"));

        assertThat(fieldNames.getValuesForFieldName("person_profession").size(), is(lessThanOrEqualTo(15)));

        final List<QueryTagCountInfo> wikipediaTypes = fieldNames.getValuesAndCountsForFieldName("wikipedia_type");

        final String first = wikipediaTypes.get(0).getValue();
        final String last = wikipediaTypes.get(wikipediaTypes.size() - 1).getValue();

        assertThat(first.compareTo(last), is(lessThan(0)));

    }
}
