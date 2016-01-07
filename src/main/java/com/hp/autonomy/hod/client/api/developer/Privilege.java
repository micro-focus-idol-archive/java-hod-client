/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.developer;

import lombok.Data;

@Data
public class Privilege {
    public static final Privilege MODIFY_DETAILS = new Privilege("MODIFY_DETAILS");
    public static final Privilege MODIFY_AUTH_MODES = new Privilege("MODIFY_AUTH_MODES");
    public static final Privilege MODIFY_AUTHS = new Privilege("MODIFY_AUTHS");
    public static final Privilege SUPER = new Privilege("SUPER");
    public static final Privilege VIEW_AUTHS = new Privilege("VIEW_AUTHS");

    private final String name;

    public Privilege(final String name) {
        this.name = name;
    }
}
