/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.token;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A {@link TokenRepository} which is backed by a {@link java.util.Map}
 *
 * This class is thread safe
 *
 * This class does not expire old tokens
 */
public class InMemoryTokenRepository implements TokenRepository {

    private final ConcurrentMap<TokenProxy<?,?>, AuthenticationToken<?,?>> map = new ConcurrentHashMap<>();

    @Override
    public <E extends EntityType, T extends TokenType> TokenProxy<E, T> insert(final AuthenticationToken<E, T> token) {
        checkTokenExpiry(token);

        final TokenProxy<E, T> key = new TokenProxy<>(token.getEntityType(), token.getTokenType());

        map.put(key, token);

        return key;
    }

    @Override
    public <E extends EntityType, T extends TokenType> AuthenticationToken<E, T> update(final TokenProxy<E, T> key, final AuthenticationToken<E, T> newToken) {
        checkTokenExpiry(newToken);

        // we only put matching pairs into the map
        //noinspection unchecked
        return (AuthenticationToken<E, T>) map.replace(key, newToken);
    }

    @Override
    public <E extends EntityType, T extends TokenType> AuthenticationToken<E, T> get(final TokenProxy<E, T> key) {
        // we only put matching pairs into the map
        //noinspection unchecked
        return (AuthenticationToken<E, T>) map.get(key);
    }

    @Override
    public <E extends EntityType, T extends TokenType> AuthenticationToken<E, T> remove(final TokenProxy<E, T> key) {
        // we only put matching pairs into the map
        //noinspection unchecked
        return (AuthenticationToken<E, T>) map.remove(key);
    }

    private void checkTokenExpiry(final AuthenticationToken<?, ?> token) {
        if (token.hasExpired()) {
            throw new IllegalArgumentException("Token has already expired");
        }
    }

}
