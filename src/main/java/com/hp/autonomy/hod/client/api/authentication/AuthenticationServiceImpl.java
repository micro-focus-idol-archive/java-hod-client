/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.autonomy.hod.client.api.authentication.tokeninformation.ApplicationTokenInformation;
import com.hp.autonomy.hod.client.api.authentication.tokeninformation.CombinedTokenInformation;
import com.hp.autonomy.hod.client.api.authentication.tokeninformation.DeveloperTokenInformation;
import com.hp.autonomy.hod.client.api.authentication.tokeninformation.UnboundTokenInformation;
import com.hp.autonomy.hod.client.api.authentication.tokeninformation.UserTokenInformation;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.token.TokenRepository;
import com.hp.autonomy.hod.client.token.TokenRepositoryException;
import com.hp.autonomy.hod.client.util.Hmac;
import com.hp.autonomy.hod.client.util.Request;
import retrofit.client.Response;

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
    private static final String ALLOWED_ORIGINS = "allowed_origins";
    private static final String REDIRECT_URL = "redirect_url";

    private static final Request<?, ?> TOKEN_INFORMATION_REQUEST = new Request<>(Request.Verb.GET, AuthenticationBackend.GET_TOKEN_INFORMATION_PATH, null, null);

    private static final TypeReference<TokenResponse<AuthenticationToken.Json>> TOKEN_TYPE_REFERENCE = new TypeReference<TokenResponse<AuthenticationToken.Json>>() {};
    private static final TypeReference<TokenResponse<DeveloperTokenInformation>> DEVELOPER_INFORMATION_TYPE = new TypeReference<TokenResponse<DeveloperTokenInformation>>() {};
    private static final TypeReference<TokenResponse<CombinedTokenInformation>> COMBINED_INFORMATION_TYPE = new TypeReference<TokenResponse<CombinedTokenInformation>>() {};
    private static final TypeReference<TokenResponse<UserTokenInformation>> USER_INFORMATION_TYPE = new TypeReference<TokenResponse<UserTokenInformation>>() {};
    private static final TypeReference<TokenResponse<UnboundTokenInformation>> UNBOUND_INFORMATION_TYPE = new TypeReference<TokenResponse<UnboundTokenInformation>>() {};
    private static final TypeReference<TokenResponse<ApplicationTokenInformation>> APPLICATION_INFORMATION_TYPE = new TypeReference<TokenResponse<ApplicationTokenInformation>>() {};
    private static final TypeReference<List<ApplicationAndUsers>> AUTHENTICATE_COMBINED_GET_TYPE = new TypeReference<List<ApplicationAndUsers>>() {};

    private final AuthenticationBackend authenticationBackend;
    private final TokenRepository tokenRepository;
    private final ObjectMapper objectMapper;
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
        objectMapper = hodServiceConfig.getObjectMapper();
    }

    @Override
    public <T extends TokenType> TokenProxy<EntityType.Application, T> authenticateApplication(
            final ApiKey apiKey,
            final String applicationName,
            final String domain,
            final T tokenType
    ) throws HodErrorException {
        final Response response = authenticationBackend.authenticateApplication(apiKey, applicationName, domain, tokenType.getParameter());
        final AuthenticationToken<EntityType.Application, T> token = parseToken(response, EntityType.Application.INSTANCE, tokenType);
        return insertToken(token);
    }

    @Override
    public AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> authenticateDeveloper(final ApiKey apiKey, final UUID tenantUuid, final String email) throws HodErrorException {
        final Response response = authenticationBackend.authenticateDeveloper(apiKey, tenantUuid.toString(), email);
        return parseToken(response, EntityType.Developer.INSTANCE, TokenType.HmacSha1.INSTANCE);
    }

    @Override
    public <T extends TokenType> AuthenticationToken<EntityType.Unbound, T> authenticateUnbound(final ApiKey apiKey, final T tokenType) throws HodErrorException {
        final Response response = authenticationBackend.authenticateUnbound(apiKey, tokenType.getParameter());
        return parseToken(response, EntityType.Unbound.INSTANCE, tokenType);
    }

    @Override
    public List<ApplicationAndUsers> authenticateCombinedGet(
            final AuthenticationToken<EntityType.CombinedSso, TokenType.Simple> combinedSsoToken,
            final AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> appToken
    ) throws HodErrorException {
        final String nonce = generateNonce();

        final Request<String, Void> request = new Request<>(
                Request.Verb.GET,
                AuthenticationBackend.COMBINED_PATH,
                Collections.singletonMap(AuthenticationBackend.NONCE_PARAMETER, Collections.singletonList(nonce)),
                null
        );

        final Response response = authenticationBackend.authenticateCombinedGet(combinedSsoToken, hmac.generateToken(request, appToken), nonce);

        try {
            return objectMapper.readValue(response.getBody().in(), AUTHENTICATE_COMBINED_GET_TYPE);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T extends TokenType> AuthenticationToken<EntityType.Combined, T> authenticateCombined(
            final AuthenticationToken<EntityType.CombinedSso, TokenType.Simple> combinedSsoToken,
            final AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> appToken,
            final String applicationDomain,
            final String applicationName,
            final T tokenType
    ) throws HodErrorException {
        final Request<Void, String> request = new Request<>(
            Request.Verb.POST,
            AuthenticationBackend.COMBINED_PATH,
            null,
            createBaseCombinedBody(applicationName, applicationDomain, tokenType)
        );

        final String signature = hmac.generateToken(request, appToken);

        final Response response = authenticationBackend.authenticateCombined(
                combinedSsoToken,
                signature,
                generateNonce(),
                applicationDomain,
                applicationName,
                null,
                null,
                tokenType.getParameter()
        );

        return parseToken(response, EntityType.Combined.INSTANCE, tokenType);
    }

    @Override
    public <T extends TokenType> AuthenticationToken<EntityType.Combined, T> authenticateCombined(
            final AuthenticationToken<EntityType.CombinedSso, TokenType.Simple> combinedSsoToken,
            final AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> appToken,
            final String applicationDomain,
            final String applicationName,
            final String userstoreDomain,
            final String userstoreName,
            final T tokenType
    ) throws HodErrorException {
        final Request<Void, String> request = new Request<>(
                Request.Verb.POST,
                AuthenticationBackend.COMBINED_PATH,
                null,
                createUserStoreCombinedBody(applicationName, applicationDomain, tokenType, userstoreDomain, userstoreName)
        );

        final String signature = hmac.generateToken(request, appToken);

        final Response response = authenticationBackend.authenticateCombined(
                combinedSsoToken,
                signature,
                generateNonce(),
                applicationDomain,
                applicationName,
                userstoreDomain,
                userstoreName,
                tokenType.getParameter()
        );

        return parseToken(response, EntityType.Combined.INSTANCE, tokenType);
    }

    @Override
    public CombinedTokenInformation getCombinedTokenInformation(final AuthenticationToken<EntityType.Combined, TokenType.Simple> token) throws HodErrorException {
        final Response response = authenticationBackend.getTokenInformation(token.toString());
        return parseTokenInformation(response, COMBINED_INFORMATION_TYPE);
    }

    @Override
    public CombinedTokenInformation getHmacCombinedTokenInformation(final AuthenticationToken<EntityType.Combined, TokenType.HmacSha1> token) throws HodErrorException {
        final Response response = authenticationBackend.getTokenInformation(hmac.generateToken(TOKEN_INFORMATION_REQUEST, token));
        return parseTokenInformation(response, COMBINED_INFORMATION_TYPE);
    }

    @Override
    public DeveloperTokenInformation getDeveloperTokenInformation(final AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token) throws HodErrorException {
        final Response response = authenticationBackend.getTokenInformation(hmac.generateToken(TOKEN_INFORMATION_REQUEST, token));
        return parseTokenInformation(response, DEVELOPER_INFORMATION_TYPE);
    }

    @Override
    public ApplicationTokenInformation getApplicationTokenInformation(final TokenProxy<EntityType.Application, TokenType.Simple> tokenProxy) throws HodErrorException {
        final AuthenticationToken<EntityType.Application, TokenType.Simple> token = getToken(tokenProxy);

        final Response response = authenticationBackend.getTokenInformation(token.toString());
        return parseTokenInformation(response, APPLICATION_INFORMATION_TYPE);
    }

    @Override
    public ApplicationTokenInformation getHmacApplicationTokenInformation(final TokenProxy<EntityType.Application, TokenType.HmacSha1> tokenProxy) throws HodErrorException {
        final AuthenticationToken<EntityType.Application, TokenType.HmacSha1> token = getToken(tokenProxy);

        final Response response = authenticationBackend.getTokenInformation(hmac.generateToken(TOKEN_INFORMATION_REQUEST, token));
        return parseTokenInformation(response, APPLICATION_INFORMATION_TYPE);
    }

    @Override
    public UserTokenInformation getUserTokenInformation(final TokenProxy<EntityType.User, TokenType.Simple> tokenProxy) throws HodErrorException {
        final AuthenticationToken<EntityType.User, TokenType.Simple> token = getToken(tokenProxy);

        final Response response = authenticationBackend.getTokenInformation(token.toString());
        return parseTokenInformation(response, USER_INFORMATION_TYPE);
    }

    @Override
    public UserTokenInformation getHmacUserTokenInformation(final TokenProxy<EntityType.User, TokenType.HmacSha1> tokenProxy) throws HodErrorException {
        final AuthenticationToken<EntityType.User, TokenType.HmacSha1> token = getToken(tokenProxy);

        final Response response = authenticationBackend.getTokenInformation(hmac.generateToken(TOKEN_INFORMATION_REQUEST, token));
        return parseTokenInformation(response, USER_INFORMATION_TYPE);
    }

    @Override
    public UnboundTokenInformation getUnboundTokenInformation(final AuthenticationToken<EntityType.Unbound, TokenType.Simple> token) throws HodErrorException {
        final Response response = authenticationBackend.getTokenInformation(token.toString());
        return parseTokenInformation(response, UNBOUND_INFORMATION_TYPE);
    }

    @Override
    public UnboundTokenInformation getHmacUnboundTokenInformation(final AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> token) throws HodErrorException {
        final Response response = authenticationBackend.getTokenInformation(hmac.generateToken(TOKEN_INFORMATION_REQUEST, token));
        return parseTokenInformation(response, UNBOUND_INFORMATION_TYPE);
    }

    @Override
    public SignedRequest combinedGetRequest(final Collection<String> allowedOrigins, final AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> token) {
        final Map<String, List<String>> queryParameters = new HashMap<>();
        queryParameters.put(ALLOWED_ORIGINS, new ArrayList<>(allowedOrigins));

        final Request<String, String> request = new Request<>(Request.Verb.GET, AuthenticationBackend.COMBINED_PATH, queryParameters, null);
        return SignedRequest.sign(hmac, endpoint, token, request);
    }

    @Override
    public SignedRequest combinedPatchRequest(final Collection<String> allowedOrigins, final AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> token) {
        final Map<String, List<String>> queryParameters = new HashMap<>();
        queryParameters.put(ALLOWED_ORIGINS, new ArrayList<>(allowedOrigins));

        final Request<String, String> request = new Request<>(Request.Verb.PATCH, AuthenticationBackend.COMBINED_PATH, queryParameters, null);
        return SignedRequest.sign(hmac, endpoint, token, request);
    }

    @Override
    public SignedRequest combinedPatchRequest(final Collection<String> allowedOrigins, final String redirectUrl, final AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> token) {
        final Map<String, List<String>> queryParameters = new HashMap<>();
        queryParameters.put(ALLOWED_ORIGINS, new ArrayList<>(allowedOrigins));
        queryParameters.put(REDIRECT_URL, Collections.singletonList(redirectUrl));

        final Request<String, String> request = new Request<>(Request.Verb.PATCH, AuthenticationBackend.COMBINED_PATH, queryParameters, null);
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
            body.put(AuthenticationBackend.NONCE_PARAMETER, Collections.singletonList(generateNonce()));
        }

        return createCombinedRequest(token, allowedOrigins, body);
    }

    private String generateNonce() {
        return UUID.randomUUID().toString();
    }

    private SignedRequest createCombinedRequest(
            final AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> token,
            final Collection<String> allowedOrigins,
            final Map<String, List<String>> body
    ) {
        final Map<String, List<String>> queryParameters = new HashMap<>();
        queryParameters.put(ALLOWED_ORIGINS, new ArrayList<>(allowedOrigins));

        final Request<String, String> request = new Request<>(Request.Verb.POST, AuthenticationBackend.COMBINED_PATH, queryParameters, body);
        return SignedRequest.sign(hmac, endpoint, token, request);
    }

    private Map<String, List<String>> createBaseCombinedBody(
            final String applicationName,
            final String applicationDomain,
            final TokenType tokenType
    ) {
        final Map<String, List<String>> body = new HashMap<>();
        body.put(AuthenticationBackend.DOMAIN_PARAMETER, Collections.singletonList(applicationDomain));
        body.put(AuthenticationBackend.APPLICATION_PARAMETER, Collections.singletonList(applicationName));
        body.put(AuthenticationBackend.TOKEN_TYPE_PARAMETER, Collections.singletonList(tokenType.getParameter()));
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
        body.put(AuthenticationBackend.USERSTORE_DOMAIN_PARAMETER, Collections.singletonList(userStoreDomain));
        body.put(AuthenticationBackend.USERSTORE_NAME_PARAMETER, Collections.singletonList(userStoreName));
        return body;
    }

    private <E extends EntityType, T extends TokenType> TokenProxy<E, T> insertToken(final AuthenticationToken<E, T> token) {
        try {
            return tokenRepository.insert(token);
        } catch (final IOException e) {
            throw new TokenRepositoryException(e);
        }
    }

    private <E extends EntityType, T extends TokenType> AuthenticationToken<E, T> getToken(final TokenProxy<E, T> tokenProxy) {
        try {
            return tokenRepository.get(tokenProxy);
        } catch (final IOException e) {
            throw new TokenRepositoryException(e);
        }
    }

    private <E extends EntityType, T extends TokenType> AuthenticationToken<E, T> parseToken(final Response response, final E entityType, final T tokenType) {
        try {
            final TokenResponse<AuthenticationToken.Json> tokenResponse = objectMapper.readValue(response.getBody().in(), TOKEN_TYPE_REFERENCE);
            return tokenResponse.getToken().buildToken(entityType, tokenType);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T parseTokenInformation(final Response response, final TypeReference<TokenResponse<T>> typeReference) {
        try {
            final TokenResponse<T> tokenResponse = objectMapper.readValue(response.getBody().in(), typeReference);
            return tokenResponse.getToken();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
