/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.api.userstore.User;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@JsonDeserialize(builder = GroupInfo.Builder.class)
public class GroupInfo {
    private final String name;
    private final ResourceIdentifier userStore;
    private final List<String> parents;
    private final List<String> children;
    private final List<User> users;

    private GroupInfo(final Builder builder) {
        name = builder.name;
        userStore = builder.userStore;
        parents = builder.parents;
        children = builder.children;
        users = builder.users;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @Setter
    @Accessors(chain = true)
    public static final class Builder {
        @JsonProperty("group")
        private String name;

        @JsonProperty("user_store")
        private ResourceIdentifier userStore;

        private List<String> parents;
        private List<String> children;
        private List<User> users;

        public GroupInfo build() {
            return new GroupInfo(this);
        }
    }
}
