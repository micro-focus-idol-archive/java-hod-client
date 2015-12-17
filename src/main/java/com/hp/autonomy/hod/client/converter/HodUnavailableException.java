/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.converter;

public class HodUnavailableException extends RuntimeException {
    HodUnavailableException(final Throwable e) {
        super("The service is unavailable", e);
    }
}
