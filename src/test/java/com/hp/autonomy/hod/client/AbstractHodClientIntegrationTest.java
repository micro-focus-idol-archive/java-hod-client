/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationService;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationServiceImpl;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import org.junit.runners.Parameterized;
import retrofit.RestAdapter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public abstract class AbstractHodClientIntegrationTest {
    protected static final String QUERY_MANIPULATION_INDEX_NAME = "java-iod-client-integration-tests-query-manipulation";

    private final Endpoint endpoint;
    private HodServiceConfig<EntityType.Application, TokenType.Simple> hodServiceConfig;
    private RestAdapter restAdapter;
    private AuthenticationToken<EntityType.Application, TokenType.Simple> token;
    private TokenProxy<EntityType.Application, TokenType.Simple> tokenProxy;

    protected void setUp() {
        System.setProperty("javax.net.debug", "all");

        hodServiceConfig = HodServiceConfigFactory.getHodServiceConfig(null, getEndpoint());
        restAdapter = hodServiceConfig.getRestAdapter();

        final AuthenticationService authenticationService = new AuthenticationServiceImpl(hodServiceConfig);

        try {
            tokenProxy = authenticationService.authenticateApplication(
                getEndpoint().getApplicationApiKey(),
                getEndpoint().getApplicationName(),
                getEndpoint().getDomainName(),
                TokenType.Simple.INSTANCE
            );

            token = hodServiceConfig.getTokenRepository().get(tokenProxy);
        } catch (final IOException | HodErrorException e) {
            throw new AssertionError("COULD NOT OBTAIN TOKEN", e);
        }
    }

    @Parameterized.Parameters
    public static Collection<Endpoint> endPoints() {
        final Endpoint endpoint = Enum.valueOf(Endpoint.class, System.getProperty("hp.hod.env", "PRODUCTION"));
        return Collections.singletonList(endpoint);
    }

    protected AbstractHodClientIntegrationTest(final Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    protected RestAdapter getRestAdapter() {
        return restAdapter;
    }

    protected HodServiceConfig<EntityType.Application, TokenType.Simple> getConfig() {
        return hodServiceConfig;
    }

    protected AuthenticationToken<EntityType.Application, TokenType.Simple> getToken() {
        return token;
    }

    protected TokenProxy<EntityType.Application, TokenType.Simple> getTokenProxy() {
        return tokenProxy;
    }

    protected ResourceIdentifier getPrivateIndex() {
        return new ResourceIdentifier(getEndpoint().getDomainName(), "java-iod-client-integration-tests");
    }

    protected ResourceIdentifier getUserStore() {
        return new ResourceIdentifier(getEndpoint().getDomainName(), getEndpoint().getUserStoreName());
    }

    protected Endpoint getEndpoint() {
        return endpoint;
    }
}
