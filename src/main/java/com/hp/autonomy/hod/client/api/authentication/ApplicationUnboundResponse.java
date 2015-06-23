package com.hp.autonomy.hod.client.api.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ApplicationUnboundResponse {
    private final AuthenticationToken token;
    private final List<Application> applications;

    public ApplicationUnboundResponse(
            @JsonProperty("token") final AuthenticationToken token,
            @JsonProperty("applications") final List<Application> applications
    ) {
        this.token = token;
        this.applications = applications;
    }
}
