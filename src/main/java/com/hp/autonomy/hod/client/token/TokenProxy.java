/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.token;

import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode
public final class TokenProxy implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identifier for the token proxy
     * @serial
     */
    private final UUID uuid = UUID.randomUUID();

    private void readObject(final ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();

        // uuid may be null if deserialized from an invalid stream
        //noinspection ConstantConditions
        if (uuid == null) {
            throw new InvalidObjectException("ID must not be null");
        }
    }

}
