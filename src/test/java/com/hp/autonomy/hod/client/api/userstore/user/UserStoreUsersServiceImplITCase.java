/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.error.HodErrorCode;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

// TODO: Enable UserStoreUsersService tests when the APIs are deployed on preview.havenondemand.com
// TODO: Add more tests for list user in user store when it is deployed
@Ignore
@RunWith(Parameterized.class)
public class UserStoreUsersServiceImplITCase extends AbstractHodClientIntegrationTest {
    private UserStoreUsersService service;

    public UserStoreUsersServiceImplITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        service = new UserStoreUsersServiceImpl(getConfig());
    }

    @Test
    public void listUsersWithoutAccountsOrGroups() throws HodErrorException {
        final List<User<Void>> users = service.list(getTokenProxy(), USER_STORE, false, false);

        for (final User<Void> user : users) {
            assertThat(user.getUuid(), not(nullValue()));
            assertThat(user.getAccounts(), nullValue());
            assertThat(user.getDirectGroups(), nullValue());
            assertThat(user.getGroups(), nullValue());
        }
    }

    @Test
    public void listUsersWithGroups() throws HodErrorException {
        final List<User<Void>> users = service.list(getTokenProxy(), USER_STORE, false, true);

        for (final User<Void> user : users) {
            assertThat(user.getUuid(), not(nullValue()));
            assertThat(user.getAccounts(), not(nullValue()));
            assertThat(user.getDirectGroups(), nullValue());
            assertThat(user.getGroups(), nullValue());
        }
    }

    @Test
    public void listUsersWithAccounts() throws HodErrorException {
        final List<User<Void>> users = service.list(getTokenProxy(), USER_STORE, true, false);

        for (final User<Void> user : users) {
            assertThat(user.getUuid(), not(nullValue()));
            assertThat(user.getAccounts(), nullValue());
            assertThat(user.getDirectGroups(), not(nullValue()));
            assertThat(user.getGroups(), not(nullValue()));
        }
    }

    @Test
    public void listUsersWithAccountsAndGroups() throws HodErrorException {
        final List<User<Void>> users = service.list(getTokenProxy(), USER_STORE, true, true);

        for (final User<Void> user : users) {
            assertThat(user.getUuid(), not(nullValue()));
            assertThat(user.getAccounts(), not(nullValue()));
            assertThat(user.getDirectGroups(), not(nullValue()));
            assertThat(user.getGroups(), not(nullValue()));
        }
    }

    @Test
    public void testResetThrowsWhenUserNotFound() throws MalformedURLException {
        try {
            service.resetAuthentication(
                getTokenProxy(),
                USER_STORE,
                "foo@example.com",
                new URL("http://www.example.com"),
                new URL("http://www.example.com")
            );

            fail("HodErrorException not thrown");
        } catch (final HodErrorException e) {
            assertThat(e.getErrorCode(), is(HodErrorCode.USER_NOT_FOUND));
        }
    }

    @Test
    public void testResetThrowsWhenUserStoreNotFound() throws MalformedURLException {
        try {
            service.resetAuthentication(
                getTokenProxy(),
                new ResourceIdentifier("dsakjhdsakjdsalkj", "dsakjhdsajkdsalkj"),
                "foo@example.com",
                new URL("http://www.example.com"),
                new URL("http://www.example.com")
            );

            fail("HodErrorException not thrown");
        } catch (final HodErrorException e) {
            assertThat(e.getErrorCode(), is(HodErrorCode.STORE_NOT_FOUND));
        }
    }
}