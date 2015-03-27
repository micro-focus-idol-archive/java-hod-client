/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client;

import com.hp.autonomy.iod.client.converter.IodConverter;
import com.hp.autonomy.iod.client.error.IodErrorHandler;
import com.hp.autonomy.iod.client.util.ApiKeyRequestInterceptor;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.HttpClientBuilder;
import retrofit.RestAdapter;
import retrofit.client.ApacheClient;
import retrofit.converter.JacksonConverter;

public class RestAdapterFactory {

    public static RestAdapter getRestAdapter(final boolean withInterceptor) {
        return getRestAdapter(withInterceptor, Endpoint.PRODUCTION);
    }

    public static RestAdapter getRestAdapter(final boolean withInterceptor, final Endpoint endpoint) {
        final HttpClientBuilder builder = HttpClientBuilder.create();

        final String proxyHost = System.getProperty("hp.iod.https.proxyHost");

        if (proxyHost != null) {
            final Integer proxyPort = Integer.valueOf(System.getProperty("hp.iod.https.proxyPort", "8080"));
            builder.setProxy(new HttpHost(proxyHost, proxyPort));
        }

        final RestAdapter.Builder restAdapterBuilder = new RestAdapter.Builder()
                .setEndpoint(endpoint.getUrl())
                .setClient(new ApacheClient(builder.build()))
                .setConverter(new IodConverter(new JacksonConverter()))
                .setErrorHandler(new IodErrorHandler());


        if (withInterceptor) {
            restAdapterBuilder.setRequestInterceptor(new ApiKeyRequestInterceptor(endpoint.getApiKey()));
        }

        return restAdapterBuilder.build();
    }

}
