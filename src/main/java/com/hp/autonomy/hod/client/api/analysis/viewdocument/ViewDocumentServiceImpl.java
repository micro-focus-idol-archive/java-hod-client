/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.analysis.viewdocument;

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
import java.util.Map;

/**
 * Default implementation of ViewDocumentService
 */
public class ViewDocumentServiceImpl implements ViewDocumentService {

    private static final Class<ViewDocumentResponse> RESPONSE_CLASS = ViewDocumentResponse.class;
    private static final String MIME_TYPE = "application/octet-stream";

    private final ViewDocumentBackend viewDocumentBackend;
    private final Requester<?, TokenType.Simple> requester;

    /**
     * Creates a new ViewDocumentServiceImpl
     * @param hodServiceConfig The configuration to use
     */
    public ViewDocumentServiceImpl(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig) {
        viewDocumentBackend = hodServiceConfig.getRestAdapter().create(ViewDocumentBackend.class);
        requester = hodServiceConfig.getRequester();
    }

    @Override
    public InputStream viewFile(final File file, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(getFileBackendCaller(file, params));
    }

    @Override
    public InputStream viewFile(final TokenProxy<?, TokenType.Simple> tokenProxy, final File file, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, getFileBackendCaller(file, params));
    }

    @Override
    public InputStream viewFile(final byte[] bytes, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(getByteArrayBackendCaller(bytes, params));
    }

    @Override
    public InputStream viewFile(final TokenProxy<?, TokenType.Simple> tokenProxy, final byte[] bytes, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, getByteArrayBackendCaller(bytes, params));
    }

    @Override
    public InputStream viewFile(final InputStream inputStream, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(getInputStreamBackendCaller(inputStream, params));
    }

    @Override
    public InputStream viewFile(final TokenProxy<?, TokenType.Simple> tokenProxy, final InputStream inputStream, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, getInputStreamBackendCaller(inputStream, params));
    }

    @Override
    public InputStream viewReference(final String reference, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(getReferenceBackendCaller(reference, params));
    }

    @Override
    public InputStream viewReference(final TokenProxy<?, TokenType.Simple> tokenProxy, final String reference, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, getReferenceBackendCaller(reference, params));
    }

    @Override
    public InputStream viewUrl(final String url, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(getUrlBackendCaller(url, params));
    }

    @Override
    public InputStream viewUrl(final TokenProxy<?, TokenType.Simple> tokenProxy, final String url, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, getUrlBackendCaller(url, params));
    }

    @Override
    public String viewFileAsHtmlString(final File file, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getFileAsStringBackendCaller(file, params)).getDocument();
    }

    @Override
    public String viewFileAsHtmlString(final TokenProxy<?, TokenType.Simple> tokenProxy, final File file, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getFileAsStringBackendCaller(file, params)).getDocument();
    }

    @Override
    public String viewFileAsHtmlString(final byte[] bytes, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getByteArrayAsStringBackendCaller(bytes, params)).getDocument();
    }

    @Override
    public String viewFileAsHtmlString(final TokenProxy<?, TokenType.Simple> tokenProxy, final byte[] bytes, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getByteArrayAsStringBackendCaller(bytes, params)).getDocument();
    }

    @Override
    public String viewFileAsHtmlString(final InputStream inputStream, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getInputStreamAsFileBackendCaller(inputStream, params)).getDocument();
    }

    @Override
    public String viewFileAsHtmlString(final TokenProxy<?, TokenType.Simple> tokenProxy, final InputStream inputStream, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getInputStreamAsFileBackendCaller(inputStream, params)).getDocument();
    }

    @Override
    public String viewReferenceAsHtmlString(final String reference, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getReferenceAsStringBackendCaller(reference, params)).getDocument();
    }

    @Override
    public String viewReferenceAsHtmlString(final TokenProxy<?, TokenType.Simple> tokenProxy, final String reference, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getReferenceAsStringBackendCaller(reference, params)).getDocument();
    }

    @Override
    public String viewUrlAsHtmlString(final String url, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(RESPONSE_CLASS, getUrlAsStringBackendCaller(url, params)).getDocument();
    }

    @Override
    public String viewUrlAsHtmlString(final TokenProxy<?, TokenType.Simple> tokenProxy, final String url, final ViewDocumentRequestBuilder params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_CLASS, getUrlAsStringBackendCaller(url, params)).getDocument();
    }

    private Map<String, Object> getRawHtmlParams(final ViewDocumentRequestBuilder requestBuilder) {
        requestBuilder.setRawHtml(false);

        return requestBuilder.build();
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getFileBackendCaller(final File file, final ViewDocumentRequestBuilder params) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return viewDocumentBackend.viewFile(authenticationToken, new TypedFile(MIME_TYPE, file), params.build());
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getByteArrayBackendCaller(final byte[] bytes, final ViewDocumentRequestBuilder params) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return viewDocumentBackend.viewFile(authenticationToken, new TypedByteArrayWithFilename(MIME_TYPE, bytes), params.build());
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getInputStreamBackendCaller(final InputStream inputStream, final ViewDocumentRequestBuilder params) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                try {
                    return viewDocumentBackend.viewFile(authenticationToken, new TypedByteArrayWithFilename(MIME_TYPE, IOUtils.toByteArray(inputStream)), params.build());
                } catch (final IOException e) {
                    throw new RuntimeException("Error reading bytes from stream", e);
                }
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getReferenceBackendCaller(final String reference, final ViewDocumentRequestBuilder params) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return viewDocumentBackend.viewReference(authenticationToken, reference, params.build());
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getUrlBackendCaller(final String url, final ViewDocumentRequestBuilder params) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return viewDocumentBackend.viewUrl(authenticationToken, url, params.build());
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getFileAsStringBackendCaller(final File file, final ViewDocumentRequestBuilder params) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return viewDocumentBackend.viewFile(authenticationToken, new TypedFile(MIME_TYPE, file), getRawHtmlParams(params));
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getByteArrayAsStringBackendCaller(final byte[] bytes, final ViewDocumentRequestBuilder params) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return viewDocumentBackend.viewFile(authenticationToken, new TypedByteArrayWithFilename(MIME_TYPE, bytes), getRawHtmlParams(params));
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getInputStreamAsFileBackendCaller(final InputStream inputStream, final ViewDocumentRequestBuilder params) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                try {
                    return viewDocumentBackend.viewFile(authenticationToken, new TypedByteArrayWithFilename(MIME_TYPE, IOUtils.toByteArray(inputStream)), getRawHtmlParams(params));
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getReferenceAsStringBackendCaller(final String reference, final ViewDocumentRequestBuilder params) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return viewDocumentBackend.viewReference(authenticationToken, reference, getRawHtmlParams(params));
            }
        };
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getUrlAsStringBackendCaller(final String url, final ViewDocumentRequestBuilder params) {
        return new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return viewDocumentBackend.viewUrl(authenticationToken, url, getRawHtmlParams(params));
            }
        };
    }
}
