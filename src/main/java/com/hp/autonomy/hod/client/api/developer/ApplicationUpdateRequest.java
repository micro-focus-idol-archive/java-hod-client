package com.hp.autonomy.hod.client.api.developer;

import lombok.Builder;

import java.util.List;
import java.util.Optional;

@Builder
public class ApplicationUpdateRequest {
    private final String description;
    private final List<Authentication> authentications;

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<List<Authentication>> getAuthentications() {
        return Optional.ofNullable(authentications);
    }
}
