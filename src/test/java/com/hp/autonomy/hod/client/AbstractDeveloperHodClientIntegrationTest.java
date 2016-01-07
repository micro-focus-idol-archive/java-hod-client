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
import com.hp.autonomy.hod.client.error.HodErrorException;

import java.util.UUID;

public abstract class AbstractDeveloperHodClientIntegrationTest extends AbstractHodClientIntegrationTest {
    private UUID tenantUuid;
    private UUID developerUuid;
    private AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> developerToken;

    public AbstractDeveloperHodClientIntegrationTest(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    protected void setUp() {
        super.setUp();

        final AuthenticationService authenticationService = new AuthenticationServiceImpl(getConfig());

        try {
            tenantUuid = authenticationService.getApplicationTokenInformation(getTokenProxy()).getTenantUuid();

            developerToken = authenticationService.authenticateDeveloper(
                getEndpoint().getDeveloperApiKey(),
                tenantUuid,
                getEndpoint().getDeveloperEmail()
            );

            developerUuid = authenticationService.getDeveloperTokenInformation(developerToken).getDeveloper().getUuid();
        } catch (final HodErrorException e) {
            throw new IllegalStateException("Could not obtain developer token and details");
        }
    }

    protected UUID getTenantUuid() {
        return tenantUuid;
    }

    protected UUID getDeveloperUuid() {
        return developerUuid;
    }

    protected AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> getDeveloperToken() {
        return developerToken;
    }
}
