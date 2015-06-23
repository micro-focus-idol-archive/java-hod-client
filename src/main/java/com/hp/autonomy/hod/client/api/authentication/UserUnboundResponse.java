package com.hp.autonomy.hod.client.api.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserUnboundResponse {
    private final AuthenticationToken token;
    private final List<User> users;

    public UserUnboundResponse(
            @JsonProperty("token") final AuthenticationToken token,
            @JsonProperty("users") final List<User> users
    ) {
        this.token = token;
        this.users = users;
    }
}
