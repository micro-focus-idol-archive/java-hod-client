/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.analysis.autocomplete;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import lombok.Data;
import retrofit.client.Response;

import java.util.List;

/**
 * Default implementation of an AutocompleteService.
 */
public class AutocompleteServiceImpl implements AutocompleteService {
    private final AutocompleteBackend backend;
    private final Requester<?, TokenType.Simple> requester;

    public AutocompleteServiceImpl(final HodServiceConfig<?, TokenType.Simple> config) {
        requester = config.getRequester();
        backend = config.getRestAdapter().create(AutocompleteBackend.class);
    }

    @Override
    public List<String> getSuggestions(final String text) throws HodErrorException {
        return requester.makeRequest(AutocompleteResponse.class, new BackendCaller<>(text)).getWords();
    }

    @Override
    public List<String> getSuggestions(final String text, final TokenProxy<?, TokenType.Simple> tokenProxy) throws HodErrorException {
        return requester.makeRequest(tokenProxy, AutocompleteResponse.class, new BackendCaller<>(text)).getWords();
    }

    @Data
    private class BackendCaller<E extends EntityType> implements Requester.BackendCaller<E, TokenType.Simple> {
        private final String text;

        @Override
        public Response makeRequest(final AuthenticationToken<? extends E, ? extends TokenType.Simple> token) throws HodErrorException {
            return backend.getSuggestions(token, text);
        }
    }
}
