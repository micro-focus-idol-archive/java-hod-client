package com.hp.autonomy.hod.client.api.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TokenTypeTest {
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void simpleSerializesToJson() throws JsonProcessingException {
        final String output = objectMapper.writeValueAsString(TokenType.Simple.INSTANCE);
        assertThat(output, is("\"simple\""));
    }

    @Test
    public void hmacSerializesToJson() throws JsonProcessingException {
        final String output = objectMapper.writeValueAsString(TokenType.HmacSha1.INSTANCE);
        assertThat(output, is("\"hmac_sha1\""));
    }
}