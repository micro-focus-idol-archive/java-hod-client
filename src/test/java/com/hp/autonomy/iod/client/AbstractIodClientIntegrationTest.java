package com.hp.autonomy.iod.client;

import com.hp.autonomy.iod.client.converter.IodConverter;
import com.hp.autonomy.iod.client.error.IodErrorHandler;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import retrofit.RestAdapter;
import retrofit.client.ApacheClient;
import retrofit.converter.JacksonConverter;

/*
 * $Id:$
 *
 * Copyright (c) 2015, Autonomy Systems Ltd.
 *
 * Last modified by $Author:$ on $Date:$
 */
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
