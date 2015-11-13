/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication.tokeninformation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

/**
 * Information about a developer token which can be obtained from the get token information API.
 */
@Data
public class DeveloperTokenInformation {
    /**
     * @return The UUID of the tenant containing the entities associated with this token.
     */
    private final UUID tenantUuid;

    /**
     * @return Information about the developer authenticated by this token.
     */
    private final DeveloperInformation developer;

    public DeveloperTokenInformation(
        @JsonProperty("tenant_uuid") final UUID tenantUuid,
        @JsonProperty("developer") final DeveloperInformation developer
    ) {
        this.tenantUuid = tenantUuid;
        this.developer = developer;
    }
}
