/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationBackend;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
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
    protected static final String APPLICATION_NAME = System.getProperty("hp.hod.application", "IOD-TEST-APPLICATION");
    protected static final String DOMAIN_NAME = System.getProperty("hp.hod.domain", "IOD-TEST-DOMAIN");

    protected static final ResourceIdentifier PRIVATE_INDEX = new ResourceIdentifier(DOMAIN_NAME, "java-iod-client-integration-tests");

    protected final Endpoint endpoint;
    private HodServiceConfig hodServiceConfig;
    private RestAdapter restAdapter;
    private AuthenticationToken token;
    private TokenProxy tokenProxy;

    public void setUp() {
        hodServiceConfig = HodServiceConfigFactory.getHodServiceConfig(null, endpoint);
        restAdapter = hodServiceConfig.getRestAdapter();

        final AuthenticationBackend authenticationBackend = restAdapter.create(AuthenticationBackend.class);

        try {
            token = authenticationBackend.authenticateApplication(
                endpoint.getApiKey(),
                APPLICATION_NAME,
                DOMAIN_NAME,
                TokenType.simple
            ).getToken();

            tokenProxy = hodServiceConfig.getTokenRepository().insert(token);
        } catch (final HodErrorException | IOException e) {
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

    public HodServiceConfig getConfig() {
        return hodServiceConfig;
    }

    public AuthenticationToken getToken() {
        return token;
    }

    public TokenProxy getTokenProxy() {
        return tokenProxy;
    }

}
