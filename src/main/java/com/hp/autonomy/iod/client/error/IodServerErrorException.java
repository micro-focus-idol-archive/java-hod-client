/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.error;

public class IodServerErrorException extends RuntimeException implements IodErrorException {
    private static final long serialVersionUID = -1623916762461350039L;

    private final IodErrorCode errorCode;

    public IodServerErrorException(final IodError iodError) {
        super(iodError.getReason());

        this.errorCode = iodError.getErrorCode();
    }

    @Override
    public IodErrorCode getErrorCode() {
        return errorCode;
    }
}
