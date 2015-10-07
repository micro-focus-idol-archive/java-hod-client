/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * A token which can be used to authorise requests to Haven OnDemand. It is parametrised in the type of the entity being
 * authorised and the type of the token.
 * @param <E> The type of the entity (user, developer, etc)
 * @param <T> The type of the token (simple, HMAC, etc)
 */
@Getter
@EqualsAndHashCode
public class AuthenticationToken<E extends EntityType, T extends TokenType> implements Serializable {

    private static final long serialVersionUID = 2L;

    /**
     * @return The entity type authorised by this token
     */
    private final E entityType;

    /**
     * The type of the token, which affects how requests should be made
     */
    private final T tokenType;

    /**
     * @return The expiry time of the token
     */
    private final DateTime expiry;

    /**
     * @return The id of the token
     */
    private final String id;

    /**
     * @return The secret of the token
     */
    private final String secret;

    /**
     * @return The time when a the token will begin to refresh
     */
    private final DateTime startRefresh;

    public AuthenticationToken(
        final E entityType,
        final T tokenType,
        final DateTime expiry,
        final String id,
        final String secret,
        final DateTime startRefresh
    ) {
        this.entityType = entityType;
        this.tokenType = tokenType;
        this.expiry = expiry;
        this.id = id;
        this.secret = secret;
        this.startRefresh = startRefresh;
    }

    private AuthenticationToken(final Json token, final E entityType, final T tokenType) {
        this(entityType, tokenType, token.getExpiry(), token.getId(), token.getSecret(), token.getStartRefresh());

        if (!getType().equals(token.getType())) {
            throw new IllegalArgumentException("Attempted to construct AuthenticationToken of type " + getType() + " from invalid response");
        }
    }

    /**
     * @return True if the token has expired; false otherwise
     */
    public boolean hasExpired() {
        return this.expiry.isBeforeNow();
    }

    /**
     * @return HOD token type for this token
     */
    public String getType() {
        return entityType.getName() + ':' + tokenType.getName();
    }

    @Override
    public String toString() {
        return getType() + ':' + id + ':' + secret;
    }

    private void readObject(final ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();

        // values may be null if deserialized from an invalid stream
        //noinspection ConstantConditions
        if (expiry == null || id == null || secret == null || startRefresh == null || entityType == null || tokenType == null) {
            throw new InvalidObjectException("Values must not be null");
        }
    }

    /**
     * Type representing the JSON representation of an authentication token as returned from Haven OnDemand.
     */
    @Data
    public static class Json {
        private final DateTime expiry;
        private final String id;
        private final String secret;
        private final String type;
        private final DateTime startRefresh;

        public Json(
            @JsonProperty("expiry") final long expiry,
            @JsonProperty("id") final String id,
            @JsonProperty("secret") final String secret,
            @JsonProperty("type") final String type,
            @JsonProperty("startRefresh") final long startRefresh
        ) {
            this.expiry = new DateTime(expiry);
            this.id = id;
            this.secret = secret;
            this.type = type;
            this.startRefresh = new DateTime(startRefresh);
        }

        /**
         * Build an AuthenticationToken of the given entity and token types from this object. This will fail if the
         * {@link #type} field is not compatible with the given entity and token types.
         * @param entityType The entity type
         * @param tokenType The token type
         * @param <E> The type of entity authorised by the new token
         * @param <T> The type of the new token
         * @throws {IllegalArgumentException} If the given entity and token types are not compatible with this object
         * @return The new authentication token
         */
        public <E extends EntityType, T extends TokenType> AuthenticationToken<E, T> buildToken(final E entityType, final T tokenType) {
            return new AuthenticationToken<>(this, entityType, tokenType);
        }
    }
}
