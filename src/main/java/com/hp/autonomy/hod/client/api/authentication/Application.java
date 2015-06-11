package com.hp.autonomy.hod.client.api.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Application {
    private final String name;
    private final String domain;
    private final String applicationDescription;
    private final String domainDescription;

    public Application(
            @JsonProperty("application") final String name,
            @JsonProperty("domain") final String domain,
            @JsonProperty("applicationDescription") final String applicationDescription,
            @JsonProperty("domainDescription") final String domainDescription
    ) {
        this.name = name;
        this.domain = domain;
        this.applicationDescription = applicationDescription;
        this.domainDescription = domainDescription;
    }
}
