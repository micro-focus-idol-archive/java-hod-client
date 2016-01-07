/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.analysis.sentiment;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.util.TypedByteArrayWithFilename;
import org.apache.commons.io.IOUtils;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Default implementation of a SentimentAnalysisService
 */
public class SentimentAnalysisServiceImpl implements SentimentAnalysisService {

    private static final Class<SentimentAnalysisResponse> RESPONSE_CLASS = SentimentAnalysisResponse.class;

    private final SentimentAnalysisBackend sentimentAnalysisBackend;
    private final Requester<?, TokenType.Simple> requester;

    /**
     * Creates a new SentimentAnalysisServiceImpl
     * @param config The configuration to use
     */
    public SentimentAnalysisServiceImpl(final HodServiceConfig<?, TokenType.Simple> config) {
        sentimentAnalysisBackend = config.getRestAdapter().create(SentimentAnalysisBackend.class);
        requester = config.getRequester();
    }

    @Override
    public SentimentAnalysisResponse analyzeSentimentForText(final String text, final SentimentAnalysisLanguage language) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getTextBackendCaller(text, language));
    }

    @Override
    public SentimentAnalysisResponse analyzeSentimentForText(final TokenProxy<?, TokenType.Simple> tokenProxy, final String text, final SentimentAnalysisLanguage language) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getTextBackendCaller(text, language));
    }

    @Override
    public SentimentAnalysisResponse analyzeSentimentForFile(final File file, final SentimentAnalysisLanguage language) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getFileBackendCaller(file, language));
    }

    @Override
    public SentimentAnalysisResponse analyzeSentimentForFile(final TokenProxy<?, TokenType.Simple> tokenProxy, final File file, final SentimentAnalysisLanguage language) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getFileBackendCaller(file, language));
    }

    @Override
    public SentimentAnalysisResponse analyzeSentimentForFile(final byte[] bytes, final SentimentAnalysisLanguage language) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getByteArrayBackendCaller(bytes, language));
    }

    @Override
    public SentimentAnalysisResponse analyzeSentimentForFile(final TokenProxy<?, TokenType.Simple> tokenProxy, final byte[] bytes, final SentimentAnalysisLanguage language) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getByteArrayBackendCaller(bytes, language));
    }

    @Override
    public SentimentAnalysisResponse analyzeSentimentForFile(final InputStream inputStream, final SentimentAnalysisLanguage language) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getInputStreamBackendCaller(inputStream, language));
    }

    @Override
    public SentimentAnalysisResponse analyzeSentimentForFile(final TokenProxy<?, TokenType.Simple> tokenProxy, final InputStream inputStream, final SentimentAnalysisLanguage language) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getInputStreamBackendCaller(inputStream, language));
    }

    @Override
    public SentimentAnalysisResponse analyzeSentimentForReference(final String reference, final SentimentAnalysisLanguage language) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getReferenceBackendCaller(reference, language));
    }

    @Override
    public SentimentAnalysisResponse analyzeSentimentForReference(final TokenProxy<?, TokenType.Simple> tokenProxy, final String reference, final SentimentAnalysisLanguage language) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getReferenceBackendCaller(reference, language));
    }

    @Override
    public SentimentAnalysisResponse analyzeSentimentForUrl(final String url, final SentimentAnalysisLanguage language) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getUrlBackendCaller(url, language));
    }

    @Override
    public SentimentAnalysisResponse analyzeSentimentForUrl(final TokenProxy<?, TokenType.Simple> tokenProxy, final String url, final SentimentAnalysisLanguage language) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getUrlBackendCaller(url, language));
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getUrlBackendCaller(final String url, final SentimentAnalysisLanguage language) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return sentimentAnalysisBackend.analyzeSentimentForUrl(authenticationToken, url, language);
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getTextBackendCaller(final String text, final SentimentAnalysisLanguage language) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return sentimentAnalysisBackend.analyzeSentimentForText(authenticationToken, text, language);
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getFileBackendCaller(final File file, final SentimentAnalysisLanguage language) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return sentimentAnalysisBackend.analyzeSentimentForFile(authenticationToken, new TypedFile("text/plain", file), language);
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getByteArrayBackendCaller(final byte[] file, final SentimentAnalysisLanguage language) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return sentimentAnalysisBackend.analyzeSentimentForFile(authenticationToken, new TypedByteArrayWithFilename("text/plain", file), language);
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getInputStreamBackendCaller(final InputStream file, final SentimentAnalysisLanguage language) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                try {
                    return sentimentAnalysisBackend.analyzeSentimentForFile(authenticationToken, new TypedByteArrayWithFilename("text/plain", IOUtils.toByteArray(file)), language);
                } catch (final IOException e) {
                    throw new RuntimeException("Error reading bytes from stream", e);
                }
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getReferenceBackendCaller(final String reference, final SentimentAnalysisLanguage language) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return sentimentAnalysisBackend.analyzeSentimentForReference(authenticationToken, reference, language);
            }
        };
    }
}
