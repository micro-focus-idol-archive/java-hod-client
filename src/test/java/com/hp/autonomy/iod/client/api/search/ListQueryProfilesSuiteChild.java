/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.AbstractIodClientIntegrationTest;
import com.hp.autonomy.iod.client.Endpoint;
import com.hp.autonomy.iod.client.error.IodErrorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class ListQueryProfilesSuiteChild extends AbstractIodClientIntegrationTest {

    public ListQueryProfilesSuiteChild(Endpoint endpoint) {
        super(endpoint);
    }

    // Not under test
    private CreateQueryProfileService createQueryProfileService;
    private DeleteQueryProfileService deleteQueryProfileService;

    // Service under test
    private ListQueryProfilesService listQueryProfilesService;

    private List<QueryProfile> qpTestResources;

    @Before
    public void setUp() {
        super.setUp();

        createQueryProfileService = getRestAdapter().create(CreateQueryProfileService.class);
        deleteQueryProfileService = getRestAdapter().create(DeleteQueryProfileService.class);
        listQueryProfilesService = getRestAdapter().create(ListQueryProfilesService.class);

        qpTestResources = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (QueryProfile qp : qpTestResources) {
            try {
                // Try/catch as QueryProfiles might not have been created
                deleteQueryProfileService.deleteQueryProfile(endpoint.getApiKey(), qp.getName());
            } catch (IodErrorException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a QueryProfile object for using in tests.  Note that the name of the QueryProfile will not be the same as
     * the nameSuffix that is passed in.
     * @param nameSuffix  A unique suffix to append to the name of the Query Profile.
     * @return  A QueryProfile object, with name and config.
     */
    private QueryProfile createTestQueryProfile(final String nameSuffix) {
        final String profileName = "iod_java_client_query_profile_test_" + nameSuffix;

        final List<String> categories = new ArrayList<>();
        categories.add("Promotions");

        final QueryProfilePromotions promotions = new QueryProfilePromotions.Builder()
                .setEnabled(true)
                .setEveryPage(false)
                .setIdentified(false)
                .setCategories(categories)
                .build();

        final QueryProfileConfig config = new QueryProfileConfig.Builder()
                .setQueryManipulationIndex(getQueryManipulationIndex())
                .setPromotions(promotions)
                .build();

        final QueryProfile qp = new QueryProfile.Builder()
                .setName(profileName)
                .setConfig(config)
                .build();

        qpTestResources.add(qp);

        return qp;
    }

    @Test
    public void testSingleQueryProfile() throws IodErrorException {
        // Setup
        final QueryProfile qp = createTestQueryProfile("001");
        createQueryProfileService.createQueryProfile(endpoint.getApiKey(), qp.getName(), qp.getConfig());
        final QueryProfiles listQPsResponse = listQueryProfilesService.listQueryProfiles(endpoint.getApiKey());

        // Test response from getQueryProfiles
        final Set<QueryProfileName> qps = listQPsResponse.getQueryProfiles();
        assertThat(qps.size(), is(1));
        for (QueryProfileName qpn : qps) {
            assertThat(qpn.getName(), is(qp.getName()));
        }

        // Test response from getNames convenience method
        final Set<String> qpNames = listQPsResponse.getNames();
        assertThat(qpNames.size(), is(1));
        assertThat(qpNames, contains(qp.getName()));
    }

    @Test
    public void testTwoQueryProfiles() throws IodErrorException {
        // Setup
        final QueryProfile qp1 = createTestQueryProfile("001");
        final QueryProfile qp2 = createTestQueryProfile("002");
        createQueryProfileService.createQueryProfile(endpoint.getApiKey(), qp1.getName(), qp1.getConfig());
        createQueryProfileService.createQueryProfile(endpoint.getApiKey(), qp2.getName(), qp2.getConfig());
        final QueryProfiles listQPsResponse = listQueryProfilesService.listQueryProfiles(endpoint.getApiKey());

        // Test response from getQueryProfiles
        final Set<QueryProfileName> qps = listQPsResponse.getQueryProfiles();
        assertThat(qps.size(), is(2));

        // Test response from getNames convenience method
        final Set<String> qpNames = listQPsResponse.getNames();
        assertThat(qpNames.size(), is(2));
        assertThat(qpNames, containsInAnyOrder(qp1.getName(), qp2.getName()));
    }

    @Test
    public void testSeveralQueryProfiles() throws IodErrorException {
        // Setup
        final Set<QueryProfile> testProfiles = new HashSet<>();
        final Set<String> testNames = new HashSet<>();

        for (int i = 0; i < 5; i++) {
            final QueryProfile qp = createTestQueryProfile("" + i);
            createQueryProfileService.createQueryProfile(endpoint.getApiKey(), qp.getName(), qp.getConfig());
            testProfiles.add(qp);
            testNames.add(qp.getName());
        }
        final QueryProfiles listQPsResponse = listQueryProfilesService.listQueryProfiles(endpoint.getApiKey());

        // Test response from getQueryProfiles
        final Set<QueryProfileName> qps = listQPsResponse.getQueryProfiles();
        assertThat(qps.size(), is(testProfiles.size()));

        // Test response from getNames convenience method
        final Set<String> qpNames = listQPsResponse.getNames();
        assertThat(qpNames.size(), is(testProfiles.size()));
        assertThat(qpNames, containsInAnyOrder(testNames.toArray(new String[testNames.size()])));
    }

    @Test
    public void testNoQueryProfiles() throws IodErrorException {
        final QueryProfiles listQPsResponse = listQueryProfilesService.listQueryProfiles(endpoint.getApiKey());

        // Test response from getQueryProfiles
        final Set<QueryProfileName> qps = listQPsResponse.getQueryProfiles();
        assertThat(qps.size(), is(0));

        // Test response from getNames convenience method
        final Set<String> qpNames = listQPsResponse.getNames();
        assertThat(qpNames.size(), is(0));
    }
}
