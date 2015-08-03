package com.hp.autonomy.hod.client.api.analysis.extractstructure;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.util.TypedByteArrayWithFilename;
import org.apache.commons.io.IOUtils;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedInput;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;

public class ExtractStructureServiceImpl implements ExtractStructureService
{
    private final ExtractStructureBackend extractStructureBackend;
    private final Requester requester;

    /**
     * Create a new ExtractStructureServiceImpl with the given configuration
     * @param hodServiceConfig The configuration to use
     */
    public ExtractStructureServiceImpl(final HodServiceConfig hodServiceConfig) {
        extractStructureBackend = hodServiceConfig.getRestAdapter().create(ExtractStructureBackend.class);
        requester = hodServiceConfig.getRequester();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromFile(final byte[] bytes) throws HodErrorException
    {
        final Structure structure = requester.makeRequest(Structure.class, getByteArrayBackendCaller(bytes));
        return structure.getContent();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromFile(final TokenProxy tokenProxy, final byte[] bytes) throws HodErrorException
    {
        final Structure structure = requester.makeRequest(tokenProxy, Structure.class, getByteArrayBackendCaller(bytes));
        return structure.getContent();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromFile(final InputStream inputStream) throws HodErrorException
    {
        final Structure structure = requester.makeRequest(Structure.class, getInputStreamBackendCaller(inputStream));
        return structure.getContent();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromFile(final TokenProxy tokenProxy, final InputStream inputStream) throws HodErrorException
    {
        final Structure structure = requester.makeRequest(tokenProxy, Structure.class, getInputStreamBackendCaller(inputStream));
        return structure.getContent();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromFile(final File file) throws HodErrorException
    {
        final Structure structure = requester.makeRequest(Structure.class, getFileBackendCaller(file));
        return structure.getContent();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromFile(final TokenProxy tokenProxy, final File file) throws HodErrorException
    {
        final Structure structure = requester.makeRequest(tokenProxy, Structure.class, getFileBackendCaller(file));
        return structure.getContent();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromReference(final TokenProxy tokenProxy, final String reference) throws HodErrorException
    {
        final Structure structure = requester.makeRequest(tokenProxy, Structure.class, getReferenceBackendCaller(reference));
        return structure.getContent();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromReference(final String reference) throws HodErrorException
    {
        final Structure structure = requester.makeRequest(Structure.class, getReferenceBackendCaller(reference));
        return structure.getContent();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromUrl(final TokenProxy tokenProxy, final String url) throws HodErrorException
    {
        final Structure structure = requester.makeRequest(tokenProxy, Structure.class, getUrlBackendCaller(url));
        return structure.getContent();
    }

    @Override
    public List<LinkedHashMap<String, String>> extractFromUrl(final String url) throws HodErrorException
    {
        final Structure structure = requester.makeRequest(Structure.class, getUrlBackendCaller(url));
        return structure.getContent();
    }

    private Requester.BackendCaller getByteArrayBackendCaller(final byte[] file) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return extractStructureBackend.extractFromFile(authenticationToken, new TypedByteArrayWithFilename("text/plain", file));
            }
        };
    }

    private Requester.BackendCaller getInputStreamBackendCaller(final InputStream inputStream)
    {
        return new Requester.BackendCaller()
        {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException
            {
                try
                {
                    return extractStructureBackend.extractFromFile(authenticationToken, new TypedByteArrayWithFilename("text/plain", IOUtils.toByteArray(inputStream)));
                } catch (final IOException e)
                {
                    throw new RuntimeException("Error reading bytes from stream", e);
                }
            }
        };
    }

    private Requester.BackendCaller getFileBackendCaller(final File file) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return extractStructureBackend.extractFromFile(authenticationToken, new TypedFile("text/plain", file));
            }
        };
    }

    private Requester.BackendCaller getReferenceBackendCaller(final String reference) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return extractStructureBackend.extractFromReference(authenticationToken, reference);
            }
        };
    }

    private Requester.BackendCaller getUrlBackendCaller(final String url) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return extractStructureBackend.extractFromUrl(authenticationToken, url);
            }
        };
    }
}
