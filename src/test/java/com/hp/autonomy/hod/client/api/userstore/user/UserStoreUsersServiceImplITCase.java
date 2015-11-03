/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.HodErrorTester;
import com.hp.autonomy.hod.client.api.authentication.ApiKey;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationService;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationServiceImpl;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.authentication.tokeninformation.UserTokenInformation;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.error.HodErrorCode;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.hp.autonomy.hod.client.HodErrorTester.testErrorCode;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class UserStoreUsersServiceImplITCase extends AbstractHodClientIntegrationTest {
    private final ApiKey apiKey;

    private UUID userUuid;
    private UserStoreUsersService service;

    public UserStoreUsersServiceImplITCase(final Endpoint endpoint) {
        super(endpoint);
        apiKey = endpoint.getApiKey();
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        service = new UserStoreUsersServiceImpl(getConfig());

        final AuthenticationService authenticationService = new AuthenticationServiceImpl(getConfig());

        try {
            // This assumes that the API key is associated with a valid user
            final TokenProxy<EntityType.User, TokenType.Simple> tokenProxy = authenticationService.authenticateUser(apiKey, APPLICATION_NAME, DOMAIN_NAME, TokenType.Simple.INSTANCE);
            final UserTokenInformation information = authenticationService.getUserTokenInformation(tokenProxy);
            userUuid = information.getUser().getUuid();
        } catch (final HodErrorException e) {
            throw new AssertionError("Failed to determine user UUID", e);
        }
    }

    @Test
    public void listUsersWithoutAccountsOrGroups() throws HodErrorException {
        final List<User<Void>> users = service.list(getTokenProxy(), USER_STORE, false, false);
        boolean foundUser = false;

        for (final User<Void> user : users) {
            assertThat(user.getUuid(), not(nullValue()));
            assertThat(user.getAccounts(), nullValue());
            assertThat(user.getDirectGroups(), nullValue());
            assertThat(user.getGroups(), nullValue());
            assertThat(user.getMetadata(), nullValue());

            if (user.getUuid().equals(userUuid)) {
                foundUser = true;
            }
        }

        assertTrue("User associated with test API key not found", foundUser);
    }

    @Test
    public void listUsersWithAccounts() throws HodErrorException {
        final List<User<Void>> users = service.list(getTokenProxy(), USER_STORE, true, false);
        boolean foundUser = false;

        for (final User<Void> user : users) {
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

            if (user.getUuid().equals(userUuid)) {
                foundUser = true;
            }
        }

        assertTrue("User associated with test API key not found", foundUser);
    }

    @Test
    public void listUsersWithGroups() throws HodErrorException {
        final List<User<Void>> users = service.list(getTokenProxy(), USER_STORE, false, true);
        boolean foundUser = false;

        for (final User<Void> user : users) {
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

            if (user.getUuid().equals(userUuid)) {
                foundUser = true;
            }
        }

        assertTrue("User associated with test API key not found", foundUser);
    }

    @Test
    public void listUsersWithAccountsAndGroups() throws HodErrorException {
        final List<User<Void>> users = service.list(getTokenProxy(), USER_STORE, true, true);
        boolean foundUser = false;

        for (final User<Void> user : users) {
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

            if (user.getUuid().equals(userUuid)) {
                foundUser = true;
            }
        }

        assertTrue("User associated with test API key not found", foundUser);
    }

    @Test
    public void createAndDeleteUser() throws HodErrorException, MalformedURLException {
        final URL testUrl = new URL("http://www.example.com");

        final String userEmail = UUID.randomUUID() + "@example.com";

        service.create(getTokenProxy(), USER_STORE, userEmail, testUrl, testUrl, null);

        final UUID userUuid = getUserUuidFromEmail(userEmail);

        assertThat(userUuid, not(nullValue()));

        service.delete(getTokenProxy(), USER_STORE, userUuid);

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
        service.create(getTokenProxy(), USER_STORE, userEmail, testUrl, testUrl, builder);

        final UUID uuid = getUserUuidFromEmail(userEmail);
        service.delete(getTokenProxy(), USER_STORE, uuid);
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

    @Test
    public void listUserGroupsThrowsWithNonExistentUser() {
        testErrorCode(HodErrorCode.USER_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.listUserGroups(
                        getTokenProxy(),
                        USER_STORE,
                        UUID.randomUUID()
                );
            }
        });
    }

    @Test
    public void listUserGroupsThrowsWithNonExistentUserStore() {
        testErrorCode(HodErrorCode.STORE_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.listUserGroups(
                        getTokenProxy(),
                        new ResourceIdentifier(DOMAIN_NAME, "notarealuserstorereally"),
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
                        USER_STORE,
                        UUID.randomUUID(),
                        new HashMap<String, Class<?>>()
                );
            }
        });
    }

    @Test
    public void getUserMetadataThrowsWithNonExistentUserStore() {
        testErrorCode(HodErrorCode.STORE_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.getUserMetadata(
                        getTokenProxy(),
                        new ResourceIdentifier(DOMAIN_NAME, "notarealuserstoreIhope"),
                        UUID.randomUUID(),
                        new HashMap<String, Class<?>>()
                );
            }
        });
    }

    @Test
    public void addUserMetadataThrowsWithNonExistentUser() {
        testErrorCode(HodErrorCode.USER_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.addUserMetadata(
                        getTokenProxy(),
                        USER_STORE,
                        UUID.randomUUID(),
                        new HashMap<String, Object>()
                );
            }
        });
    }

    @Test
    public void addUserMetadataThrowsWithNonExistentUserStore() {
        testErrorCode(HodErrorCode.STORE_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.addUserMetadata(
                        getTokenProxy(),
                        new ResourceIdentifier(DOMAIN_NAME, "notarealuserstoreIhope"),
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
                        USER_STORE,
                        UUID.randomUUID(),
                        "metakey"
                );
            }
        });
    }

    @Test
    public void removeUserMetadataThrowsWithNonExistentUserStore() {
        testErrorCode(HodErrorCode.STORE_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.removeUserMetadata(
                        getTokenProxy(),
                        new ResourceIdentifier(DOMAIN_NAME, "notarealuserstoreIhope"),
                        UUID.randomUUID(),
                        "metakey"
                );
            }
        });
    }

    @Test
    public void getEmptyMetadata() throws HodErrorException {
        final Map<String, Class<?>> metadataTypes = new HashMap<>();
        metadataTypes.put(randomString(), TestMetadata.class);

        final Map<String, Object> userMetadata = service.getUserMetadata(getTokenProxy(), USER_STORE, userUuid, metadataTypes);
        assertThat(userMetadata, is(anEmptyMap()));
    }

    @Test
    public void addGetRemoveMetadatum() throws HodErrorException {
        final String key = randomString();

        final Map<String, TestMetadata> metadata = new HashMap<>();
        final TestMetadata testMetadata = new TestMetadata(7, "bobby");
        metadata.put(key, testMetadata);

        final Map<String, Class<? extends TestMetadata>> metadataTypes = new HashMap<>();
        metadataTypes.put(key, TestMetadata.class);

        service.addUserMetadata(getTokenProxy(), USER_STORE, userUuid, metadata);

        final Map<String, TestMetadata> outputMetadata = service.getUserMetadata(getTokenProxy(), USER_STORE, userUuid, metadataTypes);
        assertThat(outputMetadata, is(aMapWithSize(1)));

        final TestMetadata outputTestMetadata = outputMetadata.get(key);
        assertThat(outputTestMetadata, is(testMetadata));

        service.removeUserMetadata(getTokenProxy(), USER_STORE, userUuid, key);

        final Map<String, TestMetadata> outputMetadata2 = service.getUserMetadata(getTokenProxy(), USER_STORE, userUuid, metadataTypes);
        assertThat(outputMetadata2, is(anEmptyMap()));
    }

    @Test
    public void addGetRemoveMetadata() throws HodErrorException {
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

        final Map<String, Class<?>> metadataTypes = new HashMap<>();
        metadataTypes.put(integerKey, Integer.class);
        metadataTypes.put(stringKey, String.class);
        metadataTypes.put(testDataKey, TestMetadata.class);

        // Add three metadata keys
        service.addUserMetadata(getTokenProxy(), USER_STORE, userUuid, metadata);

        // Check the correct metadata values were associated with the keys
        final Map<String, Object> outputMetadata = service.getUserMetadata(getTokenProxy(), USER_STORE, userUuid, metadataTypes);
        assertThat(outputMetadata, is(aMapWithSize(3)));

        assertThat((Integer) outputMetadata.get(integerKey), is(integer));
        assertThat((String) outputMetadata.get(stringKey), is(string));
        assertThat((TestMetadata) outputMetadata.get(testDataKey), is(testMetadata));

        // Remove one of the keys
        service.removeUserMetadata(getTokenProxy(), USER_STORE, userUuid, integerKey);

        // Check the correct key was removed
        final Map<String, Object> outputMetadata2 = service.getUserMetadata(getTokenProxy(), USER_STORE, userUuid, metadataTypes);
        assertThat(outputMetadata2, is(aMapWithSize(2)));

        assertThat((String) outputMetadata.get(stringKey), is(string));
        assertThat((TestMetadata) outputMetadata.get(testDataKey), is(testMetadata));

        // Remove all added keys
        service.removeUserMetadata(getTokenProxy(), USER_STORE, userUuid, stringKey);
        service.removeUserMetadata(getTokenProxy(), USER_STORE, userUuid, testDataKey);

        // Check all keys were removed
        final Map<String, Object> outputMetadata3 = service.getUserMetadata(getTokenProxy(), USER_STORE, userUuid, metadataTypes);
        assertThat(outputMetadata3, is(anEmptyMap()));
    }

    @Test
    public void addUserMetadataAndList() throws HodErrorException {
        final String key = randomString();

        final Map<String, Object> metadata = new HashMap<>();
        final TestMetadata testMetadata = new TestMetadata(7, "bobby");
        metadata.put(key, testMetadata);

        final Map<String, Class<?>> metadataTypes = new HashMap<>();
        metadataTypes.put(key, TestMetadata.class);

        service.addUserMetadata(getTokenProxy(), USER_STORE, userUuid, metadata);

        final List<User<Object>> users = service.listWithMetadata(getTokenProxy(), USER_STORE, metadataTypes, false, false);
        boolean foundUser = false;

        for (final User<Object> user : users) {
            assertThat(user.getUuid(), not(nullValue()));
            assertThat(user.getAccounts(), nullValue());
            assertThat(user.getDirectGroups(), nullValue());
            assertThat(user.getGroups(), nullValue());

            final Map<String, Object> userMetadata = user.getMetadata();

            if (user.getUuid().equals(userUuid)) {
                foundUser = true;
                assertThat(userMetadata, is(metadata));
            } else {
                assertThat(userMetadata, is(anEmptyMap()));
            }
        }

        assertTrue("User associated with test API key not found", foundUser);

        service.removeUserMetadata(getTokenProxy(), USER_STORE, userUuid, key);
    }

    @Test
    public void addUserMetadataAndListWithAccountsAndGroups() throws HodErrorException {
        final String key = randomString();

        final Map<String, TestMetadata> metadata = new HashMap<>();
        final TestMetadata testMetadata = new TestMetadata(7, "bobby");
        metadata.put(key, testMetadata);

        final Map<String, Class<? extends TestMetadata>> metadataTypes = new HashMap<>();
        metadataTypes.put(key, TestMetadata.class);

        service.addUserMetadata(getTokenProxy(), USER_STORE, userUuid, metadata);

        final List<User<TestMetadata>> users = service.listWithMetadata(getTokenProxy(), USER_STORE, metadataTypes, true, true);
        boolean foundUser = false;

        for (final User<TestMetadata> user : users) {
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

            final Map<String, TestMetadata> userMetadata = user.getMetadata();

            if (user.getUuid().equals(userUuid)) {
                foundUser = true;
                assertThat(userMetadata, is(metadata));
            } else {
                assertThat(userMetadata, is(anEmptyMap()));
            }
        }

        assertTrue("User associated with test API key not found", foundUser);

        service.removeUserMetadata(getTokenProxy(), USER_STORE, userUuid, key);
    }

    @Test
    public void getUserMetadataIgnoresIncorrectMetadataType() throws HodErrorException {
        final String key = randomString();
        final Integer value = 5;

        final Map<String, Object> metadata = new HashMap<>();
        metadata.put(key, value);

        final Map<String, Class<?>> incorrectMetadataTypes = new HashMap<>();
        incorrectMetadataTypes.put(key, TestMetadata.class);

        service.addUserMetadata(getTokenProxy(), USER_STORE, userUuid, metadata);

        final Map<String, Object> outputMetadata = service.getUserMetadata(getTokenProxy(), USER_STORE, userUuid, incorrectMetadataTypes);
        assertThat(outputMetadata, is(anEmptyMap()));

        service.removeUserMetadata(getTokenProxy(), USER_STORE, userUuid, key);
    }

    @Test
    public void getUserMetadataIgnoresMissingMetadataKey() throws HodErrorException {
        final Map<String, Class<?>> missingMetadataTypes = new HashMap<>();
        missingMetadataTypes.put(randomString(), Integer.class);

        final Map<String, Object> outputMetadata = service.getUserMetadata(getTokenProxy(), USER_STORE, userUuid, missingMetadataTypes);
        assertThat(outputMetadata, is(anEmptyMap()));
    }

    private String randomString() {
        return "hod-client-" + UUID.randomUUID().toString();
    }

    private UUID getUserUuidFromEmail(final String userEmail) throws HodErrorException {
        final List<User<Void>> users = service.list(getTokenProxy(), USER_STORE, true, false);

        for (final User<Void> user : users) {
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
