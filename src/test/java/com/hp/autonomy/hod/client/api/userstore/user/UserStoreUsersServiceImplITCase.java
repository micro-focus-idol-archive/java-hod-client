/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.userstore.User;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

// TODO: Enable GroupsService tests when the APIs are deployed on int.havenondemand.com
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
    public void listUsers() throws HodErrorException {
        final List<User> users = service.list(getTokenProxy(), USER_STORE);

        for (final User user : users) {
            assertThat(user.getName(), not(nullValue()));
            assertThat(user.getUuid(), not(nullValue()));
        }
    }
}