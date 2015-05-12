/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.util;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import retrofit.RequestInterceptor;

/**
 * Request interceptor for automatically adding an API key from an API key service to requests
 * <p/>
 * If you need to send Multipart requests to HP Haven OnDemand DO NOT use this request interceptor as you will be unable to
 * poll for job statuses
 */
public class AuthenticationTokenServiceRequestInterceptor implements RequestInterceptor {

    private final AuthenticationTokenService authenticationTokenService;

    public AuthenticationTokenServiceRequestInterceptor(final AuthenticationTokenService authenticationTokenService) {
        this.authenticationTokenService = authenticationTokenService;
    }

    @Override
    public void intercept(final RequestFacade request) {
        final AuthenticationToken token = authenticationTokenService.getToken();

        if (token != null) {
            request.addHeader("token", token.toString());
        }
    }
}
