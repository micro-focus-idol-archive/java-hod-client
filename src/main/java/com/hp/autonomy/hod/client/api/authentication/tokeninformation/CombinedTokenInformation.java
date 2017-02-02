/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication.tokeninformation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.resource.Resource;
import lombok.Data;

import java.util.UUID;

/**
 * Information about a combined token which can be obtained from the get token information API.
 */
@Data
public class CombinedTokenInformation {
    /**
     * @return The UUID of the tenant containing the entities associated with this token.
     */
    private final UUID tenantUuid;

    /**
     * @return Information about the application authenticated by this token.
     */
    private final ApplicationInformation application;

    /**
     * @return Information about the user store containing the user authenticated by this token.
     */
    private final Resource userStore;

    /**
     * @return Information about the user authenticated by this token.
     */
    private final UserInformation user;

    public CombinedTokenInformation(
        @JsonProperty("tenant_uuid") final UUID tenantUuid,
        @JsonProperty("application") final ApplicationInformation application,
        @JsonProperty("user_store") final Resource userStore,
        @JsonProperty("user") final UserInformation user
    ) {
        this.tenantUuid = tenantUuid;
        this.application = application;
        this.userStore = userStore;
        this.user = user;
    }
}
