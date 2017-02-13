package com.hp.autonomy.hod.client.api.textindex.query.parametric;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.util.Request;

import java.util.Collection;
import java.util.List;

/**
 * Default implementation of a GetParametricRangesService.
 */
public class GetParametricRangesServiceImpl implements GetParametricRangesService {
    private static final TypeReference<FieldsResponse<FieldRanges>> RESPONSE_TYPE = new TypeReference<FieldsResponse<FieldRanges>>() {};

    private final GetParametricRangesBackend backend;
    private final Requester<?, TokenType.Simple> requester;

    /**
     * Construct a new GetParametricServiceImpl using the given config.
     * @param hodServiceConfig HOD configuration
     */
    public GetParametricRangesServiceImpl(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig) {
        backend = hodServiceConfig.getRestAdapter().create(GetParametricRangesBackend.class);
        requester = hodServiceConfig.getRequester();
    }

    @Override
    public List<FieldRanges> getParametricRanges(
            final Collection<String> fieldNames,
            final Collection<ResourceName> indexes,
            final String ranges,
            final GetParametricRangesRequestBuilder params
    ) throws HodErrorException {
        return requester.makeRequest(RESPONSE_TYPE, getBackendCaller(fieldNames, indexes, ranges, params)).getFields();
    }

    @Override
    public List<FieldRanges> getParametricRanges(
            final TokenProxy<?, TokenType.Simple> tokenProxy,
            final Collection<String> fieldNames,
            final Collection<ResourceName> indexes,
            final String ranges,
            final GetParametricRangesRequestBuilder params
    ) throws HodErrorException {
        return requester.makeRequest(tokenProxy, RESPONSE_TYPE, getBackendCaller(fieldNames, indexes, ranges, params)).getFields();
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getBackendCaller(
            final Collection<String> fieldNames,
            final Collection<ResourceName> indexes,
            final String ranges,
            final GetParametricRangesRequestBuilder params
    ) {
        return authenticationToken -> backend.getParametricRanges(authenticationToken, fieldNames, indexes, ranges, params.build());
    }
}
