/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.developer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@JsonDeserialize(builder = Application.Builder.class)
public class Application {
    private final String name;
    private final String description;
    private final String domain;
    private final String domainDescription;
    private final List<Privilege> privileges;

    private Application(final Builder builder) {
        name = builder.name;
        description = builder.description;
        domain = builder.domain;
        domainDescription = builder.domainDescription;
        privileges = builder.privileges;
    }

    @Data
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private String name;
        private String description;
        private String domain;
        private List<Privilege> privileges;
        private String domainDescription;

        public Application build() {
            return new Application(this);
        }
    }
}
