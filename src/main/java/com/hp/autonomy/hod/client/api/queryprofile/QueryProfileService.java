/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

public interface QueryProfileService {

    /**
     * Create a query profile in HP Haven OnDemand for a specified query manipulation index using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param name The name of the query profile
     * @param params Configuration parameters for the query profile
     * @return The name of the created query profile
     */
    QueryProfileStatusResponse createQueryProfile(
        String name,
        String queryManipulationIndex,
        QueryProfileRequestBuilder params
    ) throws HodErrorException;

    /**
     * Create a query profile in HP Haven OnDemand for a specified query manipulation index using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param name The name of the query profile
     * @param params Configuration parameters for the query profile
     * @return The name of the created query profile
     */
    QueryProfileStatusResponse createQueryProfile(
        TokenProxy tokenProxy,
        String name,
        String queryManipulationIndex,
        QueryProfileRequestBuilder params
    ) throws HodErrorException;

    /**
     * Retrieves a query profile using a token proxy provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param name The name of the query profile
     * @return The query profile
     * @throws HodErrorException
     */
    QueryProfile retrieveQueryProfile(
        String name
    ) throws HodErrorException;

    /**
     * Retrieves a query profile using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param name The name of the query profile
     * @return The query profile
     * @throws HodErrorException
     */
    QueryProfile retrieveQueryProfile(
        TokenProxy tokenProxy,
        String name
    ) throws HodErrorException;

    /**
     * Update a query profile in HP Haven OnDemand for a specified query manipulation index using the given token proxy
     * @param name The name of the query profile
     * @param params Configuration parameters for the query profile
     * @return The name of the created query profile
     */
    QueryProfileStatusResponse updateQueryProfile(
        String name,
        String queryManipulationIndex,
        QueryProfileRequestBuilder params
    ) throws HodErrorException;

    /**
     * Update a query profile in HP Haven OnDemand for a specified query manipulation index using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param tokenProxy The token proxy to use
     * @param name The name of the query profile
     * @param params Configuration parameters for the query profile
     * @return The name of the created query profile
     */
    QueryProfileStatusResponse updateQueryProfile(
        TokenProxy tokenProxy,
        String name,
        String queryManipulationIndex,
        QueryProfileRequestBuilder params
    ) throws HodErrorException;

    /**
     * Delete an existing query profile in HP Haven OnDemand using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param name The name of the query profile
     * @return The name of the deleted query profile
     */
    QueryProfileStatusResponse deleteQueryProfile(
        String name
    ) throws HodErrorException;

    /**
     * Delete an existing query profile in HP Haven OnDemand using the given token
     * @param tokenProxy The token proxy to use
     * @param name The name of the query profile
     * @return The name of the deleted query profile
     */
    QueryProfileStatusResponse deleteQueryProfile(
        TokenProxy tokenProxy,
        String name
    ) throws HodErrorException;

    /**
     * Gets the names of the query profiles that currently exist using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @return  A QueryProfiles response object, that has a util method on it for getting the actual names.
     * @throws HodErrorException
     */
    QueryProfiles listQueryProfiles() throws HodErrorException;

    /**
     * Gets the names of the query profiles that currently exist using the given token
     * @param tokenProxy  The token proxy to use
     * @return  A QueryProfiles response object, that has a util method on it for getting the actual names.
     * @throws HodErrorException
     */
    QueryProfiles listQueryProfiles(
        TokenProxy tokenProxy
    ) throws HodErrorException;

}
