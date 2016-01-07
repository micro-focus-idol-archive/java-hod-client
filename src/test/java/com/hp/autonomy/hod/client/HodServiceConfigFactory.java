/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client;

import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.token.TokenProxyService;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.HttpClientBuilder;

public class HodServiceConfigFactory {

    public static HodServiceConfig<EntityType.Application, TokenType.Simple> getHodServiceConfig(
        final TokenProxyService<EntityType.Application, TokenType.Simple> tokenProxyService,
        final Endpoint endpoint
    ) {
        final HttpClientBuilder builder = HttpClientBuilder.create();
        builder.disableCookieManagement();

        final String proxyHost = System.getProperty("hp.hod.https.proxyHost");

        if (proxyHost != null) {
            final Integer proxyPort = Integer.valueOf(System.getProperty("hp.hod.https.proxyPort", "8080"));
            builder.setProxy(new HttpHost(proxyHost, proxyPort));
        }

        final HodServiceConfig.Builder<EntityType.Application, TokenType.Simple> configBuilder = new HodServiceConfig.Builder<EntityType.Application, TokenType.Simple>(endpoint.getUrl())
            .setHttpClient(builder.build());


        if (tokenProxyService != null) {
            configBuilder.setTokenProxyService(tokenProxyService);
        }

        return configBuilder.build();
    }

}
