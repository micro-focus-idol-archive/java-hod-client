/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex;

import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.HodJobCallback;
import com.hp.autonomy.hod.client.token.TokenProxy;

/**
 * Service representing the DeleteTextIndex API
 */
public interface DeleteTextIndexService {

    /**
     * Deletes the given text index using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}.
     * This API handles the confirm token returned by HP Haven OnDemand automatically.
     * @param index The name of the index
     * @param callback Callback that will be called with the response
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws HodErrorException If an error occurs
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    void deleteTextIndex(
        ResourceIdentifier index,
        HodJobCallback<DeleteTextIndexResponse> callback
    ) throws HodErrorException;

    /**
     * Deletes the given text index using the given token proxy.
     * This API handles the confirm token returned by HP Haven OnDemand automatically.
     * @param tokenProxy The token proxy to use
     * @param index The name of the index
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    void deleteTextIndex(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        ResourceIdentifier index,
        HodJobCallback<DeleteTextIndexResponse> callback
    ) throws HodErrorException;

}
