/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.token;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

public class InMemoryTokenRepositoryTest {

    private InMemoryTokenRepository tokenRepository;

    @Before
    public void setUp() {
        tokenRepository = new InMemoryTokenRepository();
    }

    @Test
    public void testGetReturnsNullIfNoKey() {
        assertThat(tokenRepository.get(new TokenProxy()), is(nullValue()));
    }

    @Test
    public void testInsertedTokensCanBeRetrieved() {
        final AuthenticationToken token = mock(AuthenticationToken.class);

        final TokenProxy key = tokenRepository.insert(token);

        assertThat(tokenRepository.get(key), is(token));
    }

    @Test
    public void testUpdate() {
        final AuthenticationToken token = mock(AuthenticationToken.class);

        final TokenProxy key = tokenRepository.insert(token);

        final AuthenticationToken newToken = mock(AuthenticationToken.class);
        tokenRepository.update(key, newToken);

        assertThat(tokenRepository.get(key), is(newToken));
    }

    @Test
    public void testUpdateReturnsNullAndDoesNothingIfKeyNotPresent() {
        final TokenProxy key = new TokenProxy();
        final AuthenticationToken oldToken = tokenRepository.update(key, mock(AuthenticationToken.class));

        assertThat(oldToken, is(nullValue()));
        assertThat(tokenRepository.get(key), is(nullValue()));
    }

    @Test
    public void testRemove() {
        final AuthenticationToken token = mock(AuthenticationToken.class);
        final TokenProxy key = tokenRepository.insert(token);

        assertThat(tokenRepository.remove(key), is(token));
        assertThat(tokenRepository.get(key), is(nullValue()));
    }
}