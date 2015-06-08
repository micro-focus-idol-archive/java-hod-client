/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.token;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;

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

    private final ConcurrentMap<TokenProxy, AuthenticationToken> map = new ConcurrentHashMap<>();

    @Override
    public TokenProxy insert(final AuthenticationToken token) {
        checkTokenExpiry(token);

        final TokenProxy key = new TokenProxy();

        map.put(key, token);

        return key;
    }

    @Override
    public AuthenticationToken update(final TokenProxy key, final AuthenticationToken newToken) {
        checkTokenExpiry(newToken);

        return map.replace(key, newToken);
    }

    @Override
    public AuthenticationToken get(final TokenProxy key) {
        return map.get(key);
    }

    @Override
    public AuthenticationToken remove(final TokenProxy key) {
        return map.remove(key);
    }

    private void checkTokenExpiry(final AuthenticationToken token) {
        if (token.hasExpired()) {
            throw new IllegalArgumentException("Token has already expired");
        }
    }
}
