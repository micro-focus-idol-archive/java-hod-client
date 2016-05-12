/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.group;

import com.hp.autonomy.hod.client.AbstractDeveloperHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.HodErrorTester;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.api.userstore.user.Account;
import com.hp.autonomy.hod.client.api.userstore.user.User;
import com.hp.autonomy.hod.client.api.userstore.user.UserGroups;
import com.hp.autonomy.hod.client.api.userstore.user.UserStoreUsersService;
import com.hp.autonomy.hod.client.api.userstore.user.UserStoreUsersServiceImpl;
import com.hp.autonomy.hod.client.error.HodErrorCode;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static com.hp.autonomy.hod.client.HodErrorTester.testErrorCode;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class GroupsServiceImplITCase extends AbstractDeveloperHodClientIntegrationTest {
    private static final EnumSet<HodErrorCode> GROUP_OR_USER_NOT_FOUND = EnumSet.of(HodErrorCode.GROUP_NOT_FOUND, HodErrorCode.USER_NOT_FOUND);

    private UUID developerUserUuid;
    private GroupsService service;
    private List<String> createdGroups;
    private UserStoreUsersService userStoreUsersService;

    public GroupsServiceImplITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Before
    @Override
    public void setUp() {
        super.setUp();
        createdGroups = new LinkedList<>();
        service = new GroupsServiceImpl(getConfig());
        userStoreUsersService = new UserStoreUsersServiceImpl(getConfig());

        try {
            final List<User<Void>> users = userStoreUsersService.list(getTokenProxy(), getUserStore(), true, false);

            for (final User<Void> user : users) {
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
    public void tearDown() throws HodErrorException {
        for (final String name : createdGroups) {
            service.delete(getTokenProxy(), getUserStore(), name);
        }
    }

    @Test
    public void list() throws HodErrorException {
        // All we can currently say about the pre-existing groups is that their properties are not null since we don't
        // control the environment
        final List<Group> groups = service.list(getTokenProxy(), getUserStore());
        assertThat(groups, notNullValue());

        for (final Group group : groups) {
            assertThat(group, notNullValue());
            assertThat(group.getName(), notNullValue());
        }
    }

    @Test
    public void create() throws HodErrorException {
        final NameAndResponse nameAndResponse = safeCreateGroup();
        assertThat(nameAndResponse.response.getGroupName(), is(nameAndResponse.name));
        assertThat(nameAndResponse.response.getUserStore(), is(getUserStore()));
    }

    @Test
    public void createAndList() throws HodErrorException {
        final String name = safeCreateGroup().name;
        final List<Group> groups = service.list(getTokenProxy(), getUserStore());

        Group newGroup = null;

        for (final Group group : groups) {
            if (name.equals(group.getName())) {
                newGroup = group;
            }
        }

        assertThat(newGroup.getChildren(), is(empty()));
        assertThat(newGroup.getParents(), is(empty()));
    }

    @Test
    public void createDeleteGetInfoAndList() throws HodErrorException {
        final String name = safeCreateGroup().name;
        safeDeleteGroup(name);

        // Check user has gone from the get info API
        testErrorCode(HodErrorCode.GROUP_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.getInfo(getTokenProxy(), getUserStore(), name);
            }
        });

        // Check user has gone from the list groups API
        final List<Group> groups = service.list(getTokenProxy(), getUserStore());

        for (final Group group : groups) {
            assertFalse("Group was deleted but returned from the list groups API", name.equals(group.getName()));
        }
    }

    @Test
    public void createDuplicate() throws HodErrorException {
        final String groupName = safeCreateGroup().name;

        testErrorCode(HodErrorCode.GROUP_ALREADY_EXISTS, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.create(getTokenProxy(), getUserStore(), groupName);
            }
        });
    }

    @Test
    public void createInNonExistentUserStore() {
        final Set<HodErrorCode> errorCodes = new HashSet<>();
        errorCodes.add(HodErrorCode.STORE_NOT_FOUND);
        errorCodes.add(HodErrorCode.INSUFFICIENT_PRIVILEGES);

        testErrorCode(errorCodes, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.create(getTokenProxy(), new ResourceIdentifier(getEndpoint().getDomainName(), unique()), unique());
            }
        });
    }

    @Test
    public void createAndGetInfo() throws HodErrorException {
        final String groupName = safeCreateGroup().name;

        final GroupInfo info = service.getInfo(getTokenProxy(), getUserStore(), groupName);
        assertThat(info.getName(), is(groupName));
        assertThat(info.getUsers(), is(empty()));
        assertThat(info.getChildren(), is(empty()));
        assertThat(info.getParents(), is(empty()));
    }

    @Test
    public void deleteNonExistent() {
        testErrorCode(HodErrorCode.GROUP_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.delete(getTokenProxy(), getUserStore(), unique());
            }
        });
    }

    @Test
    public void assignNonExistentUser() throws HodErrorException {
        final String groupName = safeCreateGroup().name;

        testErrorCode(HodErrorCode.USER_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.assignUser(getTokenProxy(), getUserStore(), groupName, UUID.randomUUID());
            }
        });
    }

    @Test
    public void removeNonExistentUser() throws HodErrorException {
        final String groupName = safeCreateGroup().name;

        testErrorCode(HodErrorCode.USER_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.removeUser(getTokenProxy(), getUserStore(), groupName, UUID.randomUUID());
            }
        });
    }

    @Test
    public void assignNonExistentUserToNonExistentGroup() {
        testErrorCode(GROUP_OR_USER_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.assignUser(getTokenProxy(), getUserStore(), unique(), UUID.randomUUID());
            }
        });
    }

    @Test
    public void removeNonExistentUserFromNonExistentGroup() {
        testErrorCode(GROUP_OR_USER_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.assignUser(getTokenProxy(), getUserStore(), unique(), UUID.randomUUID());
            }
        });
    }

    @Test
    public void createWithParentAndGetInfo() throws HodErrorException {
        final String parent = safeCreateGroup().name;
        final String child = safeCreateGroup(Collections.singletonList(parent), null).name;
        checkGetInfoParentChildRelationship(parent, child);
    }

    @Test
    public void createWithParentAndList() throws HodErrorException {
        final String parent = safeCreateGroup().name;
        final String child = safeCreateGroup(Collections.singletonList(parent), null).name;
        checkListGroupsParentChildRelationship(child, parent);
    }

    @Test
    public void createWithTwoParentsAndList() throws HodErrorException {
        final String parent1 = safeCreateGroup().name;
        final String parent2 = safeCreateGroup().name;
        final String child = safeCreateGroup(Arrays.asList(parent1, parent2), null).name;

        final List<Group> groups = service.list(getTokenProxy(), getUserStore());
        Group parent1Group = null;
        Group parent2Group = null;
        Group childGroup = null;

        for (final Group group : groups) {
            final String name = group.getName();

            if (parent1.equals(name)) {
                parent1Group = group;
            } else if (parent2.equals(name)) {
                parent2Group = group;
            } else if (child.equals(name)) {
                childGroup = group;
            }
        }

        assertThat(childGroup.getChildren(), is(empty()));
        assertThat(childGroup.getParents(), containsInAnyOrder(parent1, parent2));

        assertThat(parent1Group.getChildren(), contains(child));
        assertThat(parent1Group.getParents(), is(empty()));

        assertThat(parent2Group.getChildren(), contains(child));
        assertThat(parent2Group.getParents(), is(empty()));
    }

    @Test
    public void createWithChildAndGetInfo() throws HodErrorException {
        final String child = safeCreateGroup().name;
        final String parent = safeCreateGroup(null, Collections.singletonList(child)).name;
        checkGetInfoParentChildRelationship(parent, child);
    }

    @Test
    public void createWithChildAndList() throws HodErrorException {
        final String child = safeCreateGroup().name;
        final String parent = safeCreateGroup(null, Collections.singletonList(child)).name;
        checkListGroupsParentChildRelationship(child, parent);
    }

    @Test
    public void deleteParentGroup() throws HodErrorException {
        final String child = safeCreateGroup().name;
        final String parent = safeCreateGroup(null, Collections.singletonList(child)).name;
        safeDeleteGroup(parent);

        final GroupInfo childInfo = service.getInfo(getTokenProxy(), getUserStore(), child);
        assertThat(childInfo.getParents(), is(empty()));
    }

    @Test
    public void linkAndGetInfo() throws HodErrorException {
        final String child = safeCreateGroup().name;
        final String parent = safeCreateGroup().name;

        service.link(getTokenProxy(), getUserStore(), parent, child);

        checkGetInfoParentChildRelationship(parent, child);
    }

    @Test
    public void linkNonExistentParent() throws HodErrorException {
        final String child = safeCreateGroup().name;

        testErrorCode(HodErrorCode.GROUP_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.link(getTokenProxy(), getUserStore(), unique(), child);
            }
        });
    }

    @Test
    public void linkNonExistentChild() throws HodErrorException {
        final String parent = safeCreateGroup().name;

        testErrorCode(HodErrorCode.GROUP_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.link(getTokenProxy(), getUserStore(), parent, unique());
            }
        });
    }

    @Test
    public void unlinkAndGetInfo() throws HodErrorException {
        final String child = safeCreateGroup().name;
        final String parent = safeCreateGroup().name;
        service.link(getTokenProxy(), getUserStore(), parent, child);
        service.unlink(getTokenProxy(), getUserStore(), parent, child);

        final GroupInfo childInfo = service.getInfo(getTokenProxy(), getUserStore(), child);
        final GroupInfo parentInfo = service.getInfo(getTokenProxy(), getUserStore(), parent);

        assertThat(childInfo.getParents(), is(empty()));
        assertThat(parentInfo.getChildren(), is(empty()));
    }

    @Test
    public void unlinkNonExistentParent() throws HodErrorException {
        final String child = safeCreateGroup().name;

        testErrorCode(HodErrorCode.GROUP_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.unlink(getTokenProxy(), getUserStore(), unique(), child);
            }
        });
    }

    @Test
    public void unlinkNonExistentChild() throws HodErrorException {
        final String parent = safeCreateGroup().name;

        testErrorCode(HodErrorCode.GROUP_NOT_FOUND, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.unlink(getTokenProxy(), getUserStore(), parent, unique());
            }
        });
    }

    @Test
    public void assignUserToGroupAndGetInfo() throws HodErrorException {
        final String group = safeCreateGroup().name;

        service.assignUser(getTokenProxy(), getUserStore(), group, developerUserUuid);

        final GroupInfo info = service.getInfo(getTokenProxy(), getUserStore(), group);
        assertThat(info.getUsers(), contains(developerUserUuid));
    }

    @Test
    public void assignAndRemoveUser() throws HodErrorException {
        final String group = safeCreateGroup().name;

        service.assignUser(getTokenProxy(), getUserStore(), group, developerUserUuid);
        service.removeUser(getTokenProxy(), getUserStore(), group, developerUserUuid);

        final GroupInfo info = service.getInfo(getTokenProxy(), getUserStore(), group);
        assertThat(info.getUsers(), is(empty()));
    }

    @Test
    public void assignUserToHierarchyAndListUserGroups() throws HodErrorException {
        final String child = safeCreateGroup().name;
        final String parent = safeCreateGroup().name;

        service.link(getTokenProxy(), getUserStore(), parent, child);
        service.assignUser(getTokenProxy(), getUserStore(), child, developerUserUuid);

        final UserGroups userGroups = userStoreUsersService.listUserGroups(getTokenProxy(), getUserStore(), developerUserUuid);

        assertThat(userGroups.getDirectGroups(), hasItem(child));
        assertThat(userGroups.getDirectGroups(), not(hasItem(parent)));

        assertThat(userGroups.getGroups(), hasItems(child, parent));
    }

    // Use the get info API to check that the "parent" group is the parent of the "child", and that this is the their only relationship
    private void checkGetInfoParentChildRelationship(final String parent, final String child) throws HodErrorException {
        final GroupInfo parentInfo = service.getInfo(getTokenProxy(), getUserStore(), parent);
        final GroupInfo childInfo = service.getInfo(getTokenProxy(), getUserStore(), child);

        assertThat(parentInfo.getChildren(), contains(child));
        assertThat(parentInfo.getParents(), is(empty()));
        assertThat(childInfo.getParents(), contains(parent));
        assertThat(childInfo.getChildren(), is(empty()));
    }

    // Use the list groups API to check that the "parent" group is the parent of the "child", and that this is their only relationship
    private void checkListGroupsParentChildRelationship(final String child, final String parent) throws HodErrorException {
        final List<Group> groups = service.list(getTokenProxy(), getUserStore());

        Group childGroup = null;
        Group parentGroup = null;

        for (final Group group : groups) {
            final String name = group.getName();

            if (parent.equals(name)) {
                parentGroup = group;
            } else if (child.equals(name)) {
                childGroup = group;
            }
        }

        assertThat(childGroup.getParents(), contains(parent));
        assertThat(parentGroup.getChildren(), contains(child));
    }

    // Create a group with a random name, storing it for deletion in tear down
    private NameAndResponse safeCreateGroup() throws HodErrorException {
        final String name = uniqueGroupName();
        final CreateGroupResponse response = service.create(getTokenProxy(), getUserStore(), name);
        createdGroups.add(name);
        return new NameAndResponse(name, response);
    }

    // Create a group with a random name and the given relationships, storing it for deletion in tear down
    private NameAndResponse safeCreateGroup(final List<String> parents, final List<String> children) throws HodErrorException {
        final String name = uniqueGroupName();
        final CreateGroupResponse response = service.createWithHierarchy(getTokenProxy(), getUserStore(), name, parents, children);
        createdGroups.add(name);
        return new NameAndResponse(name, response);
    }

    // Delete a group that was previously created, removing it from the tear down list
    private void safeDeleteGroup(final String name) throws HodErrorException {
        if (!createdGroups.contains(name)) {
            throw new IllegalArgumentException("Attempted to safely delete group which was not created safely");
        }

        service.delete(getTokenProxy(), getUserStore(), name);
        createdGroups.remove(name);
    }

    private String uniqueGroupName() {
        return "java-hod-client-test-" + unique();
    }

    private String unique() {
        return UUID.randomUUID().toString();
    }

    private static class NameAndResponse {
        private final String name;
        private final CreateGroupResponse response;

        private NameAndResponse(final String name, final CreateGroupResponse response) {
            this.name = name;
            this.response = response;
        }
    }
}