/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.error;

import lombok.extern.slf4j.Slf4j;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

@Slf4j
public class IodErrorHandler implements ErrorHandler {
    @Override
    public Throwable handleError(final RetrofitError cause) {
        final RetrofitError.Kind kind = cause.getKind();

        if(kind == RetrofitError.Kind.HTTP) {
            // IOD returned an unsuccessful status code, parse the JSON response and throw something better
            final IodError iodError = (IodError) cause.getBodyAs(IodError.class);

            // may be null if no response body
            if (iodError != null) {
                if(iodError.getErrorCode() == IodErrorCode.UNKNOWN_ERROR_CODE) {
                    // a code we've not seen before has been returned, log it
                    log.error("UNKNOWN ERROR CODE {}", iodError.getErrorCode());
                }

                final Response response = cause.getResponse();
                final int status = response.getStatus();

                log.error("{} error communicating with IDOL OnDemand", status);

                log.error("Detail was : {}", iodError.getDetail());

                return new IodErrorException(iodError, status);
            }
            else {
                // no response body, not much we can do
                return cause;
            }
        }
        else {
            // something unusual went wrong, just rethrow the cause
            return cause;
        }
    }
}
