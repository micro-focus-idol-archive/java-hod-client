package com.hp.autonomy.hod.client.api.textindex;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IndexFlavorTest {
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void deserializeFromJson() throws IOException {
        final IndexFlavor indexFlavor = objectMapper.readValue("\"vanilla\"", IndexFlavor.class);
        assertThat(indexFlavor, is(new IndexFlavor("vanilla")));
    }

    @Test
    public void testSerializeAndDeserialize() throws IOException, ClassNotFoundException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (final ObjectOutput objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(IndexFlavor.EXPLORER);
        }

        try (final ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()))) {
            @SuppressWarnings("CastToConcreteClass")
            final IndexFlavor resourceFlavour = (IndexFlavor) objectInputStream.readObject();
            assertThat(resourceFlavour, is(IndexFlavor.EXPLORER));
        }
    }
}
