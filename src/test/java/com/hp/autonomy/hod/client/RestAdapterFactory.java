/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client;

import com.hp.autonomy.hod.client.converter.HodConverter;
import com.hp.autonomy.hod.client.error.HodErrorHandler;
import com.hp.autonomy.hod.client.util.AuthenticationTokenService;
import com.hp.autonomy.hod.client.util.AuthenticationTokenServiceRequestInterceptor;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.HttpClientBuilder;
import retrofit.RestAdapter;
import retrofit.client.ApacheClient;
import retrofit.converter.JacksonConverter;

public class RestAdapterFactory {

    public static RestAdapter getRestAdapter(final AuthenticationTokenService authenticationTokenService) {
        return getRestAdapter(authenticationTokenService, Endpoint.PRODUCTION);
    }

    public static RestAdapter getRestAdapter(final AuthenticationTokenService authenticationTokenService, final Endpoint endpoint) {
        final HttpClientBuilder builder = HttpClientBuilder.create();
        builder.disableCookieManagement();

        final String proxyHost = System.getProperty("hp.hod.https.proxyHost");

        if (proxyHost != null) {
            final Integer proxyPort = Integer.valueOf(System.getProperty("hp.hod.https.proxyPort", "8080"));
            builder.setProxy(new HttpHost(proxyHost, proxyPort));
        }

        final RestAdapter.Builder restAdapterBuilder = new RestAdapter.Builder()
                .setEndpoint(endpoint.getUrl())
                .setClient(new ApacheClient(builder.build()))
                .setConverter(new HodConverter(new JacksonConverter()))
                .setErrorHandler(new HodErrorHandler());


        if (authenticationTokenService != null) {
            restAdapterBuilder.setRequestInterceptor(new AuthenticationTokenServiceRequestInterceptor(authenticationTokenService));
        }

        return restAdapterBuilder.build();
    }

}
