/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.parametric;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.error.HodErrorException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
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
    public void getParametricValues() throws HodErrorException {
        final GetParametricValuesRequestBuilder params = new GetParametricValuesRequestBuilder()
                .setSort(ParametricSort.alphabetical)
                .setDocumentCount(true)
                .setTotalValues(true)
                .setMaxValues(15);

        final List<FieldValues> output = getParametricValuesService.getParametricValues(
                getTokenProxy(),
                Arrays.asList("wikipedia_type", "person_profession"),
                Collections.singletonList(ResourceName.WIKI_ENG),
                params
        );

        assertThat(output, hasSize(2));

        final FieldValues wikipediaType = findField(output, "wikipedia_type");
        assertThat(wikipediaType.getTotalValues(), is(greaterThan(0)));
        assertThat(wikipediaType.getValues(), hasSize(lessThanOrEqualTo(15)));

        // The wiki_eng wikipedia_type field has at least 2 values

        final FieldValues.ValueAndCount first = wikipediaType.getValues().get(0);
        assertThat(first.getCount(), not(nullValue()));
        assertThat(first.getSubField(), nullValue());

        final FieldValues.ValueAndCount last = wikipediaType.getValues().get(wikipediaType.getValues().size() - 1);
        assertThat(last.getCount(), not(nullValue()));
        assertThat(last.getSubField(), nullValue());

        assertThat(first.getValue().compareTo(last.getValue()), lessThan(0));

        final FieldValues personProfession = findField(output, "person_profession");
        assertThat(personProfession.getTotalValues(), is(greaterThan(0)));
        assertThat(wikipediaType.getValues(), hasSize(lessThanOrEqualTo(15)));
    }

    @Test
    public void getNestedParametricValues() throws HodErrorException {
        final GetParametricValuesRequestBuilder params = new GetParametricValuesRequestBuilder()
                .setSort(ParametricSort.alphabetical)
                .setNestFieldResults(true)
                .setMaxValues(10);

        final List<FieldValues> output = getParametricValuesService.getParametricValues(
                getTokenProxy(),
                Arrays.asList("wikipedia_type", "person_profession"),
                Collections.singletonList(ResourceName.WIKI_ENG),
                params
        );

        assertThat(output, hasSize(1));

        final FieldValues wikipediaType = findField(output, "wikipedia_type");
        assertThat(wikipediaType.getName(), is("wikipedia_type"));
        assertThat(wikipediaType.getValues(), hasSize(lessThanOrEqualTo(10)));

        final FieldValues subField = wikipediaType.getValues().get(0).getSubField();
        assertThat(subField.getName(), is("person_profession"));
    }

    private FieldValues findField(final Collection<FieldValues> fieldValuesList, final String fieldName) {
        return fieldValuesList.stream()
                .filter(fieldValues -> fieldName.equals(fieldValues.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(fieldName + " not present"));
    }
}
