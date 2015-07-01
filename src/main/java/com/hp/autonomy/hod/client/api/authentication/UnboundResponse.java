package com.hp.autonomy.hod.client.api.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UnboundResponse {
    private final AuthenticationToken token;
    private final List<Application> applications;

    public UnboundResponse(
            @JsonProperty("token") final AuthenticationToken token,
            @JsonProperty("applications") final List<Application> applications
    ) {
        this.token = token;
        this.applications = applications;
    }
}
