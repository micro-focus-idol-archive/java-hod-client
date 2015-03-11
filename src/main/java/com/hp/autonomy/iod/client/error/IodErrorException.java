/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.error;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class IodErrorException extends Exception {
    private static final long serialVersionUID = -1623916762461350039L;

    private final IodErrorCode errorCode;
    private final boolean serverError;

    public IodErrorException(final IodError iodError, final int httpStatusCode) {
        super(iodError.getReason());

        this.errorCode = iodError.getErrorCode();
        this.serverError = httpStatusCode >= 500;
    }

}
