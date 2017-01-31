package com.hp.autonomy.hod.client.api.developer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UserAuthModeTest {
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void deserializesFromJson() throws IOException {
        final UserAuthMode output = objectMapper.readValue("\"none\"", UserAuthMode.class);
        assertThat(output, is(UserAuthMode.NONE));
    }

    @Test
    public void serializesToJson() throws JsonProcessingException {
        final String output = objectMapper.writeValueAsString(UserAuthMode.FACEBOOK);
        assertThat(output, is("\"facebook\""));
    }
}