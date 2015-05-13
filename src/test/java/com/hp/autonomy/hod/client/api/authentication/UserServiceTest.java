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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
@Slf4j
@Ignore // TODO: Remove this when production has platform version 2 features
public class UserServiceTest extends AbstractHodClientIntegrationTest {
    private AuthenticationService authenticationService;
    private UserService userService;

    public UserServiceTest(final Endpoint endpoint) {
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
        final AuthenticationToken userUnboundToken = authenticationService.authenticateUserUnbound(new ApiKey(endpoint.getApiKey())).getToken();
        final List<User> users = userService.getUser(userUnboundToken).getUsers();
        assertThat(users, hasSize(1));

        final User user = users.get(0);

        final String username = user.getUsername();
        log.debug("Username was {}", username);
        assertThat(username, is(not(nullValue())));

        final String userStore = user.getUserStore();
        log.debug("User store was {}", userStore);
        assertThat(userStore, is(not(nullValue())));
    }
}