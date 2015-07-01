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
    public void unboundAuthentication() throws HodErrorException {
        final AuthenticationToken token = authenticationService.authenticateUnbound(apiKey);
        assertThat(token, is(notNullValue()));
    }

    @Test
    public void failsWithInvalidApiKey() {
        HodErrorCode errorCode = null;

        try {
            authenticationService.authenticateUnbound(new ApiKey("PROBABLY_NOT_A_REAL_API_KEY"));
        } catch (final HodErrorException e) {
            errorCode = e.getErrorCode();
        }

        assertThat(errorCode, is(HodErrorCode.AUTHENTICATION_FAILED));
    }
}
