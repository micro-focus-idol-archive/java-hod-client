/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client;

import com.hp.autonomy.hod.client.api.authentication.ApiKey;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationService;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.runners.Parameterized;
import retrofit.RestAdapter;

import java.util.Arrays;
import java.util.Collection;

public abstract class AbstractHodClientIntegrationTest {

    private RestAdapter restAdapter;
    protected final Endpoint endpoint;
    private AuthenticationService authenticationService;


    public void setUp() {
        restAdapter = RestAdapterFactory.getRestAdapter(false, endpoint);
        authenticationService = restAdapter.create(AuthenticationService.class);
    }

    @Parameterized.Parameters
    public static Collection<Endpoint> endPoints() {
        final Endpoint endpoint = Enum.valueOf(Endpoint.class, System.getProperty("hp.hod.env", "PRODUCTION"));
        return Arrays.asList(endpoint);
    }

    /**
     * Make sure you override this and call super(endpoint);
     * @param endpoint
     */
    public AbstractHodClientIntegrationTest(final Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public String getIndex() {
        return "java-iod-client-integration-tests";
    }

    public String getQueryManipulationIndex() {
        return "java-iod-client-integration-tests-query-manipulation";
    }

    public RestAdapter getRestAdapter() {
        return restAdapter;
    }

    public AuthenticationToken getToken() throws HodErrorException {
        return authenticationService.authenticateApplication(
            new ApiKey(System.getProperty("hp.dev.placeholder.hod.apiKey")),
            "IOD-TEST-APPLICATION",
            "IOD-TEST-DOMAIN",
            TokenType.simple
        ).getToken();
    }

}
