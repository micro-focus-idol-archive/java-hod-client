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

import java.util.List;

import static org.hamcrest.Matchers.*;
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
        authenticationService = getRestAdapter().create(AuthenticationService.class);
        userService = getRestAdapter().create(UserService.class);
    }

    @Test
    public void getsUserDetails() throws HodErrorException {
        final GetUserResponse getUserResponse = userService.getUser(getUserUnboundToken());
        checkSingleUserResponse(getUserResponse);
    }

    @Test
    public void getsCombinedTokenUsers() throws HodErrorException {
        final AuthenticationToken userUnboundToken = getUserUnboundToken();
        final AuthenticationToken applicationUnboundToken = authenticationService.authenticateApplicationUnbound(getApiKey(), APPLICATION_NAME, DOMAIN_NAME).getToken();
        final AuthenticationToken combinedToken = authenticationService.combineTokens(applicationUnboundToken, userUnboundToken, TokenType.simple).getToken();

        final GetUserResponse getUserResponse = userService.getUserCombined(combinedToken);
        checkSingleUserResponse(getUserResponse);
    }

    private void checkSingleUserResponse(final GetUserResponse userResponse) {
        final List<User> users = userResponse.getUsers();
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