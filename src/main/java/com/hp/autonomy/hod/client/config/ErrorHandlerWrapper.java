/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.config;

import com.hp.autonomy.hod.client.error.HodErrorHandler;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;

class ErrorHandlerWrapper implements ErrorHandler {

    private final HodErrorHandler errorHandler;

    ErrorHandlerWrapper(final HodErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    @Override
    public Throwable handleError(final RetrofitError cause) {
        return errorHandler.handleError(cause);
    }

}
