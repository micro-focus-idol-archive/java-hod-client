/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication.tokeninformation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

/**
 * Information about an application token which can be obtained from the get token information API.
 */
@Data
public class ApplicationTokenInformation {
    /**
     * @return The UUID of the tenant containing the entities associated with this token.
     */
    private final UUID tenantUuid;

    /**
     * @return Information about the application authenticated by this token.
     */
    private final ApplicationInformation application;

    public ApplicationTokenInformation(
        @JsonProperty("tenant_uuid") final UUID tenantUuid,
        @JsonProperty("application") final ApplicationInformation application
    ) {
        this.tenantUuid = tenantUuid;
        this.application = application;
    }
}
