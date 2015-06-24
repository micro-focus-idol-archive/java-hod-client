package com.hp.autonomy.hod.client.api.authentication;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.error.HodErrorCode;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

/*
 * $Id:$
 *
 * Copyright (c) 2015, Autonomy Systems Ltd.
 *
 * Last modified by $Author:$ on $Date:$
 */
@RunWith(Parameterized.class)
@Slf4j
public class AuthenticationServiceITCase extends AbstractHodClientIntegrationTest {
    private static final String USER_STORE = "IOD-TEST-USER-STORE";

    private AuthenticationServiceImpl authenticationService;
    private ApiKey apiKey;

    public AuthenticationServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        apiKey = new ApiKey(System.getProperty("hp.dev.placeholder.hod.apiKey"));
        authenticationService = new AuthenticationServiceImpl(getConfig());
    }

    @Test
    public void testAuthenticateApplication() throws HodErrorException {
        final TokenProxy tokenProxy = authenticationService.authenticateApplication(
            apiKey,
            APPLICATION_NAME,
            DOMAIN_NAME,
            TokenType.simple
        );

        assertThat(tokenProxy, is(notNullValue()));
    }

    @Test
    public void testAuthenticateUser() throws HodErrorException {
        final TokenProxy tokenProxy = authenticationService.authenticateUser(apiKey, APPLICATION_NAME, DOMAIN_NAME, TokenType.simple);

        assertThat(tokenProxy, is(notNullValue()));
    }

    @Test
    public void testCombinedAuthentication() throws HodErrorException {
        final ApplicationUnboundResponse applicationUnboundResponse = authenticationService.authenticateApplicationUnbound(apiKey);

        final AuthenticationToken applicationUnboundToken = applicationUnboundResponse.getToken();
        assertThat(applicationUnboundToken, is(notNullValue()));

        final List<Application> applications = applicationUnboundResponse.getApplications();
        assertThat(applications, hasSize(1));
        assertThat(applications.get(0).getName(), is(APPLICATION_NAME));
        assertThat(applications.get(0).getDomain(), is(DOMAIN_NAME));

        final UserUnboundResponse userUnboundResponse = authenticationService.authenticateUserUnbound(apiKey);

        final AuthenticationToken userUnboundToken = userUnboundResponse.getToken();
        assertThat(userUnboundToken, is(notNullValue()));

        final List<User> users = userUnboundResponse.getUsers();
        assertThat(users, hasSize(1));
        assertThat(users.get(0).getUsername(), is(notNullValue()));
        assertThat(users.get(0).getUserStore(), is(DOMAIN_NAME + ':' + USER_STORE));

        final TokenProxy combinedTokenProxy = authenticationService.combineTokens(applicationUnboundToken, userUnboundToken, APPLICATION_NAME, DOMAIN_NAME, TokenType.simple);

        assertThat(combinedTokenProxy, is(notNullValue()));
    }

    @Test
    public void failsWithInvalidToken() throws HodErrorException {
        final AuthenticationToken userUnboundToken = authenticationService.authenticateUserUnbound(apiKey).getToken();
        final AuthenticationToken madeUpApplicationToken = new AuthenticationToken(1431357140457L, "InvalidId!!!", "InvalidSecret!", "InvalidType", 1431357140457L);

        HodErrorCode errorCode = null;

        try {
            authenticationService.combineTokens(madeUpApplicationToken, userUnboundToken, APPLICATION_NAME, DOMAIN_NAME, TokenType.simple);
        } catch (final HodErrorException e) {
            errorCode = e.getErrorCode();
        }

        assertThat(errorCode, is(HodErrorCode.AUTHENTICATION_FAILED));
    }
}
