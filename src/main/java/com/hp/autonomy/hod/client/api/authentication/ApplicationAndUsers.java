package com.hp.autonomy.hod.client.api.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * An application and a list of users who can authenticate to that application. Returned from an authenticate combined
 * GET request.
 */
@Data
public class ApplicationAndUsers {
    private final String name;
    private final String domain;
    private final String description;
    private final String domainDescription;
    private final List<String> supportedTokenTypes;
    private final List<User> users;

    @JsonCreator
    public ApplicationAndUsers(
            @JsonProperty("name") final String name,
            @JsonProperty("domain") final String domain,
            @JsonProperty("description") final String description,
            @JsonProperty("domainDescription") final String domainDescription,
            @JsonProperty("supportedTokenTypes") final List<String> supportedTokenTypes,
            @JsonProperty("users") final List<User> users
    ) {
        this.name = name;
        this.domain = domain;
        this.description = description;
        this.domainDescription = domainDescription;
        this.users = users == null ? Collections.<User>emptyList() : Collections.unmodifiableList(users);
        this.supportedTokenTypes = supportedTokenTypes == null ? Collections.<String>emptyList() : Collections.unmodifiableList(supportedTokenTypes);
    }

    /**
     * A representation of a user returned from an authenticate combined GET request.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class User {
        private final String userStore;
        private final String domain;
        private final String domainDescription;

        @JsonCreator
        public User(
                @JsonProperty("userStore") final String userStore,
                @JsonProperty("domain") final String domain,
                @JsonProperty("domainDescription") final String domainDescription
        ) {
            this.userStore = userStore;
            this.domain = domain;
            this.domainDescription = domainDescription;
        }
    }
}
