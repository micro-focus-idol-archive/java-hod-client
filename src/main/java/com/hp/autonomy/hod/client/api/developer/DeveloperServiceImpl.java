/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
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

import java.util.Collections;
import java.util.UUID;

/**
 * Default implementation of a DeveloperService.
 */
public class DeveloperServiceImpl implements DeveloperService {
    private final Hmac hmac = new Hmac();
    private final DeveloperBackend backend;

    public DeveloperServiceImpl(final HodServiceConfig<?, ?> config) {
        backend = config.getRestAdapter().create(DeveloperBackend.class);
    }

    @Override
    public ApiKey addAuthentication(final AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token, final UUID developerUuid) throws HodErrorException {
        final String mode = UserAuthMode.API_KEY.getName();

        final Request<String, String> request = new Request<>(
            Request.Verb.POST,
            "/2/developer/" + developerUuid + "/authentication",
            null,
            Collections.singletonMap(DeveloperBackend.AUTH_MODE_PART, Collections.singletonList(mode))
        );

        final String signature = hmac.generateToken(request, token);
        final AddDeveloperAuthenticationResponse response = backend.addAuthentication(signature, developerUuid, mode);
        return response.getCredentials().getApiKey();
    }
}
