package com.hp.autonomy.hod.client.api.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Application {
    private final String name;
    private final String domain;

    public Application(
            @JsonProperty("application") final String name,
            @JsonProperty("domain") final String domain
    ) {
        this.name = name;
        this.domain = domain;
    }
}
