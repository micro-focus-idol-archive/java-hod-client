/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.parametric;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Default implementation of GetParametricValuesService
 */
public class GetParametricValuesServiceImpl implements GetParametricValuesService {
    private final GetParametricValuesBackend getParametricValuesBackend;
    private final Requester<?, TokenType.Simple> requester;
    private final ObjectMapper objectMapper;

    /**
     * Creates a new GetParametricValuesServiceImpl with the given configuration
     *
     * @param hodServiceConfig The configuration to use
     */
    public GetParametricValuesServiceImpl(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig) {
        getParametricValuesBackend = hodServiceConfig.getRestAdapter().create(GetParametricValuesBackend.class);
        requester = hodServiceConfig.getRequester();
        objectMapper = hodServiceConfig.getObjectMapper();
    }

    @Override
    public List<FieldValues> getParametricValues(
            final Collection<String> fieldNames,
            final Collection<ResourceName> indexes,
            final GetParametricValuesRequestBuilder params
    ) throws HodErrorException {
        final JsonNode response = requester.makeRequest(JsonNode.class, getBackendCaller(fieldNames, indexes, params));
        return parseResponse(response);
    }

    @Override
    public List<FieldValues> getParametricValues(
            final TokenProxy<?, TokenType.Simple> tokenProxy,
            final Collection<String> fieldNames,
            final Collection<ResourceName> indexes,
            final GetParametricValuesRequestBuilder params
    ) throws HodErrorException {
        final JsonNode response = requester.makeRequest(tokenProxy, JsonNode.class, getBackendCaller(fieldNames, indexes, params));
        return parseResponse(response);
    }

    /*
     * HOD has a bug (HOD-5731) where, if a query profile is provided, GetParametricValues returns an object with number
      * keys rather than an array. This parses a JSON node of either type.
     */
    private List<FieldValues> parseResponse(final JsonNode response) {
        if (response == null || response.get("fields") == null) {
            throw new RuntimeException("Failed to parse JSON");
        }

        final JsonNode fieldsNode = response.get("fields");

        if (fieldsNode.getNodeType() == JsonNodeType.OBJECT || fieldsNode.getNodeType() == JsonNodeType.ARRAY) {
            final List<FieldValues> output = new ArrayList<>();

            for (final JsonNode node : fieldsNode) {
                try {
                    final FieldValues fieldValues = objectMapper.treeToValue(node, FieldValues.class);
                    output.add(fieldValues);
                } catch (final JsonProcessingException e) {
                    throw new RuntimeException("Failed to parse JSON", e);
                }
            }

            return output;
        } else {
            throw new RuntimeException("Failed to parse JSON");
        }
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> getBackendCaller(
            final Collection<String> fieldNames,
            final Collection<ResourceName> indexes,
            final GetParametricValuesRequestBuilder params
    ) {
        return authenticationToken -> getParametricValuesBackend.getParametricValues(authenticationToken, fieldNames, indexes, params.build());
    }
}
