/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

public interface ResourcesService {

    /**
     * Query HP Haven OnDemand for the list of resources using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param parameters Request parameters
     * @return Public and private resources
     */
    Resources list(ListResourcesRequestBuilder parameters) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for the list of resources using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param parameters Request parameters
     * @return Public and private resources
     */
    Resources list(TokenProxy tokenProxy, ListResourcesRequestBuilder parameters) throws HodErrorException;

}
