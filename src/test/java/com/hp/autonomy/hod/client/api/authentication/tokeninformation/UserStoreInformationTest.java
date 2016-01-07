/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication.tokeninformation;

import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserStoreInformationTest {
    @Test
    public void buildsResourceIdentifier() {
        final UserStoreInformation information = new UserStoreInformation(UUID.randomUUID(), "store_name", "domain_name");

        final ResourceIdentifier identifier = information.getIdentifier();
        assertThat(identifier.getDomain(), is("domain_name"));
        assertThat(identifier.getName(), is("store_name"));
    }
}
