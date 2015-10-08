/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client;

import com.hp.autonomy.hod.client.api.authentication.*;
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
    protected static final String APPLICATION_NAME = System.getProperty("hp.hod.application", "IOD-TEST-APPLICATION");
    protected static final String DOMAIN_NAME = System.getProperty("hp.hod.domain", "IOD-TEST-DOMAIN");
    protected static final String USER_STORE_NAME = System.getProperty("hp.hod.userstore", "DEFAULT_USER_STORE");
    protected static final String DEVELOPER_EMAIL = System.getProperty("hp.hod.developerEmail");

    protected static final ResourceIdentifier PRIVATE_INDEX = new ResourceIdentifier(DOMAIN_NAME, "java-iod-client-integration-tests");
    protected static final ResourceIdentifier USER_STORE = new ResourceIdentifier(DOMAIN_NAME, USER_STORE_NAME);

    protected final Endpoint endpoint;
    private HodServiceConfig<EntityType.Application, TokenType.Simple> hodServiceConfig;
    private RestAdapter restAdapter;
    private AuthenticationToken<EntityType.Application, TokenType.Simple> token;
    private TokenProxy<EntityType.Application, TokenType.Simple> tokenProxy;

    public void setUp() {
        hodServiceConfig = HodServiceConfigFactory.getHodServiceConfig(null, endpoint);
        restAdapter = hodServiceConfig.getRestAdapter();

        final AuthenticationService authenticationService = new AuthenticationServiceImpl(hodServiceConfig);

        try {
            tokenProxy = authenticationService.authenticateApplication(endpoint.getApiKey(), APPLICATION_NAME, DOMAIN_NAME, TokenType.Simple.INSTANCE);
            token = hodServiceConfig.getTokenRepository().get(tokenProxy);
        } catch (final IOException | HodErrorException e) {
            throw new AssertionError("COULD NOT OBTAIN TOKEN");
        }
    }

    @Parameterized.Parameters
    public static Collection<Endpoint> endPoints() {
        final Endpoint endpoint = Enum.valueOf(Endpoint.class, System.getProperty("hp.hod.env", "PRODUCTION"));
        return Collections.singletonList(endpoint);
    }

    public AbstractHodClientIntegrationTest(final Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public String getQueryManipulationIndex() {
        return "java-iod-client-integration-tests-query-manipulation";
    }

    public RestAdapter getRestAdapter() {
        return restAdapter;
    }

    public HodServiceConfig<EntityType.Application, TokenType.Simple> getConfig() {
        return hodServiceConfig;
    }

    public AuthenticationToken<EntityType.Application, TokenType.Simple> getToken() {
        return token;
    }

    public TokenProxy<EntityType.Application, TokenType.Simple> getTokenProxy() {
        return tokenProxy;
    }
}
