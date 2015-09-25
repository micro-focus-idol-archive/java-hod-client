/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.user;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class ApplicationUsersServiceImplITCase extends AbstractHodClientIntegrationTest {
    private ApplicationUsersService service;

    public ApplicationUsersServiceImplITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Before
    @Override
    public void setUp() {
        super.setUp();
        service = new ApplicationUsersServiceImpl(getConfig());
    }

    @Test
    public void listsUsers() throws HodErrorException {
        final List<User> users = service.getUsers(getTokenProxy());

        for (final User user : users) {
            assertThat(user.getName(), notNullValue());

            final ResourceIdentifier userStore = user.getUserStore();
            assertThat(userStore, notNullValue());
            assertThat(userStore.getName(), notNullValue());
            assertThat(userStore.getDomain(), notNullValue());
        }
    }
}
