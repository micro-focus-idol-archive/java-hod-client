/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.HodErrorTester;
import com.hp.autonomy.hod.client.api.authentication.tokeninformation.ApplicationTokenInformation;
import com.hp.autonomy.hod.client.api.authentication.tokeninformation.DeveloperTokenInformation;
import com.hp.autonomy.hod.client.api.authentication.tokeninformation.UnboundTokenInformation;
import com.hp.autonomy.hod.client.error.HodErrorCode;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.UUID;

import static com.hp.autonomy.hod.client.HodErrorTester.testErrorCode;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
@Slf4j
public class AuthenticationServiceITCase extends AbstractHodClientIntegrationTest {
    private AuthenticationServiceImpl authenticationService;
    private UUID tenantUuid;

    public AuthenticationServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        authenticationService = new AuthenticationServiceImpl(getConfig());

        try {
            tenantUuid = authenticationService.getApplicationTokenInformation(getTokenProxy()).getTenantUuid();
        } catch (final HodErrorException e) {
            throw new AssertionError("Could not determine tenant UUID");
        }
    }

    @Test
    public void testAuthenticateApplication() throws HodErrorException {
        final TokenProxy<EntityType.Application, TokenType.Simple> tokenProxy = authenticationService.authenticateApplication(
            getEndpoint().getApplicationApiKey(),
            getEndpoint().getApplicationName(),
            getEndpoint().getDomainName(),
            TokenType.Simple.INSTANCE
        );

        assertThat(tokenProxy, is(notNullValue()));
    }

    @Test
    public void unboundSimpleAuthentication() throws HodErrorException {
        final AuthenticationToken<EntityType.Unbound, TokenType.Simple> token = authenticationService.authenticateUnbound(
            getEndpoint().getApplicationApiKey(),
            TokenType.Simple.INSTANCE
        );

        assertThat(token.getEntityType(), is(EntityType.Unbound.INSTANCE));
        assertThat(token.getTokenType(), is(TokenType.Simple.INSTANCE));
    }

    @Test
    public void unboundHmacAuthentication() throws HodErrorException {
        final AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> token = authenticationService.authenticateUnbound(
            getEndpoint().getApplicationApiKey(),
            TokenType.HmacSha1.INSTANCE
        );

        assertThat(token.getEntityType(), is(EntityType.Unbound.INSTANCE));
        assertThat(token.getTokenType(), is(TokenType.HmacSha1.INSTANCE));
    }

    @Test
    public void developerAuthentication() throws HodErrorException {
        final AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token = authenticationService.authenticateDeveloper(
            getEndpoint().getDeveloperApiKey(),
            tenantUuid,
            getEndpoint().getDeveloperEmail()
        );

        assertThat(token.getEntityType(), is(EntityType.Developer.INSTANCE));
        assertThat(token.getTokenType(), is(TokenType.HmacSha1.INSTANCE));

        assertThat(token.getType(), is("DEV:HMAC_SHA1"));

        assertThat(token.getExpiry(), not(nullValue()));
        assertThat(token.getId(), not(nullValue()));
        assertThat(token.getSecret(), not(nullValue()));
        assertThat(token.getStartRefresh(), not(nullValue()));
    }

    @Test
    public void getApplicationTokenInformation() throws HodErrorException, IOException {
        final String applicationName = getEndpoint().getApplicationName();
        final String domainName = getEndpoint().getDomainName();

        final TokenProxy<EntityType.Application, TokenType.Simple> tokenProxy = authenticationService.authenticateApplication(
            getEndpoint().getApplicationApiKey(),
            applicationName,
            domainName,
            TokenType.Simple.INSTANCE
        );

        final ApplicationTokenInformation information = authenticationService.getApplicationTokenInformation(tokenProxy);

        assertThat(information.getTenantUuid(), is(tenantUuid));

        assertThat(information.getApplication().getName(), is(applicationName));
        assertThat(information.getApplication().getDomain(), is(domainName));

        assertThat(information.getApplication().getAuthentication().getUuid(), not(nullValue()));
        assertThat(information.getApplication().getAuthentication().getType(), not(nullValue()));
    }

    @Test
    public void getHmacApplicationTokenInformation() throws HodErrorException {
        final String applicationName = getEndpoint().getApplicationName();
        final String domainName = getEndpoint().getDomainName();

        final TokenProxy<EntityType.Application, TokenType.HmacSha1> tokenProxy = authenticationService.authenticateApplication(
            getEndpoint().getApplicationApiKey(),
            applicationName,
            domainName,
            TokenType.HmacSha1.INSTANCE
        );

        final ApplicationTokenInformation information = authenticationService.getHmacApplicationTokenInformation(tokenProxy);

        assertThat(information.getTenantUuid(), is(tenantUuid));

        assertThat(information.getApplication().getName(), is(applicationName));
        assertThat(information.getApplication().getDomain(), is(domainName));

        assertThat(information.getApplication().getAuthentication().getUuid(), not(nullValue()));
        assertThat(information.getApplication().getAuthentication().getType(), not(nullValue()));
    }

    @Test
    public void getUnboundTokenInformation() throws HodErrorException {
        final AuthenticationToken<EntityType.Unbound, TokenType.Simple> token = authenticationService.authenticateUnbound(
            getEndpoint().getApplicationApiKey(),
            TokenType.Simple.INSTANCE
        );

        final UnboundTokenInformation information = authenticationService.getUnboundTokenInformation(token);

        assertThat(information.getAuthentication().getType(), not(nullValue()));
        assertThat(information.getAuthentication().getUuid(), not(nullValue()));
    }

    @Test
    public void getHmacUnboundTokenInformation() throws HodErrorException {
        final AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> token = authenticationService.authenticateUnbound(
            getEndpoint().getApplicationApiKey(),
            TokenType.HmacSha1.INSTANCE
        );

        final UnboundTokenInformation information = authenticationService.getHmacUnboundTokenInformation(token);

        assertThat(information.getAuthentication().getType(), not(nullValue()));
        assertThat(information.getAuthentication().getUuid(), not(nullValue()));
    }

    @Test
    public void getDeveloperTokenInformation() throws HodErrorException {
        final AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token = authenticationService.authenticateDeveloper(
            getEndpoint().getDeveloperApiKey(),
            tenantUuid,
            getEndpoint().getDeveloperEmail()
        );

        final DeveloperTokenInformation information = authenticationService.getDeveloperTokenInformation(token);

        assertThat(information.getTenantUuid(), is(tenantUuid));

        assertThat(information.getDeveloper().getName(), not(nullValue()));
        assertThat(information.getDeveloper().getUuid(), not(nullValue()));

        assertThat(information.getDeveloper().getAuthentication().getType(), not(nullValue()));
        assertThat(information.getDeveloper().getAuthentication().getUuid(), not(nullValue()));
    }

    @Test
    public void failsWithInvalidApiKey() {
        testErrorCode(HodErrorCode.AUTHENTICATION_FAILED, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                authenticationService.authenticateUnbound(new ApiKey("PROBABLY_NOT_A_REAL_API_KEY"), TokenType.Simple.INSTANCE);
            }
        });
    }

    @Test
    public void getTokenInformationFailsWithInvalidToken() {
        final AuthenticationToken<EntityType.Combined, TokenType.Simple> fakeToken = new AuthenticationToken<>(
            EntityType.Combined.INSTANCE,
            TokenType.Simple.INSTANCE,
            new DateTime(123),
            "not-a-real-token",
            "not-a-real-secret",
            new DateTime(456)
        );

        testErrorCode(HodErrorCode.AUTHENTICATION_FAILED, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                authenticationService.getCombinedTokenInformation(fakeToken);
            }
        });
    }
}
