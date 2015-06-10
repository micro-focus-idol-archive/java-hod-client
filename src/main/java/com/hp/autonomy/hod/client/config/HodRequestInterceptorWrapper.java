/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.config;

import com.hp.autonomy.hod.client.util.HodRequestInterceptor;
import retrofit.RequestInterceptor;

class HodRequestInterceptorWrapper implements RequestInterceptor {

    private final HodRequestInterceptor hodRequestInterceptor;

    HodRequestInterceptorWrapper(final HodRequestInterceptor hodRequestInterceptor) {
        this.hodRequestInterceptor = hodRequestInterceptor;
    }

    @Override
    public void intercept(final RequestFacade request) {
        hodRequestInterceptor.intercept(new HodRequest(request));
    }
}
