package com.hp.autonomy.hod.client.api.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {
    private final String username;
    private final String userStore;

    public User(@JsonProperty("user_name") final String username, @JsonProperty("user_store") final String userStore) {
        this.username = username;
        this.userStore = userStore;
    }
}
