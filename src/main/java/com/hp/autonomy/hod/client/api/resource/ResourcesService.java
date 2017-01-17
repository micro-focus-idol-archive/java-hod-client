/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.util.List;

/**
 * Service for calling the Resources API.
 */
public interface ResourcesService {

    /**
     * Query HP Haven OnDemand for the list of resources using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param parameters Request parameters
     * @return List of HOD resources
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    List<Resource> list(ListResourcesRequestBuilder parameters) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for the list of resources using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param parameters Request parameters
     * @return List of HOD resources
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    List<Resource> list(TokenProxy<?, TokenType.Simple> tokenProxy, ListResourcesRequestBuilder parameters) throws HodErrorException;

}
