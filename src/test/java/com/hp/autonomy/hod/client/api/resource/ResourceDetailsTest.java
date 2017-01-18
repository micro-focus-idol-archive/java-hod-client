package com.hp.autonomy.hod.client.api.resource;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.*;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ResourceDetailsTest {
    @Test
    public void testSerializeAndDeserialize() throws IOException, ClassNotFoundException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        final ResourceDetails input = new ResourceDetails(
                ResourceInformation.builder()
                        .domain("my-domain")
                        .name("my-connector")
                        .uuid(UUID.randomUUID())
                        .build(),
                "My amazing connector",
                ResourceType.CONNECTOR,
                "2015-10-12T07:17:50.000Z",
                "My Connector"
        );

        try (final ObjectOutput objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(input);
        }

        try (final ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()))) {
            @SuppressWarnings("CastToConcreteClass")
            final ResourceDetails output = (ResourceDetails) objectInputStream.readObject();
            assertThat(output, is(input));
        }
    }

    @Test
    public void deserializesFromJson() throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        final ResourceDetails output;

        try (final InputStream jsonStream = getClass().getResourceAsStream("/com/hp/autonomy/hod/client/api/resource/resource.json")) {
            output = objectMapper.readValue(jsonStream, ResourceDetails.class);
        }

        final ResourceDetails expectedOutput = new ResourceDetails(
                ResourceInformation.builder()
                        .domain("PUBLIC_INDEXES")
                        .name("arxiv")
                        .uuid(UUID.fromString("111e34b3-d20c-11e4-9484-d48564a6c0ef"))
                        .build(),
                "Arxiv scientific papers",
                ResourceType.TEXT_INDEX,
                "2015-10-12T07:17:50.000Z",
                "Arxiv"
        );

        assertThat(output, is(expectedOutput));
    }
}