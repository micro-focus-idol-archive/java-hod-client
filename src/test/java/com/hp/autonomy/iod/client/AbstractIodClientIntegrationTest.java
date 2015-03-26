package com.hp.autonomy.iod.client;


import org.junit.runners.Parameterized;
import retrofit.RestAdapter;

import java.util.Arrays;
import java.util.Collection;

/*
 * $Id:$
 *
 * Copyright (c) 2015, Autonomy Systems Ltd.
 *
 * Last modified by $Author:$ on $Date:$
 */
public abstract class AbstractIodClientIntegrationTest {

    private RestAdapter restAdapter;

    public void setUp() {
        restAdapter = RestAdapterFactory.getRestAdapter(false);
    }

    public void setUp(final Endpoint endpoint) {
        restAdapter = RestAdapterFactory.getRestAdapter(false, endpoint);
    }

    @Parameterized.Parameters
    public static Collection<Endpoint> endPoints() {
        return Arrays.asList(
                Endpoint.PRODUCTION,
                Endpoint.DEVELOPMENT
        );
    }

    public String getIndex() {
        return "java-iod-client-integration-tests";
    }

    public RestAdapter getRestAdapter() {
        return restAdapter;
    }

    public String getApiKey() {
        return System.getProperty("hp.iod.apiKey");
    }
}
