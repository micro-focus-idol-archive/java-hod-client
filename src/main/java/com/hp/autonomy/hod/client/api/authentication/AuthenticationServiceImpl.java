/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.token.TokenRepository;
import com.hp.autonomy.hod.client.token.TokenRepositoryException;

import java.io.IOException;

/**
 * Default implementation of {@link AuthenticationService}
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationBackend authenticationBackend;
    private final TokenRepository tokenRepository;

    /**
     * Creates a new AuthenticationServiceImpl with the given configuration
     * @param hodServiceConfig The configuration to use
     */
    public AuthenticationServiceImpl(final HodServiceConfig hodServiceConfig) {
        authenticationBackend = hodServiceConfig.getRestAdapter().create(AuthenticationBackend.class);
        tokenRepository = hodServiceConfig.getTokenRepository();
    }

    @Override
    public TokenProxy authenticateApplication(
        final ApiKey apiKey,
        final String applicationName,
        final String domain,
        final TokenType tokenType
    ) throws HodErrorException {
        final AuthenticationToken token = authenticationBackend.authenticateApplication(apiKey, applicationName, domain, tokenType).getToken();

        return insertToken(token);
    }

    @Override
    public TokenProxy authenticateUser(
        final ApiKey apiKey,
        final String applicationName,
        final String applicationDomain,
        final TokenType tokenType
    ) throws HodErrorException {
        final AuthenticationToken token = authenticationBackend.authenticateUser(apiKey, applicationName, applicationDomain, tokenType).getToken();

        return insertToken(token);
    }

    @Override
    public TokenProxy authenticateUser(
        final ApiKey apiKey,
        final String applicationName,
        final String applicationDomain,
        final TokenType tokenType,
        final String userStore,
        final String storeDomain
    ) throws HodErrorException {
        final AuthenticationToken token = authenticationBackend.authenticateUser(apiKey, applicationName, applicationDomain, tokenType, userStore, storeDomain).getToken();

        return insertToken(token);
    }

    @Override
    public AuthenticationToken authenticateUnbound(final ApiKey apiKey) throws HodErrorException {
        return authenticationBackend.authenticateApplicationUnbound(apiKey).getToken();
    }

    private TokenProxy insertToken(final AuthenticationToken token) {
        try {
            return tokenRepository.insert(token);
        } catch (final IOException e) {
            throw new TokenRepositoryException(e);
        }
    }
}
