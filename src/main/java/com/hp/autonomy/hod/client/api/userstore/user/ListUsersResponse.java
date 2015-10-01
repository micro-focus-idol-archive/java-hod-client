/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.userstore.User;
import lombok.Data;

import java.util.List;

@Data
class ListUsersResponse {
    private final List<User> users;

    ListUsersResponse(@JsonProperty("users") final List<User> users) {
        this.users = users;
    }
}
