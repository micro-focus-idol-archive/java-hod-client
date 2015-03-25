/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client;

import org.junit.Before;
import retrofit.RestAdapter;

public abstract class AbstractIodClientIntegrationTest {

    private RestAdapter restAdapter;

    @Before
    public void setUp() {
        restAdapter = RestAdapterFactory.getRestAdapter(false);
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
