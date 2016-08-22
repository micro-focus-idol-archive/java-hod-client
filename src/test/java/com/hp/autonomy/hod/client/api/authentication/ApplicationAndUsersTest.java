package com.hp.autonomy.hod.client.api.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ApplicationAndUsersTest {
    @Test
    public void deserializes() throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final ApplicationAndUsers output;

        try (final InputStream jsonStream = getClass().getResourceAsStream("/com/hp/autonomy/hod/client/api/authentication/application-and-users.json")) {
            output = objectMapper.readValue(jsonStream, ApplicationAndUsers.class);
        }

        final ApplicationAndUsers expectedOutput = new ApplicationAndUsers(
                "Find",
                "8bacd595-f288-4c33-a5dd-8a92f7bd14a0",
                "Find Application",
                null,
                Arrays.asList("SIMPLE", "HMAC_SHA1"),
                Collections.singletonList(new ApplicationAndUsers.User("DEFAULT_USER_STORE", "8bacd595-f288-4c33-a5dd-8a92f7bd14a0", null))
        );

        assertThat(output, is(expectedOutput));
    }
}