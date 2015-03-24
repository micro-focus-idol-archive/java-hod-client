/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.util;

import retrofit.RequestInterceptor;

/**
 * Request interceptor for automatically adding the given API key to requests
 *
 * If you need to send Multipart requests to IDOL OnDemand the DO NOT use this request interceptor as you will be unable to
 * poll for job statuses
 */
public class ApiKeyRequestInterceptor implements RequestInterceptor {

    private final String apiKey;

    public ApiKeyRequestInterceptor(final String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void intercept(final RequestFacade request) {
        request.addQueryParam("apiKey", apiKey);
    }
}
