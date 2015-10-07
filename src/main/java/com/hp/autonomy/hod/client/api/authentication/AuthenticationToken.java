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

// TODO: Update JavaDoc
/**
 * A token which can be used to make API requests to HP Haven OnDemand
 */
@Getter
@EqualsAndHashCode
public class AuthenticationToken<E extends EntityType, T extends TokenType> implements Serializable {

    private static final long serialVersionUID = 2L;

    private final E entityType;

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

        public <E extends EntityType, T extends TokenType> AuthenticationToken<E, T> buildToken(final E entityType, final T tokenType) {
            return new AuthenticationToken<>(this, entityType, tokenType);
        }
    }
}
