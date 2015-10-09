/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.application;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private final String name;
    private final ResourceIdentifier userStore;

    public User(@JsonProperty("user_name") final String name, @JsonProperty("user_store") final String userStore) {
        this.name = name;
        this.userStore = new ResourceIdentifier(userStore);
    }
}
