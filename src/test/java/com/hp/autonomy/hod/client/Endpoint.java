/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client;

import com.hp.autonomy.hod.client.api.authentication.ApiKey;

/**
 * Enum type representing the possible endpoints for HP Haven OnDemand
 */
public enum Endpoint {
    INTEGRATION("https://api.int.havenondemand.com", System.getProperty("hp.int.hod.apiKey")),
    PREVIEW("https://api.preview.havenondemand.com", System.getProperty("hp.preview.hod.apiKey")),
    STAGING("https://api.staging.havenondemand.com", System.getProperty("hp.staging.hod.apiKey")),
    PRODUCTION("https://api.havenondemand.com", System.getProperty("hp.hod.apiKey"));

    private final String url;
    private final String apiKey;

    Endpoint(final String url, final String apiKey) {
        this.url = url;
        this.apiKey = apiKey;
    }

    public String getUrl() {
        return url;
    }

    public ApiKey getApiKey() {
        return new ApiKey(apiKey);
    }

    @Override
    public String toString() {
        return url;
    }
}
