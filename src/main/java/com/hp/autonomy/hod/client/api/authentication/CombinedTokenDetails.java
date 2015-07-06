package com.hp.autonomy.hod.client.api.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import lombok.Data;

@Data
public class CombinedTokenDetails {
    private final ResourceIdentifier application;
    private final User user;

    public CombinedTokenDetails(
            @JsonProperty("application") final ResourceIdentifier application,
            @JsonProperty("user") final User user
    ) {
        this.application = application;
        this.user = user;
    }
}
