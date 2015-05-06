/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.error;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class HodErrorException extends Exception {
    private static final long serialVersionUID = -1623916762461350039L;

    private final HodErrorCode errorCode;
    private final boolean serverError;

    public HodErrorException(final HodError hodError, final int httpStatusCode) {
        super(hodError.getReason());

        this.errorCode = hodError.getErrorCode();
        this.serverError = httpStatusCode >= 500;
    }

}
