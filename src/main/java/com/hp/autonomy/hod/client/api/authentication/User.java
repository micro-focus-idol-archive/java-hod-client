package com.hp.autonomy.hod.client.api.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import lombok.Data;

@Data
public class User {
    private final String name;
    private final ResourceIdentifier userStore;

    public User(
        @JsonProperty("name") final String name,
        @JsonProperty("user_store") final ResourceIdentifier userStore
    ) {
        this.name = name;
        this.userStore = userStore;
    }
}
