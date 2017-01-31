package com.hp.autonomy.hod.client.api.developer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AuthenticationTest {
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper()
            // Sort keys deterministically for simpler expectations
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
    }

    @Test
    public void serializesToJson() throws JsonProcessingException {
        final Authentication authentication = new Authentication(ApplicationAuthMode.API_KEY, UserAuthMode.FACEBOOK, TokenType.Simple.INSTANCE);
        final String output = objectMapper.writeValueAsString(authentication);
        assertThat(output, is("{\"app_auth_mode\":\"apikey\",\"token_type\":\"simple\",\"user_auth_mode\":\"facebook\"}"));
    }
}