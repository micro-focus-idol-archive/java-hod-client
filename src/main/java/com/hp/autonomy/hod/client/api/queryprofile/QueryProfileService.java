/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

/**
 * Service for manipulating HP Haven OnDemand Query Profiles
 */
public interface QueryProfileService {

    /**
     * Create a query profile in HP Haven OnDemand for a specified query manipulation index using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param name The name of the new query profile
     * @param queryManipulationIndex The name of the query manipulation index
     * @param params Configuration parameters for the query profile
     * @return The name of the created query profile
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryProfileStatusResponse createQueryProfile(
        String name,
        String queryManipulationIndex,
        QueryProfileRequestBuilder params
    ) throws HodErrorException;

    /**
     * Create a query profile in HP Haven OnDemand for a specified query manipulation index using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param name The name of the new query profile
     * @param queryManipulationIndex The name of the query manipulation index
     * @param params Configuration parameters for the query profile
     * @return The name of the created query profile
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryProfileStatusResponse createQueryProfile(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String name,
        String queryManipulationIndex,
        QueryProfileRequestBuilder params
    ) throws HodErrorException;

    /**
     * Retrieves a query profile using a token proxy provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param queryProfile The domain and name of the query profile
     * @return The query profile
     * @throws HodErrorException
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryProfile retrieveQueryProfile(
        ResourceName queryProfile
    ) throws HodErrorException;

    /**
     * Retrieves a query profile using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param queryProfile The domain and name of the query profile
     * @return The query profile
     * @throws HodErrorException
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryProfile retrieveQueryProfile(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        ResourceName queryProfile
    ) throws HodErrorException;

    /**
     * Update a query profile in HP Haven OnDemand for a specified query manipulation index using the given token proxy
     * @param queryProfile The domain and name of the query profile
     * @param queryManipulationIndex The name of the new query manipulation index; if null then this is not updated
     * @param params Configuration parameters for the query profile to update
     * @return The name of the created query profile
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryProfileStatusResponse updateQueryProfile(
        ResourceName queryProfile,
        String queryManipulationIndex,
        QueryProfileRequestBuilder params
    ) throws HodErrorException;

    /**
     * Update a query profile in HP Haven OnDemand for a specified query manipulation index using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param tokenProxy The token proxy to use
     * @param queryProfile The domain and name of the query profile to update
     * @param queryManipulationIndex The name of the new query manipulation index; if null then this is not updated
     * @param params Configuration parameters for the query profile
     * @return The name of the created query profile
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryProfileStatusResponse updateQueryProfile(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        ResourceName queryProfile,
        String queryManipulationIndex,
        QueryProfileRequestBuilder params
    ) throws HodErrorException;

    /**
     * Delete an existing query profile in HP Haven OnDemand using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param queryProfile The domain and name of the query profile to delete
     * @return The name of the deleted query profile
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryProfileStatusResponse deleteQueryProfile(
        ResourceName queryProfile
    ) throws HodErrorException;

    /**
     * Delete an existing query profile in HP Haven OnDemand using the given token
     * @param tokenProxy The token proxy to use
     * @param queryProfile The domain and name of the query profile to delete
     * @return The name of the deleted query profile
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    QueryProfileStatusResponse deleteQueryProfile(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        ResourceName queryProfile
    ) throws HodErrorException;

}
