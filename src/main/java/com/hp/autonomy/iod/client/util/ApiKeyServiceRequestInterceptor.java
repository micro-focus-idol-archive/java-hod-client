/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.util;

import retrofit.RequestInterceptor;

/**
 * Request interceptor for automatically adding an API key from an API key service to requests
 *
 * If you need to send Multipart requests to IDOL OnDemand the DO NOT use this request interceptor as you will be unable to
 * poll for job statuses
 */
public class ApiKeyServiceRequestInterceptor implements RequestInterceptor {

    private final ApiKeyService apiKeyService;

    public ApiKeyServiceRequestInterceptor(final ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @Override
    public void intercept(final RequestFacade request) {
        request.addQueryParam("apiKey", apiKeyService.getApiKey());
    }
}
