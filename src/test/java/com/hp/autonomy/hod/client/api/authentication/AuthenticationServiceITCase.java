package com.hp.autonomy.hod.client.api.authentication;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.error.HodError;
import com.hp.autonomy.hod.client.error.HodErrorCode;
import com.hp.autonomy.hod.client.error.HodErrorException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
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
@Ignore // TODO: remove this when production has the features
public class AuthenticationServiceITCase extends AbstractHodClientIntegrationTest {
    private AuthenticationService authenticationService;
    private ApiKey apiKey;

    public AuthenticationServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        apiKey = new ApiKey(endpoint.getApiKey());
        authenticationService = getRestAdapter().create(AuthenticationService.class);
    }

    @Test
    public void testAuthenticateApplication() throws HodErrorException {
        final AuthenticationToken token = authenticationService.authenticateApplication(
            new ApiKey(endpoint.getApiKey()),
            "IOD-TEST-APPLICATION",
            "IOD-TEST-DOMAIN",
            TokenType.simple
        ).getToken();

        log.debug("Token was: {}", token);

        assertThat(token, is(notNullValue()));
    }

    @Test
    public void testCombinedAuthentication() throws HodErrorException {
        final AuthenticationToken applicationUnboundToken = authenticationService.authenticateApplicationUnbound(
            apiKey,
            "IOD-TEST-APPLICATION",
            "IOD-TEST-DOMAIN"
        ).getToken();

        final AuthenticationToken userUnboundToken = authenticationService.authenticateUserUnbound(new ApiKey(endpoint.getApiKey())).getToken();

        final AuthenticationToken combinedToken = authenticationService.combineTokens(applicationUnboundToken, userUnboundToken, TokenType.simple).getToken();

        log.debug("Combined token was: {}", combinedToken);

        assertThat(combinedToken, is(notNullValue()));
    }

    @Test
    public void failsWithInvalidToken() throws HodErrorException {
        final AuthenticationToken userUnboundToken = authenticationService.authenticateUserUnbound(apiKey).getToken();
        final AuthenticationToken madeUpApplicationToken = new AuthenticationToken(1431357140457L, "InvalidId!!!", "InvalidSecret!", "InvalidType", 1431357140457L);

        HodErrorCode errorCode = null;

        try {
            authenticationService.combineTokens(madeUpApplicationToken, userUnboundToken, TokenType.simple);
        } catch (final HodErrorException e) {
            errorCode = e.getErrorCode();
        }

        assertThat(errorCode, is(HodErrorCode.INVALID_TOKEN));
    }
}
