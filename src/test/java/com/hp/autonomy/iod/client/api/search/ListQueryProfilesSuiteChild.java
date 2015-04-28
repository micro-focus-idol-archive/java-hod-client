/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.AbstractQueryProfileIntegrationTest;
import com.hp.autonomy.iod.client.Endpoint;
import com.hp.autonomy.iod.client.error.IodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class ListQueryProfilesSuiteChild extends AbstractQueryProfileIntegrationTest {

    public ListQueryProfilesSuiteChild(Endpoint endpoint) {
        super(endpoint);
    }

    // Service under test
    private ListQueryProfilesService listQueryProfilesService;

    @Before
    public void setUp() {
        super.setUp();
        listQueryProfilesService = getRestAdapter().create(ListQueryProfilesService.class);
    }

    @Test
    public void testSingleQueryProfile() throws IodErrorException {
        // Setup

        final QueryProfile qp = createQueryProfile("001");
        final QueryProfiles listQPsResponse = listQueryProfilesService.listQueryProfiles(endpoint.getApiKey());

        // Test response from getQueryProfiles
        final Set<QueryProfileName> qps = listQPsResponse.getQueryProfiles();
        assertThat(qps, hasSize(1));
        for (QueryProfileName qpn : qps) {
            assertThat(qpn.getName(), is(qp.getName()));
        }

        // Test response from getNames convenience method
        final Set<String> qpNames = listQPsResponse.getNames();
        assertThat(qpNames, hasSize(1));
        assertThat(qpNames, contains(qp.getName()));
    }

    @Test
    public void testTwoQueryProfiles() throws IodErrorException {
        // Setup
        final QueryProfile qp1 = createQueryProfile("001");
        final QueryProfile qp2 = createQueryProfile("002");
        final QueryProfiles listQPsResponse = listQueryProfilesService.listQueryProfiles(endpoint.getApiKey());

        // Test response from getQueryProfiles
        final Set<QueryProfileName> qps = listQPsResponse.getQueryProfiles();
        assertThat(qps, hasSize(2));

        // Test response from getNames convenience method
        final Set<String> qpNames = listQPsResponse.getNames();
        assertThat(qpNames, hasSize(2));
        assertThat(qpNames, containsInAnyOrder(qp1.getName(), qp2.getName()));
    }

    @Test
    public void testSeveralQueryProfiles() throws IodErrorException {
        // Setup
        final Set<QueryProfile> testProfiles = new HashSet<>();
        final Set<String> testNames = new HashSet<>();

        for (int i = 0; i < 5; i++) {
            final QueryProfile qp = createQueryProfile("" + i);
            testProfiles.add(qp);
            testNames.add(qp.getName());
        }
        final QueryProfiles listQPsResponse = listQueryProfilesService.listQueryProfiles(endpoint.getApiKey());

        // Test response from getQueryProfiles
        final Set<QueryProfileName> qps = listQPsResponse.getQueryProfiles();
        assertThat(qps, hasSize(testProfiles.size()));

        // Test response from getNames convenience method
        final Set<String> qpNames = listQPsResponse.getNames();
        assertThat(qpNames, hasSize(testProfiles.size()));
        assertThat(qpNames, containsInAnyOrder(testNames.toArray(new String[testNames.size()])));
    }

    @Test
    public void testNoQueryProfiles() throws IodErrorException {
        final QueryProfiles listQPsResponse = listQueryProfilesService.listQueryProfiles(endpoint.getApiKey());

        // Test response from getQueryProfiles
        final Set<QueryProfileName> qps = listQPsResponse.getQueryProfiles();
        assertThat(qps, hasSize(0));

        // Test response from getNames convenience method
        final Set<String> qpNames = listQPsResponse.getNames();
        assertThat(qpNames, hasSize(0));
    }
}
