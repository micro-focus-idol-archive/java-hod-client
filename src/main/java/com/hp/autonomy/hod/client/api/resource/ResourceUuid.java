package com.hp.autonomy.hod.client.api.resource;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class ResourceUuid implements Serializable, ResourceIdentifier {
    private static final long serialVersionUID = -85589867313315229L;

    private final UUID uuid;

    public ResourceUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public ResourceUuid(final String identifierString) {
        if (!identifierString.startsWith(SEPARATOR)) {
            throw new IllegalArgumentException("Invalid identifier string");
        }

        uuid = UUID.fromString(identifierString.substring(1));
    }

    @Override
    public String toString() {
        return SEPARATOR + uuid.toString();
    }
}
