/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.hp.autonomy.hod.client.AbstractDeveloperHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.HodErrorTester;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.error.HodErrorCode;
import com.hp.autonomy.hod.client.error.HodErrorException;
import lombok.Data;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.hp.autonomy.hod.client.HodErrorTester.testErrorCode;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class UserStoreUsersServiceImplITCase extends AbstractDeveloperHodClientIntegrationTest {

    private UserStoreUsersService service;
    private UUID developerUserUuid;

    private final Set<HodErrorCode> storeNotFoundErrorCodes = new HashSet<>();
    private final Set<HodErrorCode> userNotFoundErrorCodes = new HashSet<>();

    public UserStoreUsersServiceImplITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        service = new UserStoreUsersServiceImpl(getConfig());

        storeNotFoundErrorCodes.add(HodErrorCode.STORE_NOT_FOUND);
        storeNotFoundErrorCodes.add(HodErrorCode.INSUFFICIENT_PRIVILEGES);

        userNotFoundErrorCodes.add(HodErrorCode.USER_NOT_FOUND);
        userNotFoundErrorCodes.add(HodErrorCode.INSUFFICIENT_PRIVILEGES);

        try {
            final List<User> users = service.list(getTokenProxy(), getUserStore(), true, false);

            for (final User user : users) {
                for (final Account account : user.getAccounts()) {
                    if (Account.Type.DEVELOPER.equals(account.getType()) && getDeveloperUuid().toString().equals(account.getAccount())) {
                        developerUserUuid = user.getUuid();
                    }
                }
            }

            if (developerUserUuid == null) {
                throw new IllegalStateException("No user is associated with the test developer in the test user store");
            }
        } catch (final HodErrorException e) {
            throw new IllegalStateException("HOD returned an error when trying to determine the UUID of the user associated with the test developer", e);
        }
    }

    @After
    public void tearDown() {
        developerUserUuid = null;
    }

    @Test
    public void listUsersWithoutAccountsOrGroups() throws HodErrorException {
        final List<User> users = service.list(getTokenProxy(), getUserStore(), false, false);
        boolean foundDeveloper = false;

        for (final User user : users) {
            assertThat(user.getUuid(), not(nullValue()));
            assertThat(user.getAccounts(), nullValue());
            assertThat(user.getDirectGroups(), nullValue());
            assertThat(user.getGroups(), nullValue());
            assertThat(user.getMetadata(), nullValue());

            if (user.getUuid().equals(developerUserUuid)) {
                foundDeveloper = true;
            }
        }

        assertTrue("User associated with test developer not found", foundDeveloper);
    }

    @Test
    public void listUsersWithAccounts() throws HodErrorException {
        final List<User> users = service.list(getTokenProxy(), getUserStore(), true, false);
        boolean foundDeveloper = false;
        boolean foundDeveloperAccount = false;

        for (final User user : users) {
            assertThat(user.getUuid(), not(nullValue()));
            assertThat(user.getAccounts(), not(nullValue()));
            assertThat(user.getDirectGroups(), nullValue());
            assertThat(user.getGroups(), nullValue());
            assertThat(user.getMetadata(), nullValue());

            for (final Account account : user.getAccounts()) {
                assertThat(account.getAccount(), not(nullValue()));
                assertThat(account.getStatus(), not(nullValue()));
                assertThat(account.getType(), not(nullValue()));
            }

            if (user.getUuid().equals(developerUserUuid)) {
                foundDeveloper = true;

                for (final Account account : user.getAccounts()) {
                    if (Account.Type.DEVELOPER.equals(account.getType()) && getDeveloperUuid().toString().equals(account.getAccount())) {
                        foundDeveloperAccount = true;
                    }
                }
            }
        }

        assertTrue("User associated with test developer not found", foundDeveloper);
        assertTrue("Account associated with test developer not found", foundDeveloperAccount);
    }

    @Test
    public void listUsersWithGroups() throws HodErrorException {
        final List<User> users = service.list(getTokenProxy(), getUserStore(), false, true);
        boolean foundDeveloper = false;

        for (final User user : users) {
            assertThat(user.getUuid(), not(nullValue()));
            assertThat(user.getAccounts(), nullValue());
            assertThat(user.getDirectGroups(), not(nullValue()));
            assertThat(user.getGroups(), not(nullValue()));
            assertThat(user.getMetadata(), nullValue());

            for (final String name : user.getDirectGroups()) {
                assertThat(name, not(nullValue()));
            }

            for (final String name : user.getGroups()) {
                assertThat(name, not(nullValue()));
            }

            if (user.getUuid().equals(developerUserUuid)) {
                foundDeveloper = true;
            }
        }

        assertTrue("User associated with test developer not found", foundDeveloper);
    }

    @Test
    public void listUsersWithAccountsAndGroups() throws HodErrorException {
        final List<User> users = service.list(getTokenProxy(), getUserStore(), true, true);
        boolean foundDeveloper = false;
        boolean foundDeveloperAccount = false;

        for (final User user : users) {
            assertThat(user.getUuid(), not(nullValue()));
            assertThat(user.getAccounts(), not(nullValue()));
            assertThat(user.getDirectGroups(), not(nullValue()));
            assertThat(user.getGroups(), not(nullValue()));
            assertThat(user.getMetadata(), nullValue());

            for (final String name : user.getDirectGroups()) {
                assertThat(name, not(nullValue()));
            }

            for (final String name : user.getGroups()) {
                assertThat(name, not(nullValue()));
            }

            for (final Account account : user.getAccounts()) {
                assertThat(account.getAccount(), not(nullValue()));
                assertThat(account.getStatus(), not(nullValue()));
                assertThat(account.getType(), not(nullValue()));
            }

            if (user.getUuid().equals(developerUserUuid)) {
                foundDeveloper = true;

                for (final Account account : user.getAccounts()) {
                    if (Account.Type.DEVELOPER.equals(account.getType()) && getDeveloperUuid().toString().equals(account.getAccount())) {
                        foundDeveloperAccount = true;
                    }
                }
            }
        }

        assertTrue("User associated with test developer not found", foundDeveloper);
        assertTrue("Account associated with test developer not found", foundDeveloperAccount);
    }

    @Test
    public void createAndDeleteUser() throws HodErrorException, MalformedURLException {
        final URL testUrl = new URL("http://www.example.com");

        final String userEmail = UUID.randomUUID() + "@example.com";

        service.create(getTokenProxy(), getUserStore(), userEmail, testUrl, testUrl, null);

        final UUID userUuid = getUserUuidFromEmail(userEmail);

        assertThat(userUuid, not(nullValue()));

        service.delete(getTokenProxy(), getUserStore(), userUuid);

        assertThat(getUserUuidFromEmail(userEmail), nullValue());
    }

    @Test
    public void createAndDeleteUserWithMessageAndMetadata() throws HodErrorException, MalformedURLException {
        final URL testUrl = new URL("http://www.example.com");

        final Map<String, TestMetadata> metadata = new HashMap<>();
        metadata.put("my-metadata", new TestMetadata(54, "fred"));

        final CreateUserRequestBuilder builder = new CreateUserRequestBuilder()
                .setMetadata(metadata)
                .setUserMessage("Welcome to My Super Cool App!");

        final String userEmail = UUID.randomUUID() + "@example.com";
        service.create(getTokenProxy(), getUserStore(), userEmail, testUrl, testUrl, builder);

        final UUID uuid = getUserUuidFromEmail(userEmail);
        service.delete(getTokenProxy(), getUserStore(), uuid);
    }

    @Test
    public void deleteNonExistentUser() throws HodErrorException {
        testErrorCode(HodErrorCode.USER_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.delete(getTokenProxy(), getUserStore(), new UUID(0, 0));
            }
        });
    }

    @Test
    public void testResetThrowsWhenUserNotFound() throws MalformedURLException {
        final URL testUrl = new URL("http://www.example.com");

        testErrorCode(HodErrorCode.USER_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.resetAuthentication(getTokenProxy(), getUserStore(), UUID.randomUUID(), testUrl, testUrl);
            }
        });
    }

    @Test
    public void testResetThrowsWhenUserStoreNotFound() throws MalformedURLException {
        final URL testUrl = new URL("http://www.example.com");

        testErrorCode(storeNotFoundErrorCodes, new HodErrorTester.HodExceptionRunnable() {
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

    @Test
    public void listUserGroupsThrowsWithNonExistentUser() {
        testErrorCode(HodErrorCode.USER_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.listUserGroups(
                        getTokenProxy(),
                        getUserStore(),
                        UUID.randomUUID()
                );
            }
        });
    }

    @Test
    public void listUserGroupsThrowsWithNonExistentUserStore() {
        testErrorCode(storeNotFoundErrorCodes, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.listUserGroups(
                        getTokenProxy(),
                        new ResourceIdentifier(getEndpoint().getDomainName(), "notarealuserstorereally"),
                        UUID.randomUUID()
                );
            }
        });
    }

    @Test
    public void getUserMetadataThrowsWithNonExistentUser() {
        testErrorCode(HodErrorCode.USER_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.getUserMetadata(
                        getTokenProxy(),
                        getUserStore(),
                        UUID.randomUUID()
                );
            }
        });
    }

    @Test
    public void getUserMetadataThrowsWithNonExistentUserStore() {
        testErrorCode(storeNotFoundErrorCodes, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.getUserMetadata(
                        getTokenProxy(),
                        new ResourceIdentifier(getEndpoint().getDomainName(), "notarealuserstoreIhope"),
                        UUID.randomUUID()
                );
            }
        });
    }

    @Test
    public void addUserMetadataThrowsWithNonExistentUser() {
        testErrorCode(userNotFoundErrorCodes, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.addUserMetadata(
                        getTokenProxy(),
                        getUserStore(),
                        UUID.randomUUID(),
                        new HashMap<String, Object>()
                );
            }
        });
    }

    @Test
    public void addUserMetadataThrowsWithNonExistentUserStore() {
        testErrorCode(storeNotFoundErrorCodes, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.addUserMetadata(
                        getTokenProxy(),
                        new ResourceIdentifier(getEndpoint().getDomainName(), "notarealuserstoreIhope"),
                        UUID.randomUUID(),
                        new HashMap<String, Object>()
                );
            }
        });
    }

    @Test
    public void removeUserMetadataThrowsWithNonExistentUser() {
        testErrorCode(HodErrorCode.USER_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.removeUserMetadata(
                        getTokenProxy(),
                        getUserStore(),
                        UUID.randomUUID(),
                        "metakey"
                );
            }
        });
    }

    @Test
    public void removeUserMetadataThrowsWithNonExistentUserStore() {
        testErrorCode(storeNotFoundErrorCodes, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.removeUserMetadata(
                        getTokenProxy(),
                        new ResourceIdentifier(getEndpoint().getDomainName(), "notarealuserstoreIhope"),
                        UUID.randomUUID(),
                        "metakey"
                );
            }
        });
    }

    @Test
    public void addGetRemoveMetadatum() throws HodErrorException {
        final String key = randomString();

        final Map<String, TestMetadata> metadata = new HashMap<>();
        final TestMetadata testMetadata = new TestMetadata(7, "bobby");
        metadata.put(key, testMetadata);

        final int metadataSize = service.getUserMetadata(getTokenProxy(), getUserStore(), developerUserUuid).size();

        service.addUserMetadata(getTokenProxy(), getUserStore(), developerUserUuid, metadata);
        final int metadataSizeAfterAdd = service.getUserMetadata(getTokenProxy(), getUserStore(), developerUserUuid).size();
        assertEquals(metadataSize + 1, metadataSizeAfterAdd);

        service.removeUserMetadata(getTokenProxy(), getUserStore(), developerUserUuid, key);
        final int metadataSizeAfterRemove = service.getUserMetadata(getTokenProxy(), getUserStore(), developerUserUuid).size();
        assertEquals(metadataSize, metadataSizeAfterRemove);
    }


    @Test
    public void addGetRemoveMetadata() throws HodErrorException {
        final int metadataStartSize = service.getUserMetadata(getTokenProxy(), getUserStore(), developerUserUuid).size();

        final String integerKey = randomString();
        final String stringKey = randomString();
        final String testDataKey = randomString();

        final Integer integer = 3;
        final String string = "myString";
        final TestMetadata testMetadata = new TestMetadata(13, "penny");

        final Map<String, Object> metadata = new HashMap<>();
        metadata.put(integerKey, integer);
        metadata.put(stringKey, string);
        metadata.put(testDataKey, testMetadata);

        // Add two metadata keys
        service.addUserMetadata(getTokenProxy(), getUserStore(), developerUserUuid, metadata);

        // Check the correct number of metadata values were added
        final Map<String, JsonNode> outputMetadata = service.getUserMetadata(getTokenProxy(), getUserStore(), developerUserUuid);
        assertEquals(metadataStartSize + 3, outputMetadata.size());

        // Remove one of the keys
        service.removeUserMetadata(getTokenProxy(), getUserStore(), developerUserUuid, integerKey);

        // Check the correct key was removed
        final Map<String, JsonNode> outputMetadata2 = service.getUserMetadata(getTokenProxy(), getUserStore(), developerUserUuid);
        assertEquals(metadataStartSize + 2, outputMetadata2.size());

        // Remove all added keys
        service.removeUserMetadata(getTokenProxy(), getUserStore(), developerUserUuid, stringKey);
        service.removeUserMetadata(getTokenProxy(), getUserStore(), developerUserUuid, testDataKey);

        // Check all keys were removed
        final Map<String, JsonNode> outputMetadata3 = service.getUserMetadata(getTokenProxy(), getUserStore(), developerUserUuid);
        assertEquals(metadataStartSize, outputMetadata3.size());
    }

    @Test
    public void addUserMetadataAndList() throws HodErrorException {
        final int metadataStartSize = service.getUserMetadata(getTokenProxy(), getUserStore(), developerUserUuid).size();
        final String key = randomString();

        final Map<String, Object> metadata = new HashMap<>();
        final TestMetadata testMetadata = new TestMetadata(7, "bobby");
        metadata.put(key, testMetadata);

        service.addUserMetadata(getTokenProxy(), getUserStore(), developerUserUuid, metadata);

        final List<User> users = service.listWithMetadata(getTokenProxy(), getUserStore(), false, false);
        boolean foundUser = false;

        for (final User user : users) {
            assertThat(user.getUuid(), not(nullValue()));
            assertThat(user.getAccounts(), nullValue());
            assertThat(user.getDirectGroups(), nullValue());
            assertThat(user.getGroups(), nullValue());

            final List<Metadata<JsonNode>> userMetadata = user.getMetadata();

            if (user.getUuid().equals(developerUserUuid)) {
                foundUser = true;
                assertEquals(metadataStartSize + 1, userMetadata.size());
            }
        }

        assertTrue("User associated with test API key not found", foundUser);

        service.removeUserMetadata(getTokenProxy(), getUserStore(), developerUserUuid, key);
    }

    @Test
    public void addUserMetadataAndListWithAccountsAndGroups() throws HodErrorException {
        final int metadataStartSize = service.getUserMetadata(getTokenProxy(), getUserStore(), developerUserUuid).size();
        final String key = randomString();

        final Map<String, TestMetadata> metadata = new HashMap<>();
        final TestMetadata testMetadata = new TestMetadata(7, "bobby");
        metadata.put(key, testMetadata);

        service.addUserMetadata(getTokenProxy(), getUserStore(), developerUserUuid, metadata);

        final List<User> users = service.listWithMetadata(getTokenProxy(), getUserStore(), true, true);
        boolean foundUser = false;

        for (final User user : users) {
            assertThat(user.getUuid(), not(nullValue()));
            assertThat(user.getAccounts(), not(nullValue()));
            assertThat(user.getDirectGroups(), not(nullValue()));
            assertThat(user.getGroups(), not(nullValue()));

            for (final String name : user.getDirectGroups()) {
                assertThat(name, not(nullValue()));
            }

            for (final String name : user.getGroups()) {
                assertThat(name, not(nullValue()));
            }

            for (final Account account : user.getAccounts()) {
                assertThat(account.getAccount(), not(nullValue()));
                assertThat(account.getStatus(), not(nullValue()));
                assertThat(account.getType(), not(nullValue()));
            }

            final List<Metadata<JsonNode>> userMetadata = user.getMetadata();

            if (user.getUuid().equals(developerUserUuid)) {
                foundUser = true;
                assertEquals(metadataStartSize + 1, userMetadata.size());
            }
        }

        assertTrue("User associated with test API key not found", foundUser);

        service.removeUserMetadata(getTokenProxy(), getUserStore(), developerUserUuid, key);
    }

    private String randomString() {
        return "hod-client-" + UUID.randomUUID().toString();
    }

    private UUID getUserUuidFromEmail(final String userEmail) throws HodErrorException {
        final List<User> users = service.list(getTokenProxy(), getUserStore(), true, false);

        for (final User user : users) {
            for (final Account account : user.getAccounts()) {
                if (Account.Type.EMAIL.equals(account.getType()) && userEmail.equals(account.getAccount())) {
                    return user.getUuid();
                }
            }
        }

        return null;
    }

    @Data
    private static class TestMetadata {
        private final int age;
        private final String name;

        private TestMetadata(
                @JsonProperty("age") final int age,
                @JsonProperty("name") final String name
        ) {
            this.age = age;
            this.name = name;
        }
    }
}
