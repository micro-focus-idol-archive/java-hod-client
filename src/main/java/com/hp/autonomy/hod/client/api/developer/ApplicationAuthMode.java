/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.developer;

import lombok.Data;

@Data
public class ApplicationAuthMode {
    public static final ApplicationAuthMode API_KEY = new ApplicationAuthMode("apikey");

    private final String name;

    public ApplicationAuthMode(final String name) {
        this.name = name;
    }
}
