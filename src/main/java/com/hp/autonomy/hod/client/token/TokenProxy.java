/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.token;

import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.UUID;

/**
 * A token which can be used to get an {@link com.hp.autonomy.hod.client.api.authentication.AuthenticationToken} from a
 * {@link TokenRepository}.
 * @param <E> The entity type of the authentication tokens which can be obtained using this proxy
 * @param <T> The token type of the authentication tokens which can be obtained using this proxy
 */
@Data
public final class TokenProxy<E extends EntityType, T extends TokenType> implements Serializable {

    private static final long serialVersionUID = 2L;

    /**
     * Identifier for the token proxy
     * @serial
     */
    @Getter(AccessLevel.NONE)
    private final UUID uuid = UUID.randomUUID();

    /**
     * Entity type for this token proxy.
     */
    private final E entityType;

    /**
     * Token type for this token proxy.
     */
    private final T tokenType;

    public TokenProxy(final E entityType, final T tokenType) {
        this.entityType = entityType;
        this.tokenType = tokenType;
    }

    private void readObject(final ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();

        // uuid may be null if deserialized from an invalid stream
        //noinspection ConstantConditions
        if (uuid == null || entityType == null || tokenType == null) {
            throw new InvalidObjectException("Values must not be null");
        }
    }

}
