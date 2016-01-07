/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.converter;

public class HodUnavailableException extends RuntimeException {
    private static final long serialVersionUID = 7338702565370576310L;

    HodUnavailableException(final Throwable e) {
        super("The service is unavailable", e);
    }
}
