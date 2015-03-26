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

    public RestAdapter getRestAdapter() {
        return restAdapter;
    }

}
