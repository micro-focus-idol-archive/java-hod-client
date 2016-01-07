/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.util;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Represents a request to HP Haven OnDemand.
 */
@Data
public class Request<Q, B> {
    private final Verb verb;
    private final String path;
    private final Map<String, List<Q>> queryParameters;
    private final Map<String, List<B>> body;

    public Request(final Verb verb, final String path, final Map<String, List<Q>> queryParameters, final Map<String, List<B>> body) {
        if (verb == null || path == null) {
            throw new IllegalArgumentException("Verb and path are mandatory");
        }

        this.verb = verb;
        this.path = path;
        this.queryParameters = queryParameters;
        this.body = body;
    }

    public enum Verb {
        // These must be uppercase or HMAC signing will fail
        GET, POST, PUT, DELETE
    }
}
