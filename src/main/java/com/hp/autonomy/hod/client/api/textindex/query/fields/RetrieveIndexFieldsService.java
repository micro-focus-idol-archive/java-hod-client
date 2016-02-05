/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.fields;

import com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.util.Collection;
import java.util.Map;

/**
 * Service representing the RetrieveIndexFields API
 */
public interface RetrieveIndexFieldsService {

    /**
     * Retrieve from HP Haven OnDemand a list of the fields that have been indexed
     * into the specified text indexes using a token proxy provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * Returns results across all indexes in a single combined response.
     *
     * @param indexes The indexes to retrieve fields from
     * @param params  Parameters to be sent as part of the request
     * @return A list of fields per index and their types
     * @throws NullPointerException             If a TokenProxyService has not been defined
     * @throws HodAuthenticationFailedException If the token associated with the token proxy has expired
     */
    RetrieveIndexFieldsResponse retrieveIndexFields(
            final Collection<ResourceIdentifier> indexes,
            final RetrieveIndexFieldsRequestBuilder params
    ) throws HodErrorException;

    /**
     * Retrieve from HP Haven OnDemand a list of the fields that have been indexed
     * into the specified text indexes index using the given token proxy.
     * Returns results across all indexes in a single combined response.
     *
     * @param tokenProxy The token proxy to use
     * @param indexes    The indexes to retrieve fields from
     * @param params     Parameters to be sent as part of the request
     * @return A list of fields per index and their types
     * @throws HodAuthenticationFailedException If the token associated with the token proxy has expired
     */
    RetrieveIndexFieldsResponse retrieveIndexFields(
            final TokenProxy<?, TokenType.Simple> tokenProxy,
            final Collection<ResourceIdentifier> indexes,
            final RetrieveIndexFieldsRequestBuilder params
    ) throws HodErrorException;

    /**
     * Retrieve from HP Haven OnDemand a list of the fields that have been indexed
     * into the specified text indexes using a token proxy provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * Returns results per index.
     *
     * @param indexes The indexes to retrieve fields from
     * @param params  Parameters to be sent as part of the request
     * @return A list of fields per index and their types
     * @throws NullPointerException             If a TokenProxyService has not been defined
     * @throws HodAuthenticationFailedException If the token associated with the token proxy has expired
     */
    Map<String, RetrieveIndexFieldsResponse> retrieveIndexFieldsByIndex(
            final Collection<ResourceIdentifier> indexes,
            final RetrieveIndexFieldsRequestBuilder params
    ) throws HodErrorException;

    /**
     * Retrieve from HP Haven OnDemand a list of the fields that have been indexed
     * into the specified text indexes index using the given token proxy.
     * Returns results per index.
     *
     * @param tokenProxy The token proxy to use
     * @param indexes    The indexes to retrieve fields from
     * @param params     Parameters to be sent as part of the request
     * @return A list of fields per index and their types
     * @throws HodAuthenticationFailedException If the token associated with the token proxy has expired
     */
    Map<String, RetrieveIndexFieldsResponse> retrieveIndexFieldsByIndex(
            final TokenProxy<?, TokenType.Simple> tokenProxy,
            final Collection<ResourceIdentifier> indexes,
            final RetrieveIndexFieldsRequestBuilder params
    ) throws HodErrorException;

}
