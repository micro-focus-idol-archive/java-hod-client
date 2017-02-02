package com.hp.autonomy.hod.client.api.resource;

import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ResourceUuidTest {
    @Test
    public void toStringAddsColon() {
        final String uuidString = "45a4f1ff-158d-4952-8fa7-d8afa17de48d";
        final UUID uuid = UUID.fromString(uuidString);
        final ResourceIdentifier identifier = new ResourceUuid(uuid);
        assertThat(identifier.toString(), is(':' + uuidString));
    }

    @Test
    public void parsesIdentifierString() {
        final String uuidString = "45a4f1ff-158d-4952-8fa7-d8afa17de48d";
        final ResourceUuid identifier = new ResourceUuid(':' + uuidString);
        assertThat(identifier.getUuid(), is(UUID.fromString(uuidString)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWithInvalidUuid() {
        new ResourceUuid(":45a4f1ff:158d:4952:8fa7:d8afa17de48d");
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWithNoColon() {
        new ResourceUuid("45a4f1ff-158d-4952-8fa7-d8afa17de48d");
    }
}