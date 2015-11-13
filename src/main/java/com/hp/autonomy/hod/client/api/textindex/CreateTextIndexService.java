/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex;

import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.HodJobCallback;
import com.hp.autonomy.hod.client.token.TokenProxy;

/**
 * Service representing the CreateTextIndex API
 */
public interface CreateTextIndexService {

    /**
     * Create a text index using a token provided by a {@link retrofit.RequestInterceptor}
     * @param index The name of the index
     * @param flavor The flavor of the index
     * @param params Additional parameters used to create the index
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
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
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    void createTextIndex(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String index,
        IndexFlavor flavor,
        CreateTextIndexRequestBuilder params,
        HodJobCallback<CreateTextIndexResponse> callback
    ) throws HodErrorException;
}
