/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.token;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        final AuthenticationToken token = mockToken();

        final TokenProxy key = tokenRepository.insert(token);

        assertThat(tokenRepository.get(key), is(token));
    }

    @Test
    public void testUpdate() {
        final AuthenticationToken token = mockToken();

        final TokenProxy key = tokenRepository.insert(token);

        final AuthenticationToken newToken = mockToken();
        tokenRepository.update(key, newToken);

        assertThat(tokenRepository.get(key), is(newToken));
    }

    @Test
    public void testUpdateReturnsNullAndDoesNothingIfKeyNotPresent() {
        final TokenProxy key = new TokenProxy();
        final AuthenticationToken oldToken = tokenRepository.update(key, mockToken());

        assertThat(oldToken, is(nullValue()));
        assertThat(tokenRepository.get(key), is(nullValue()));
    }

    @Test
    public void testRemove() {
        final AuthenticationToken token = mockToken();
        final TokenProxy key = tokenRepository.insert(token);

        assertThat(tokenRepository.remove(key), is(token));
        assertThat(tokenRepository.get(key), is(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertThrowsIllegalArgumentExceptionForExpiredTokens() throws IOException {
        final AuthenticationToken token = mock(AuthenticationToken.class);
        when(token.hasExpired()).thenReturn(true);

        tokenRepository.insert(token);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateThrowsIllegalArgumentExceptionForExpiredTokens() throws IOException {
        final AuthenticationToken token1 = mockToken();
        final AuthenticationToken token2 = mock(AuthenticationToken.class);
        when(token2.hasExpired()).thenReturn(true);

        TokenProxy key = null;

        try {
            key = tokenRepository.insert(token1);
        } catch (final IllegalArgumentException e) {
            fail("The initial token should not have expired");
        }

        tokenRepository.update(key, token2);
    }

    private AuthenticationToken mockToken() {
        final AuthenticationToken token = mock(AuthenticationToken.class);
        when(token.hasExpired()).thenReturn(false);

        return token;
    }
}