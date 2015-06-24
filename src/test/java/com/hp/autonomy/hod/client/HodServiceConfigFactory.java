/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client;

import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.token.TokenProxyService;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.HttpClientBuilder;

public class HodServiceConfigFactory {

    public static HodServiceConfig getHodServiceConfig(final TokenProxyService tokenProxyService) {
        return getHodServiceConfig(tokenProxyService, Endpoint.PRODUCTION);
    }

    public static HodServiceConfig getHodServiceConfig(final TokenProxyService tokenProxyService, final Endpoint endpoint) {
        final HttpClientBuilder builder = HttpClientBuilder.create();
        builder.disableCookieManagement();

        final String proxyHost = System.getProperty("hp.hod.https.proxyHost");

        if (proxyHost != null) {
            final Integer proxyPort = Integer.valueOf(System.getProperty("hp.hod.https.proxyPort", "8080"));
            builder.setProxy(new HttpHost(proxyHost, proxyPort));
        }

        final HodServiceConfig.Builder configBuilder = new HodServiceConfig.Builder(endpoint.getUrl())
            .setHttpClient(builder.build());


        if (tokenProxyService != null) {
            configBuilder.setTokenProxyService(tokenProxyService);
        }

        return configBuilder.build();
    }

}
