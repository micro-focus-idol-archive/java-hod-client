/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client;


import org.junit.runners.Parameterized;
import retrofit.RestAdapter;

import java.util.Arrays;
import java.util.Collection;


public abstract class AbstractIodClientIntegrationTest {

    private RestAdapter restAdapter;

    public void setUp(final Endpoint endpoint) {
        restAdapter = RestAdapterFactory.getRestAdapter(false, endpoint);
    }

    @Parameterized.Parameters
    public static Collection<Endpoint> endPoints() {
        return Arrays.asList(
                Endpoint.PRODUCTION
        );
    }

    public String getIndex() {
        return "java-iod-client-integration-tests";
    }

    public String getQueryManipulationIndex() {
        return "java-iod-client-integration-tests-query-manipulation";
    }

    public RestAdapter getRestAdapter() {
        return restAdapter;
    }

}
