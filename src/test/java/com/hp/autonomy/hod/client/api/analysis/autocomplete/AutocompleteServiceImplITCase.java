/*
 * Copyright 2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.analysis.autocomplete;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class AutocompleteServiceImplITCase extends AbstractHodClientIntegrationTest {
    private AutocompleteService service;

    public AutocompleteServiceImplITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        service = new AutocompleteServiceImpl(getConfig());
    }

    @Test
    public void getSuggestions() throws HodErrorException {
        final List<String> suggestions = service.getSuggestions("fleetwood", getTokenProxy());
        assertThat(suggestions, hasItem("fleetwood mac"));
    }

    @Test
    public void getNoSuggestions() throws HodErrorException {
        final List<String> suggestions = service.getSuggestions("foobarbaz", getTokenProxy());
        assertThat(suggestions, is(empty()));
    }
}