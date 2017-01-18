/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ResourceTest {
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void buildsResourceIdentifier() {
        final Resource information = new Resource(UUID.randomUUID(), "resource_name", "domain_name");

        final ResourceName identifier = information.getIdentifier();
        assertThat(identifier.getDomain(), is("domain_name"));
        assertThat(identifier.getName(), is("resource_name"));
    }

    @Test
    public void deserializesFromUserTokenInformationJson() throws IOException {
        testDeserialization("/com/hp/autonomy/hod/client/api/resource/user-token-information-user-store.json");
    }

    @Test
    public void deserializesFromGroupTokenInformationJson() throws IOException {
        testDeserialization("/com/hp/autonomy/hod/client/api/resource/group-token-information-user-store.json");
    }

    @Test
    public void testSerializeAndDeserialize() throws IOException, ClassNotFoundException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        final Resource input = new Resource(UUID.randomUUID(), "name", "domain");

        try (final ObjectOutput objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(input);
        }

        try (final ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()))) {
            @SuppressWarnings("CastToConcreteClass")
            final Resource output = (Resource) objectInputStream.readObject();
            assertThat(output, is(input));
        }
    }

    private void testDeserialization(final String file) throws IOException {
        final Resource output;

        try (final InputStream jsonStream = getClass().getResourceAsStream(file)) {
            output = objectMapper.readValue(jsonStream, Resource.class);
        }

        final Resource expectedOutput = new Resource(
                UUID.fromString("1f885ea5-9d63-4101-908d-257d300efb72"),
                "DEFAULT_USER_STORE",
                "33ed089f-8fe7-451d-82b3-51a06dcdc49b"
        );

        assertThat(output, is(expectedOutput));
    }
}
