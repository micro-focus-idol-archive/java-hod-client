/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.token;

import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TokenProxyTest {

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        final TokenProxy<EntityType.User, TokenType.Simple> initialTokenProxy = new TokenProxy<>(EntityType.User.INSTANCE, TokenType.Simple.INSTANCE);
        objectOutputStream.writeObject(initialTokenProxy);

        final ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

        @SuppressWarnings("unchecked")
        final TokenProxy<EntityType.User, TokenType.Simple> result = (TokenProxy<EntityType.User, TokenType.Simple>) objectInputStream.readObject();

        assertThat(result, is(initialTokenProxy));
    }

}