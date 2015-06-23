package com.hp.autonomy.hod.client.api.authentication;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
@Slf4j
public class UserServiceITCase extends AbstractHodClientIntegrationTest {
    private AuthenticationService authenticationService;
    private UserService userService;

    public UserServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        authenticationService = new AuthenticationServiceImpl(getConfig());
        userService = new UserServiceImpl(getConfig());
    }

    @Test
    public void getsUserDetails() throws HodErrorException {
        checkSingleUserResponse(userService.getUser(getUserUnboundToken()));
    }

    @Test
    public void getsCombinedTokenUsers() throws HodErrorException {
        final AuthenticationToken userUnboundToken = getUserUnboundToken();
        final AuthenticationToken applicationUnboundToken = authenticationService.authenticateApplicationUnbound(getApiKey()).getToken();
        final TokenProxy combinedToken = authenticationService.combineTokens(applicationUnboundToken, userUnboundToken, APPLICATION_NAME, DOMAIN_NAME, TokenType.simple);

        checkSingleUserResponse(userService.getUser(combinedToken));
    }

    private void checkSingleUserResponse(final List<User> users) {
        assertThat(users, hasSize(1));

        final User user = users.get(0);

        final String username = user.getUsername();
        assertThat(username, is(not(nullValue())));
        log.debug("Username was {}", username);

        final String userStore = user.getUserStore();
        assertThat(userStore, is(not(nullValue())));
        log.debug("User store was {}", userStore);
    }

    private AuthenticationToken getUserUnboundToken() throws HodErrorException {
        return authenticationService.authenticateUserUnbound(getApiKey()).getToken();
    }

    private ApiKey getApiKey() {
        return new ApiKey(System.getProperty("hp.dev.placeholder.hod.apiKey"));
    }
}