package com.hp.autonomy.hod.client.api.textindex.status;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.autonomy.hod.client.api.textindex.IndexFlavor;
import com.hp.autonomy.hod.client.api.userstore.UserStoreInformation;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TextIndexStatusTest {
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void deserializesFromUserTokenInformationJson() throws IOException {
        final TextIndexStatus output;

        try (final InputStream jsonStream = getClass().getResourceAsStream("/com/hp/autonomy/hod/client/api/textindex/status/text-index-status.json")) {
            output = objectMapper.readValue(jsonStream, TextIndexStatus.class);
        }

        final UserStoreInformation expectedUserStore = UserStoreInformation.builder()
                .name("DEFAULT_USER_STORE")
                .domain("21259eb0-b1c4-4bd7-b4fc-c502aac60a44")
                .uuid(UUID.fromString("39b69aed-bdf4-44dc-83a6-aeb41bd7a9af"))
                .build();

        final TextIndexStatus expectedOutput = TextIndexStatus.builder()
                .totalDocuments(5)
                .totalIndexSize(15514038)
                .componentCount(1)
                .flavor(IndexFlavor.STANDARD)
                .indexUpdates24hr(5)
                .userStore(expectedUserStore)
                .build();

        assertThat(output, is(expectedOutput));
    }
}
