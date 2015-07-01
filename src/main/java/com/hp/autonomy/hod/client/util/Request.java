package com.hp.autonomy.hod.client.util;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Request {
    private final Verb verb;
    private final String path;
    private final Map<String, List<?>> queryParameters;
    private final Map<String, List<?>> body;

    public Request(final Verb verb, final String path, final Map<String, List<?>> queryParameters, final Map<String, List<?>> body) {
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
