package com.hp.autonomy.hod.client.api.authentication;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
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

    public AuthenticationServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();

        authenticationService = getRestAdapter().create(AuthenticationService.class);
    }

    @Test
    public void testCombinedAuthentication() throws HodErrorException {
        final AuthenticationToken applicationUnboundToken = authenticationService.authenticateApplicationUnbound(
            new ApiKey(endpoint.getApiKey()),
            "IOD-TEST-APPLICATION",
            "IOD-TEST-DOMAIN"
        ).getToken();

        final AuthenticationToken userUnboundToken = authenticationService.authenticateUserUnbound(new ApiKey(endpoint.getApiKey())).getToken();

        final AuthenticationToken combinedToken = authenticationService.combineTokens(applicationUnboundToken, userUnboundToken, TokenType.simple).getToken();

        log.debug("Combined token was: {}", combinedToken);

        assertThat(combinedToken, is(notNullValue()));
    }
}
