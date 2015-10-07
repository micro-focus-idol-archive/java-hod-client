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
import com.hp.autonomy.hod.client.util.Hmac;
import com.hp.autonomy.hod.client.util.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Default implementation of {@link AuthenticationService}
 */
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String COMBINED_PATH = "/2/authenticate/combined";
    private static final String ALLOWED_ORIGINS = "allowed_origins";

    private final AuthenticationBackend authenticationBackend;
    private final TokenRepository tokenRepository;
    private final String endpoint;

    private final Hmac hmac = new Hmac();

    /**
     * Creates a new AuthenticationServiceImpl with the given configuration
     * @param hodServiceConfig The configuration to use
     */
    public AuthenticationServiceImpl(final HodServiceConfig<?, ?> hodServiceConfig) {
        authenticationBackend = hodServiceConfig.getRestAdapter().create(AuthenticationBackend.class);
        tokenRepository = hodServiceConfig.getTokenRepository();
        endpoint = hodServiceConfig.getEndpoint();
    }

    @Override
    public <T extends TokenType> TokenProxy<EntityType.Application, T> authenticateApplication(
        final ApiKey apiKey,
        final String applicationName,
        final String domain,
        final T tokenType
    ) throws HodErrorException {
        final AuthenticationTokenResponse response = authenticationBackend.authenticateApplication(apiKey, applicationName, domain, tokenType.getParameter());
        final AuthenticationToken<EntityType.Application, T> token = response.getTokenJson().buildToken(EntityType.Application.INSTANCE, tokenType);
        return insertToken(token);
    }

    @Override
    public <T extends TokenType> TokenProxy<EntityType.User, T> authenticateUser(
        final ApiKey apiKey,
        final String applicationName,
        final String applicationDomain,
        final T tokenType
    ) throws HodErrorException {
        final AuthenticationTokenResponse response = authenticationBackend.authenticateUser(apiKey, applicationName, applicationDomain, tokenType.getParameter());
        final AuthenticationToken<EntityType.User, T> token = response.getTokenJson().buildToken(EntityType.User.INSTANCE, tokenType);
        return insertToken(token);
    }

    @Override
    public <T extends TokenType> TokenProxy<EntityType.User, T> authenticateUser(
        final ApiKey apiKey,
        final String applicationName,
        final String applicationDomain,
        final T tokenType,
        final String userStore,
        final String storeDomain
    ) throws HodErrorException {
        final AuthenticationTokenResponse response = authenticationBackend.authenticateUser(apiKey, applicationName, applicationDomain, tokenType.getParameter(), userStore, storeDomain);
        final AuthenticationToken<EntityType.User, T> token = response.getTokenJson().buildToken(EntityType.User.INSTANCE, tokenType);
        return insertToken(token);
    }

    @Override
    public AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> authenticateUnbound(final ApiKey apiKey) throws HodErrorException {
        final AuthenticationTokenResponse response = authenticationBackend.authenticateUnbound(apiKey, TokenType.HmacSha1.INSTANCE.getParameter());
        return response.getTokenJson().buildToken(EntityType.Unbound.INSTANCE, TokenType.HmacSha1.INSTANCE);
    }

    @Override
    public SignedRequest combinedGetRequest(final Collection<String> allowedOrigins, final AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> token) {
        final Map<String, List<String>> queryParameters = new HashMap<>();
        queryParameters.put(ALLOWED_ORIGINS, new ArrayList<>(allowedOrigins));

        final Request<String, String> request = new Request<>(Request.Verb.GET, COMBINED_PATH, queryParameters, null);
        return SignedRequest.sign(hmac, endpoint, token, request);
    }

    @Override
    public SignedRequest combinedRequest(
            final Collection<String> allowedOrigins,
            final AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> token,
            final String applicationDomain,
            final String applicationName,
            final TokenType tokenType
    ) {
        final Map<String, List<String>> body = createBaseCombinedBody(applicationName, applicationDomain, tokenType);
        return createCombinedRequest(token, allowedOrigins, body);
    }

    @Override
    public SignedRequest combinedRequest(
            final Collection<String> allowedOrigins,
            final AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> token,
            final String applicationDomain,
            final String applicationName,
            final String userStoreDomain,
            final String userStoreName,
            final TokenType tokenType
    ) {
        final Map<String, List<String>> body = createUserStoreCombinedBody(applicationName, applicationDomain, tokenType, userStoreDomain, userStoreName);
        return createCombinedRequest(token, allowedOrigins, body);
    }

    @Override
    public SignedRequest combinedRequest(
            final Collection<String> allowedOrigins,
            final AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> token,
            final String applicationDomain,
            final String applicationName,
            final String userStoreDomain,
            final String userStoreName,
            final TokenType tokenType,
            final boolean useNonce
    ) {
        final Map<String, List<String>> body = createUserStoreCombinedBody(applicationName, applicationDomain, tokenType, userStoreDomain, userStoreName);

        if (useNonce) {
            final String nonce = UUID.randomUUID().toString();
            body.put("nonce", Collections.singletonList(nonce));
        }

        return createCombinedRequest(token, allowedOrigins, body);
    }

    private SignedRequest createCombinedRequest(
            final AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> token,
            final Collection<String> allowedOrigins,
            final Map<String, List<String>> body
    ) {
        final Map<String, List<String>> queryParameters = new HashMap<>();
        queryParameters.put(ALLOWED_ORIGINS, new ArrayList<>(allowedOrigins));

        final Request<String, String> request = new Request<>(Request.Verb.POST, COMBINED_PATH, queryParameters, body);
        return SignedRequest.sign(hmac, endpoint, token, request);
    }

    private Map<String, List<String>> createBaseCombinedBody(
            final String applicationName,
            final String applicationDomain,
            final TokenType tokenType
    ) {
        final Map<String, List<String>> body = new HashMap<>();
        body.put("domain", Collections.singletonList(applicationDomain));
        body.put("application", Collections.singletonList(applicationName));
        body.put("token_type", Collections.singletonList(tokenType.getParameter()));
        return body;
    }

    private Map<String, List<String>> createUserStoreCombinedBody(
            final String applicationName,
            final String applicationDomain,
            final TokenType tokenType,
            final String userStoreDomain,
            final String userStoreName
    ) {
        final Map<String, List<String>> body = createBaseCombinedBody(applicationName, applicationDomain, tokenType);
        body.put("userstore_domain", Collections.singletonList(userStoreDomain));
        body.put("userstore_name", Collections.singletonList(userStoreName));
        return body;
    }

    private <E extends EntityType, T extends TokenType> TokenProxy<E, T> insertToken(final AuthenticationToken<E, T> token) {
        try {
            return tokenRepository.insert(token);
        } catch (final IOException e) {
            throw new TokenRepositoryException(e);
        }
    }
}
