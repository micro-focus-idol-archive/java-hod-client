/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.developer;

import com.hp.autonomy.hod.client.AbstractDeveloperHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.authentication.ApiKey;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationService;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationServiceImpl;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class DeveloperServiceImplITCase extends AbstractDeveloperHodClientIntegrationTest {
    private DeveloperService developerService;

    public DeveloperServiceImplITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();

        developerService = new DeveloperServiceImpl(getConfig());
    }

    @Test
    public void addAuthentication() throws HodErrorException {
        final ApiKey newApiKey = developerService.addAuthentication(getDeveloperToken(), getDeveloperUuid());
        assertThat(newApiKey, not(nullValue()));

        // Check we can authenticate with the new API key
        final AuthenticationService authenticationService = new AuthenticationServiceImpl(getConfig());

        final AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token = authenticationService.authenticateDeveloper(
            newApiKey,
            getTenantUuid(),
            getEndpoint().getDeveloperEmail()
        );

        assertThat(token, not(nullValue()));
    }
}
