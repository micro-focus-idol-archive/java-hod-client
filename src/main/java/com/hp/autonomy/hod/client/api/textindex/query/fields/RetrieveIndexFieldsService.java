/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.fields;

import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

public interface RetrieveIndexFieldsService {

    /**
     * Retrieve from HP Haven OnDemand a list of the fields that have been indexed
     * into a given text index using a token proxy provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param index The index to retrieve fields from
     * @param params Parameters to be sent as part of the request
     * @return A list of fields and their types
     */
    RetrieveIndexFieldsResponse retrieveIndexFields(
        String index,
        RetrieveIndexFieldsRequestBuilder params
    ) throws HodErrorException;

    /**
     * Retrieve from HP Haven OnDemand a list of the fields that have been indexed
     * into a given text index using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param index The index to retrieve fields from
     * @param params Parameters to be sent as part of the request
     * @return A list of fields and their types
     */
    RetrieveIndexFieldsResponse retrieveIndexFields(
        TokenProxy tokenProxy,
        String index,
        RetrieveIndexFieldsRequestBuilder params
    ) throws HodErrorException;

}
