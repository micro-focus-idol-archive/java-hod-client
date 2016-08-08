/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.converter.HodConverter;
import com.hp.autonomy.hod.client.error.DefaultHodErrorHandler;
import com.hp.autonomy.hod.client.error.HodErrorHandler;
import com.hp.autonomy.hod.client.token.InMemoryTokenRepository;
import com.hp.autonomy.hod.client.token.TokenProxyService;
import com.hp.autonomy.hod.client.token.TokenRepository;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.http.client.HttpClient;
import org.joda.time.Duration;
import retrofit.RestAdapter;
import retrofit.client.ApacheClient;
import retrofit.client.Client;
import retrofit.converter.JacksonConverter;

/**
 * Configuration class for a HodService
 */
@Data
public class HodServiceConfig<E extends EntityType, T extends TokenType> {

    private final RestAdapter restAdapter;
    private final TokenRepository tokenRepository;
    private final Requester<E, T> requester;
    private final String endpoint;
    private final ObjectMapper objectMapper;
    private final Duration asyncTimeout;

    private HodServiceConfig(final Builder<E, T> builder) {
        final RestAdapter.Builder restAdapterBuilder = new RestAdapter.Builder()
            .setEndpoint(builder.endpoint)
            .setErrorHandler(new ErrorHandlerWrapper(builder.errorHandler));

        if(builder.client != null) {
            restAdapterBuilder.setClient(builder.client);
        }

        if (builder.objectMapper != null) {
            objectMapper = builder.objectMapper.copy();
        }
        else {
            objectMapper = new ObjectMapper();
        }

        // HP Haven OnDemand does not consider adding new properties to be a breaking change, so ignore any unknown
        // properties
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        final JacksonConverter jacksonConverter = new JacksonConverter(objectMapper);
        final HodConverter converter = new HodConverter(jacksonConverter);
        restAdapterBuilder.setConverter(converter);

        restAdapter = restAdapterBuilder.build();
        tokenRepository = builder.tokenRepository;

        requester = new Requester<>(tokenRepository, new ResponseParser(tokenRepository, objectMapper), builder.tokenProxyService);
        endpoint = builder.endpoint;

        asyncTimeout = builder.asyncTimeout;
    }

    /**
     * Builder for HodServiceConfig
     */
    @Accessors(chain = true)
    public static class Builder<E extends EntityType, T extends TokenType> {

        private final String endpoint;
        private Duration asyncTimeout;

        /**
         * Sets the TokenRepository to use. If not provided an {@link InMemoryTokenRepository} will be used
         * @param tokenRepository The TokenRepository to use
         */
        @Setter
        private TokenRepository tokenRepository = new InMemoryTokenRepository();

        /**
         * @param objectMapper The {@link ObjectMapper} to use for processing HP Haven OnDemand requests and responses
         */
        @Setter
        private ObjectMapper objectMapper;

        /**
         * @param tokenProxyService Provides a TokenProxy which is used for every request
         */
        @Setter
        private TokenProxyService<E, T> tokenProxyService;

        private HodErrorHandler errorHandler = new DefaultHodErrorHandler();
        private Client client;

        /**
         * Creates a new HodServiceConfig pointing at the given endpoint
         * @param endpoint The HP Haven OnDemand endpoint to use
         */
        public Builder(final String endpoint) {
            this.endpoint = endpoint;
        }

        /**
         * Configures an Apache HttpClient to use for communication with HP Haven OnDemand. If not provided a sensible
         * default will be used
         * @param httpClient The HttpClient to use
         * @return this
         */
        public Builder<E, T> setHttpClient(final HttpClient httpClient) {
            client = new ApacheClient(httpClient);
            return this;
        }

        /**
         * Configures an error handler to process. In most cases this should wrap a {@link DefaultHodErrorHandler}
         * @param errorHandler The error handler to use
         * @return this
         */
        public Builder<E, T> setErrorHandler(final HodErrorHandler errorHandler) {
            this.errorHandler = errorHandler;
            return this;
        }

        /**
         * Configures an timeout for async requests
         * @param asyncTimeout The duration of the timeout
         * @return this
         */
        public Builder<E, T> setAsyncTimeout(final Duration asyncTimeout) {
            this.asyncTimeout = asyncTimeout;
            return this;
        }

        /**
         * @return A HodServiceConfig with the given options
         */
        public HodServiceConfig<E, T> build() {
            return new HodServiceConfig<>(this);
        }

    }

}
