/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.developer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.autonomy.hod.client.api.authentication.ApiKey;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.util.Hmac;
import com.hp.autonomy.hod.client.util.MultiMap;
import com.hp.autonomy.hod.client.util.Request;

import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Default implementation of an {@link ApplicationService}.
 */
public class ApplicationServiceImpl implements ApplicationService {
    private static final String DESCRIPTION_PART = "description";
    private static final String AUTH_MODES_PART = "auth_modes";

    private final ApplicationBackend backend;
    private final Hmac hmac;
    final ObjectMapper objectMapper;

    public ApplicationServiceImpl(final HodServiceConfig<?, ?> config) {
        backend = config.getRestAdapter().create(ApplicationBackend.class);
        hmac = new Hmac();
        objectMapper = config.getObjectMapper();
    }

    @Override
    public List<Application> list(final AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token, final String domain) throws HodErrorException {
        final Request<Void, Void> request = new Request<>(Request.Verb.GET, pathForDomain(domain) + "/v1", null, null);
        final String signature = hmac.generateToken(request, token);
        return backend.list(signature, domain).getApplications();
    }

    @Override
    public void create(final AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token, final String domain, final String name, final String description) throws HodErrorException {
        final Map<String, List<String>> body = new LinkedHashMap<>();
        body.put("application_name", Collections.singletonList(name));
        body.put("description", Collections.singletonList(description));

        final Request<Void, String> request = new Request<>(Request.Verb.POST, pathForDomain(domain) + "/v1", null, body);
        final String signature = hmac.generateToken(request, token);
        backend.create(signature, domain, name, description);
    }

    @Override
    public void delete(final AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token, final String domain, final String name) throws HodErrorException {
        final Request<Void, Void> request = new Request<>(Request.Verb.DELETE, pathForApplication(domain, name) + "/v1", null, null);
        final String signature = hmac.generateToken(request, token);
        backend.delete(signature, domain, name);
    }

    @Override
    public void update(
            final AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token,
            final String domain,
            final String name,
            final ApplicationUpdateRequest updateRequest
    ) throws HodErrorException {
        final Optional<List<String>> optionalAuthModes = updateRequest.getAuthentications()
                .map(authentications -> authentications.stream()
                        .map(this::serializeAuthentication)
                        .collect(Collectors.toList())
                );

        final Map<String, List<String>> body = new HashMap<>();
        final Map<String, String> bodyMultiMap = new MultiMap<>();

        optionalAuthModes.ifPresent(authenticationStrings -> {
            body.put(AUTH_MODES_PART, authenticationStrings);
            authenticationStrings.forEach(s -> bodyMultiMap.put(AUTH_MODES_PART, s));
        });

        updateRequest.getDescription()
                .ifPresent(description -> {
                    body.put(DESCRIPTION_PART, Collections.singletonList(description));
                    bodyMultiMap.put(DESCRIPTION_PART, description);
                });

        final Request<Void, String> request = new Request<>(Request.Verb.PATCH, pathForApplication(domain, name) + "/v1", null, body);
        final String signature = hmac.generateToken(request, token);
        backend.update(signature, domain, name, bodyMultiMap);
    }

    @Override
    public List<AuthenticationDetails> listAuthentications(final AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token, final String domain, final String name) throws HodErrorException {
        final Request<Void, Void> request = new Request<>(Request.Verb.GET, pathForApplication(domain, name) + "/authentication/v1", null, null);
        final String signature = hmac.generateToken(request, token);
        return backend.listAuthentications(signature, domain, name).getAuthentications();
    }

    @Override
    public ApiKey addAuthentication(final AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token, final String domain, final String name) throws HodErrorException {
        final Request<Void, Void> request = new Request<>(Request.Verb.POST, pathForApplication(domain, name) + "/authentication/v1", null, null);
        final String signature = hmac.generateToken(request, token);
        return backend.addAuthentication(signature, domain, name).getCredentials().getApplicationApiKey();
    }

    private String serializeAuthentication(final Authentication authentication) {
        try {
            return objectMapper.writeValueAsString(authentication);
        } catch (final JsonProcessingException e) {
            throw new IllegalStateException("Authentication could not be serialized", e);
        }
    }

    private String pathForDomain(final String domain) {
        return "/2/api/sync/domain/" + encodeUriComponent(domain) + "/application";
    }

    private String pathForApplication(final String domain, final String application) {
        return pathForDomain(domain) + '/' + encodeUriComponent(application);
    }

    private String encodeUriComponent(final String input) {
        try {
            return URLEncoder.encode(input, StandardCharsets.UTF_8.name());
        } catch (final UnsupportedEncodingException e) {
            throw new IllegalStateException("This should never happen", e);
        }
    }
}
