/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
class ListUsersResponse<T> {
    private final List<User<T>> users;

    ListUsersResponse(@JsonProperty("users") final List<User<T>> users) {
        this.users = users;
    }
}
