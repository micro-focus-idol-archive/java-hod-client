/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Represents the return type from the get group info API.
 */
@Data
@JsonDeserialize(builder = GroupInfo.Builder.class)
public class GroupInfo {
    private final String name;
    private final ResourceIdentifier userStore;
    private final List<String> parents;
    private final List<String> children;
    private final List<UUID> users;

    private GroupInfo(final Builder builder) {
        name = builder.name;
        userStore = builder.userStore;
        parents = builder.parents;
        children = builder.children;

        users = new LinkedList<>();

        users.addAll(builder.users.stream().map(GroupUser::getUuid).collect(Collectors.toList()));
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
        private List<GroupUser> users;

        public GroupInfo build() {
            return new GroupInfo(this);
        }
    }
}
