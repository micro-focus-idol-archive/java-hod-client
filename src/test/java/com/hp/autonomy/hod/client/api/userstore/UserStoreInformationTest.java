/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserStoreInformationTest {
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void buildsResourceIdentifier() {
        final UserStoreInformation information = new UserStoreInformation(UUID.randomUUID(), "store_name", "domain_name");

        final ResourceIdentifier identifier = information.getIdentifier();
        assertThat(identifier.getDomain(), is("domain_name"));
        assertThat(identifier.getName(), is("store_name"));
    }

    @Test
    public void deserializesFromUserTokenInformationJson() throws IOException {
        testDeserialization("/com/hp/autonomy/hod/client/api/userstore/user-token-information-user-store.json");
    }

    @Test
    public void deserializesFromGroupTokenInformationJson() throws IOException {
        testDeserialization("/com/hp/autonomy/hod/client/api/userstore/group-token-information-user-store.json");
    }

    private void testDeserialization(final String file) throws IOException {
        final UserStoreInformation output;

        try (final InputStream jsonStream = getClass().getResourceAsStream(file)) {
            output = objectMapper.readValue(jsonStream, UserStoreInformation.class);
        }

        final UserStoreInformation expectedOutput = new UserStoreInformation(
                UUID.fromString("1f885ea5-9d63-4101-908d-257d300efb72"),
                "DEFAULT_USER_STORE",
                "33ed089f-8fe7-451d-82b3-51a06dcdc49b"
        );

        assertThat(output, is(expectedOutput));
    }
}
