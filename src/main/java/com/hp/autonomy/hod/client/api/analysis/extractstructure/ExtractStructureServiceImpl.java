/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.analysis.extractstructure;

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
import java.util.LinkedHashMap;
import java.util.List;

public class ExtractStructureServiceImpl implements ExtractStructureService {
    private final ExtractStructureBackend extractStructureBackend;
    private final Requester<?, TokenType.Simple> requester;

    /**
     * Create a new ExtractStructureServiceImpl with the given configuration
     * @param hodServiceConfig The configuration to use
     */
    public ExtractStructureServiceImpl(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig) {
        extractStructureBackend = hodServiceConfig.getRestAdapter().create(ExtractStructureBackend.class);
        requester = hodServiceConfig.getRequester();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromFile(final byte[] bytes) throws HodErrorException {
        final Structure structure = requester.makeRequest(Structure.class, getByteArrayBackendCaller(bytes));
        return structure.getContent();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromFile(final TokenProxy<?, TokenType.Simple> tokenProxy, final byte[] bytes) throws HodErrorException {
        final Structure structure = requester.makeRequest(tokenProxy, Structure.class, getByteArrayBackendCaller(bytes));
        return structure.getContent();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromFile(final InputStream inputStream) throws HodErrorException {
        final Structure structure = requester.makeRequest(Structure.class, getInputStreamBackendCaller(inputStream));
        return structure.getContent();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromFile(final TokenProxy<?, TokenType.Simple> tokenProxy, final InputStream inputStream) throws HodErrorException {
        final Structure structure = requester.makeRequest(tokenProxy, Structure.class, getInputStreamBackendCaller(inputStream));
        return structure.getContent();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromFile(final File file) throws HodErrorException {
        final Structure structure = requester.makeRequest(Structure.class, getFileBackendCaller(file));
        return structure.getContent();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromFile(final TokenProxy<?, TokenType.Simple> tokenProxy, final File file) throws HodErrorException {
        final Structure structure = requester.makeRequest(tokenProxy, Structure.class, getFileBackendCaller(file));
        return structure.getContent();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromReference(final TokenProxy<?, TokenType.Simple> tokenProxy, final String reference) throws HodErrorException {
        final Structure structure = requester.makeRequest(tokenProxy, Structure.class, getReferenceBackendCaller(reference));
        return structure.getContent();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromReference(final String reference) throws HodErrorException {
        final Structure structure = requester.makeRequest(Structure.class, getReferenceBackendCaller(reference));
        return structure.getContent();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromUrl(final TokenProxy<?, TokenType.Simple> tokenProxy, final String url) throws HodErrorException {
        final Structure structure = requester.makeRequest(tokenProxy, Structure.class, getUrlBackendCaller(url));
        return structure.getContent();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromUrl(final String url) throws HodErrorException {
        final Structure structure = requester.makeRequest(Structure.class, getUrlBackendCaller(url));
        return structure.getContent();
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getByteArrayBackendCaller(final byte[] file) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return extractStructureBackend.extractFromFile(authenticationToken, new TypedByteArrayWithFilename("text/plain", file));
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getInputStreamBackendCaller(final InputStream inputStream) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                try {
                    return extractStructureBackend.extractFromFile(authenticationToken, new TypedByteArrayWithFilename("text/plain", IOUtils.toByteArray(inputStream)));
                } catch (final IOException e) {
                    throw new RuntimeException("Error reading bytes from stream", e);
                }
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getFileBackendCaller(final File file) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return extractStructureBackend.extractFromFile(authenticationToken, new TypedFile("text/plain", file));
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getReferenceBackendCaller(final String reference) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return extractStructureBackend.extractFromReference(authenticationToken, reference);
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getUrlBackendCaller(final String url) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return extractStructureBackend.extractFromUrl(authenticationToken, url);
            }
        };
    }
}
