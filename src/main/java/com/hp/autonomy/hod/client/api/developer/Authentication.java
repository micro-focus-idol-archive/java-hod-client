package com.hp.autonomy.hod.client.api.developer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import lombok.Data;

@Data
public class Authentication {
    @JsonProperty("app_auth_mode")
    private final ApplicationAuthMode applicationAuthMode;

    @JsonProperty("user_auth_mode")
    private final UserAuthMode userAuthMode;

    @JsonProperty("token_type")
    private final TokenType tokenType;

    public Authentication(final ApplicationAuthMode applicationAuthMode, final UserAuthMode userAuthMode, final TokenType tokenType) {
        this.applicationAuthMode = applicationAuthMode;
        this.userAuthMode = userAuthMode;
        this.tokenType = tokenType;
    }
}
