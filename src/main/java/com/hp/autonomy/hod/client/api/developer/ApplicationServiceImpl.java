/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.developer;

import com.hp.autonomy.hod.client.api.authentication.ApiKey;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.util.Hmac;
import com.hp.autonomy.hod.client.util.Request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Default implementation of an {@link ApplicationService}.
 */
public class ApplicationServiceImpl implements ApplicationService {
    Request<Void, Void> LIST_APPLICATION_REQUEST = new Request<>(Request.Verb.GET, ApplicationBackend.APPLICATION_PATH, null, null);

    private final ApplicationBackend backend;
    private final Hmac hmac;

    public ApplicationServiceImpl(final HodServiceConfig<?, ?> config) {
        backend = config.getRestAdapter().create(ApplicationBackend.class);
        hmac = new Hmac();
    }

    @Override
    public List<Application> list(final AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token) throws HodErrorException {
        final String signature = hmac.generateToken(LIST_APPLICATION_REQUEST, token);
        return backend.list(signature).getApplications();
    }

    @Override
    public void create(final AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token, final String domain, final String name, final String description) throws HodErrorException {
        final Map<String, List<String>> body = new LinkedHashMap<>();
        body.put("domain_name", Collections.singletonList(domain));
        body.put("application_name", Collections.singletonList(name));
        body.put("description", Collections.singletonList(description));

        final Request<Void, String> request = new Request<>(Request.Verb.POST, ApplicationBackend.APPLICATION_PATH, null, body);
        final String signature = hmac.generateToken(request, token);
        backend.create(signature, domain, name, description);
    }

    @Override
    public void delete(final AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token, final String domain, final String name) throws HodErrorException {
        final Request<Void, Void> request = new Request<>(Request.Verb.DELETE, pathForApplication(domain, name), null, null);
        final String signature = hmac.generateToken(request, token);
        backend.delete(signature, domain, name);
    }

    @Override
    public List<Authentication> listAuthentications(final AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token, final String domain, final String name) throws HodErrorException {
        final Request<Void, Void> request = new Request<>(Request.Verb.GET, pathForApplication(domain, name) + "/authentication", null, null);
        final String signature = hmac.generateToken(request, token);
        return backend.listAuthentications(signature, domain, name).getAuthentications();
    }

    @Override
    public ApiKey addAuthentication(final AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token, final String domain, final String name) throws HodErrorException {
        final Request<Void, Void> request = new Request<>(Request.Verb.POST, pathForApplication(domain, name) + "/authentication", null, null);
        final String signature = hmac.generateToken(request, token);
        return backend.addAuthentication(signature, domain, name).getCredentials().getApplicationApiKey();
    }

    @Override
    public void addAuthenticationMode(
        final AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token,
        final String domain,
        final String name,
        final ApplicationAuthMode applicationMode,
        final UserAuthMode userMode,
        final TokenType returnTokenType
    ) throws HodErrorException {
        final Map<String, List<String>> body = new HashMap<>();
        body.put(ApplicationBackend.APP_AUTH_MODE_PART, Collections.singletonList(applicationMode.getName()));
        body.put(ApplicationBackend.USER_AUTH_MODE_PART, Collections.singletonList(userMode.getName()));
        body.put(ApplicationBackend.RETURN_TOKEN_TYPE_PART, Collections.singletonList(returnTokenType.getParameter()));

        final Request<Void, String> request = new Request<>(Request.Verb.POST, pathForApplication(domain, name) + "/authentication_mode", null, body);
        final String signature = hmac.generateToken(request, token);
        backend.addAuthMode(signature, domain, name, applicationMode.getName(), userMode.getName(), returnTokenType.getParameter());
    }

    private String pathForApplication(final String domain, final String application) {
        try {
            final String encodedDomain = URLEncoder.encode(domain, StandardCharsets.UTF_8.name());
            final String encodedName = URLEncoder.encode(application, StandardCharsets.UTF_8.name());
            return "/2/domain/" + encodedDomain + "/application/" + encodedName;
        } catch (final UnsupportedEncodingException e) {
            throw new IllegalStateException("This should never happen", e);
        }
    }
}
