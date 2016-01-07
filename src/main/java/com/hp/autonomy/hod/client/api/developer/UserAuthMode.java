/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.developer;

import lombok.Data;

@Data
public class UserAuthMode {
    public static final UserAuthMode NONE = new UserAuthMode("none");
    public static final UserAuthMode API_KEY = new UserAuthMode("apikey");
    public static final UserAuthMode GOOGLE = new UserAuthMode("google");
    public static final UserAuthMode FACEBOOK = new UserAuthMode("facebook");
    public static final UserAuthMode OPEN_ID = new UserAuthMode("openid");
    public static final UserAuthMode TWITTER = new UserAuthMode("twitter");
    public static final UserAuthMode HP_PASSPORT = new UserAuthMode("hppassport");
    public static final UserAuthMode DEVELOPER_TOKEN = new UserAuthMode("devtoken");

    private final String name;

    public UserAuthMode(final String name) {
        this.name = name;
    }
}
