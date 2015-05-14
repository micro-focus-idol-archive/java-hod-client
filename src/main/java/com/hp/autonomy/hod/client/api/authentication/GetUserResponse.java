package com.hp.autonomy.hod.client.api.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetUserResponse {
    private final List<User> users;

    GetUserResponse(@JsonProperty("users") final List<User> users) {
        this.users = users;
    }
}
