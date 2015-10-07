/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import org.joda.time.DateTime;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AuthenticationTokenTest {

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        final AuthenticationToken<EntityType.Combined, TokenType.Simple> token = new AuthenticationToken<>(
            EntityType.Combined.INSTANCE,
            TokenType.Simple.INSTANCE,
            new DateTime(1234567890L),
            "foo",
            "bar",
            new DateTime(1234567890L)
        );

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(token);

        final ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

        final AuthenticationToken<EntityType.Combined, TokenType.Simple> result = (AuthenticationToken<EntityType.Combined, TokenType.Simple>) objectInputStream.readObject();

        assertThat(result, is(token));
    }

}
