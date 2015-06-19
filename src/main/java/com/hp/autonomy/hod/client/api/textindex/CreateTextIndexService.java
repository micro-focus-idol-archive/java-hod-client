/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex;

import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.HodJobCallback;
import com.hp.autonomy.hod.client.token.TokenProxy;

public interface CreateTextIndexService {

    /**
     * Create a text index using a token provided by a {@link retrofit.RequestInterceptor}
     * @param index The name of the index
     * @param flavor The flavor of the index
     * @param params Additional parameters used to create the index
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs
     */
    void createTextIndex(
        String index,
        IndexFlavor flavor,
        CreateTextIndexRequestBuilder params,
        HodJobCallback<CreateTextIndexResponse> callback
    ) throws HodErrorException;

    /**
     * Create a text index using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param index The name of the index
     * @param flavor The flavor of the index
     * @param params  Additional parameters used to create the index
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs
     */
    void createTextIndex(
        TokenProxy tokenProxy,
        String index,
        IndexFlavor flavor,
        CreateTextIndexRequestBuilder params,
        HodJobCallback<CreateTextIndexResponse> callback
    ) throws HodErrorException;
}
