/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.HodErrorTester;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.error.HodErrorCode;
import com.hp.autonomy.hod.client.error.HodErrorException;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import static com.hp.autonomy.hod.client.HodErrorTester.testErrorCode;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

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
    public void createUser() throws HodErrorException, MalformedURLException {
        final URL testUrl = new URL("http://www.example.com");

        service.create(getTokenProxy(), USER_STORE, UUID.randomUUID() + "@example.com", testUrl, testUrl, null);
    }

    @Test
    public void createUserWithMessageAndMetadata() throws HodErrorException, MalformedURLException {
        final URL testUrl = new URL("http://www.example.com");

        final CreateUserRequestBuilder builder = new CreateUserRequestBuilder()
                .setMetadata(new TestMetadata(54))
                .setUserMessage("Welcome to My Super Cool App!");

        service.create(getTokenProxy(), USER_STORE, UUID.randomUUID() + "@example.com", testUrl, testUrl, builder);
    }

    @Test
    public void deleteNonExistentUser() throws HodErrorException {
        testErrorCode(HodErrorCode.USER_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.delete(getTokenProxy(), USER_STORE, new UUID(0, 0));
            }
        });
    }

    @Test
    public void testResetThrowsWhenUserNotFound() throws MalformedURLException {
        final URL testUrl = new URL("http://www.example.com");

        testErrorCode(HodErrorCode.USER_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.resetAuthentication(getTokenProxy(), USER_STORE, UUID.randomUUID(), testUrl, testUrl);
            }
        });
    }

    @Test
    public void testResetThrowsWhenUserStoreNotFound() throws MalformedURLException {
        final URL testUrl = new URL("http://www.example.com");

        testErrorCode(HodErrorCode.STORE_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.resetAuthentication(
                    getTokenProxy(),
                    new ResourceIdentifier("dsakjhdsakjdsalkj", "dsakjhdsajkdsalkj"),
                    UUID.randomUUID(),
                    testUrl,
                    testUrl
                );
            }
        });
    }

    @Data
    private static class TestMetadata {
        private final int age;

        private TestMetadata(final int age) {
            this.age = age;
        }
    }
}