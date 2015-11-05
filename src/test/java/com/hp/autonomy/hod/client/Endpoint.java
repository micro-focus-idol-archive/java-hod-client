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
    INTEGRATION(
        "https://api.int.havenondemand.com",
        System.getProperty("hp.hod.int.applicationApiKey"),
        System.getProperty("hp.hod.int.userApiKey"),
        System.getProperty("hp.hod.int.developerApiKey"),
        System.getProperty("hp.hod.int.developerEmail"),
        System.getProperty("hp.hod.int.application"),
        System.getProperty("hp.hod.int.domain"),
        System.getProperty("hp.hod.int.userStoreName", "DEFAULT_USER_STORE")
    ),
    PREVIEW(
        "https://api.preview.havenondemand.com",
        System.getProperty("hp.hod.preview.applicationApiKey"),
        System.getProperty("hp.hod.preview.userApiKey"),
        System.getProperty("hp.hod.preview.developerApiKey"),
        System.getProperty("hp.hod.preview.developerEmail"),
        System.getProperty("hp.hod.preview.application"),
        System.getProperty("hp.hod.preview.domain"),
        System.getProperty("hp.hod.preview.userStoreName", "DEFAULT_USER_STORE")
    ),
    STAGING(
        "https://api.staging.havenondemand.com",
        System.getProperty("hp.hod.staging.applicationApiKey"),
        System.getProperty("hp.hod.staging.userApiKey"),
        System.getProperty("hp.hod.staging.developerApiKey"),
        System.getProperty("hp.hod.staging.developerEmail"),
        System.getProperty("hp.hod.staging.application"),
        System.getProperty("hp.hod.staging.domain"),
        System.getProperty("hp.hod.staging.userStoreName", "DEFAULT_USER_STORE")
    ),
    PRODUCTION(
        "https://api.havenondemand.com",
        System.getProperty("hp.hod.applicationApiKey"),
        System.getProperty("hp.hod.userApiKey"),
        System.getProperty("hp.hod.developerApiKey"),
        System.getProperty("hp.hod.developerEmail"),
        System.getProperty("hp.hod.application"),
        System.getProperty("hp.hod.domain"),
        System.getProperty("hp.hod.userStoreName", "DEFAULT_USER_STORE")
    );

    private final String url;
    private final String applicationApiKey;
    private final String userApiKey;
    private final String developerApiKey;
    private final String developerEmail;
    private final String applicationName;
    private final String domainName;
    private final String userStoreName;

    Endpoint(
        final String url,
        final String applicationApiKey,
        final String userApiKey,
        final String developerApiKey,
        final String developerEmail,
        final String applicationName,
        final String domainName,
        final String userStoreName
    ) {
        this.url = url;
        this.applicationApiKey = applicationApiKey;
        this.userApiKey = userApiKey;
        this.developerApiKey = developerApiKey;
        this.developerEmail = developerEmail;
        this.applicationName = applicationName;
        this.domainName = domainName;
        this.userStoreName = userStoreName;
    }

    public String getUrl() {
        return url;
    }

    public ApiKey getApplicationApiKey() {
        return new ApiKey(applicationApiKey);
    }

    public ApiKey getUserApiKey() {
        return new ApiKey(userApiKey);
    }

    public ApiKey getDeveloperApiKey() {
        return new ApiKey(developerApiKey);
    }

    public String getDeveloperEmail() {
        return developerEmail;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getUserStoreName() {
        return userStoreName;
    }

    @Override
    public String toString() {
        return url;
    }
}
