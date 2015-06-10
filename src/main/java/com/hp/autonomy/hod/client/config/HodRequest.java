/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.config;

import lombok.AccessLevel;
import lombok.Getter;
import retrofit.RequestInterceptor;

/**
 * Abstraction representing an HTTP request
 */
@Getter(value = AccessLevel.PACKAGE)
public class HodRequest {

    private final RequestInterceptor.RequestFacade requestFacade;

    HodRequest(final RequestInterceptor.RequestFacade requestFacade) {
        this.requestFacade = requestFacade;
    }

    /**
     * Adds a header to the HTTP request. This will not replace any existing headers
     * @param name The name of the header
     * @param value The value of the header
     */
    public void addHeader(final String name, final String value) {
        requestFacade.addHeader(name, value);
    }

    /**
     * Adds a query parameter to the HTTP request. This will not replace any existing query parameters
     * @param name The name of the query parameter
     * @param value The value of the query parameter
     */
    public void addQueryParam(final String name, final String value) {
        requestFacade.addQueryParam(name, value);
    }

    /**
     * Adds a query parameter to the HTTP request. This will not replace any existing query parameters. It is assumed
     * the parameter has already been appropriately URI encoded
     * @param name The name of the query parameter
     * @param value The value of the query parameter
     */
    public void addEncodedQueryParam(final String name, final String value) {
        requestFacade.addEncodedQueryParam(name, value);
    }
}
