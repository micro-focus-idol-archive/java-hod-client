/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.token;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
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
        assertThat(tokenRepository.get(new TokenProxy<>(EntityType.Application.INSTANCE, TokenType.Simple.INSTANCE)), is(nullValue()));
    }

    @Test
    public void testInsertedTokensCanBeRetrieved() {
        final AuthenticationToken<EntityType.Combined, TokenType.Simple> token = mockToken(false);

        final TokenProxy<EntityType.Combined, TokenType.Simple> key = tokenRepository.insert(token);

        assertThat(tokenRepository.get(key), is(token));
    }

    @Test
    public void testUpdate() {
        final AuthenticationToken<EntityType.Combined, TokenType.Simple> token = mockToken(false);

        final TokenProxy<EntityType.Combined, TokenType.Simple> key = tokenRepository.insert(token);

        final AuthenticationToken<EntityType.Combined, TokenType.Simple> newToken = mockToken(false);
        tokenRepository.update(key, newToken);

        assertThat(tokenRepository.get(key), is(newToken));
    }

    @Test
    public void testUpdateReturnsNullAndDoesNothingIfKeyNotPresent() {
        final TokenProxy<EntityType.Combined, TokenType.Simple> key = new TokenProxy<>(EntityType.Combined.INSTANCE, TokenType.Simple.INSTANCE);
        final AuthenticationToken<EntityType.Combined, TokenType.Simple> oldToken = tokenRepository.update(key, mockToken(false));

        assertThat(oldToken, is(nullValue()));
        assertThat(tokenRepository.get(key), is(nullValue()));
    }

    @Test
    public void testRemove() {
        final AuthenticationToken<EntityType.Combined, TokenType.Simple> token = mockToken(false);
        final TokenProxy<EntityType.Combined, TokenType.Simple> key = tokenRepository.insert(token);

        assertThat(tokenRepository.remove(key), is(token));
        assertThat(tokenRepository.get(key), is(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertThrowsIllegalArgumentExceptionForExpiredTokens() throws IOException {
        final AuthenticationToken<EntityType.Combined, TokenType.Simple> token = mockToken(true);

        tokenRepository.insert(token);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateThrowsIllegalArgumentExceptionForExpiredTokens() throws IOException {
        final AuthenticationToken<EntityType.Combined, TokenType.Simple> token1 = mockToken(false);
        final AuthenticationToken<EntityType.Combined, TokenType.Simple> token2 = mockToken(true);
        when(token2.hasExpired()).thenReturn(true);

        TokenProxy<EntityType.Combined, TokenType.Simple> key = null;

        try {
            key = tokenRepository.insert(token1);
        } catch (final IllegalArgumentException e) {
            fail("The initial token should not have expired");
        }

        tokenRepository.update(key, token2);
    }

    private AuthenticationToken<EntityType.Combined, TokenType.Simple> mockToken(final boolean expired) {
        @SuppressWarnings("unchecked")
        final AuthenticationToken<EntityType.Combined, TokenType.Simple> token = mock(AuthenticationToken.class);

        when(token.hasExpired()).thenReturn(expired);
        when(token.getEntityType()).thenReturn(EntityType.Combined.INSTANCE);
        when(token.getTokenType()).thenReturn(TokenType.Simple.INSTANCE);

        return token;
    }
}