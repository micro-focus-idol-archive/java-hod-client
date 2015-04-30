/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.error;

import com.hp.autonomy.iod.client.job.Action;
import lombok.extern.slf4j.Slf4j;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;
import java.util.Map;

@Slf4j
public class IodErrorHandler implements ErrorHandler {
    @Override
    public Throwable handleError(final RetrofitError cause) {
        final RetrofitError.Kind kind = cause.getKind();

        if (kind == RetrofitError.Kind.HTTP) {
            // IOD returned an unsuccessful status code, parse the JSON response and throw something better
            IodError iodError = (IodError) cause.getBodyAs(IodError.class);

            // IOD errors are tediously non standard
            // if it has message (rather than reason, it's probably an API key error)
            final String message = iodError.getMessage();

            // if has actions, pull the error out of the action as it will be more useful
            final List<Action<?>> actions = iodError.getActions();

            if (message != null && iodError.getErrorCode() == null) {
                // IOD has given us an API key error
                final Map<String, Object> detail = (Map<String, Object>) iodError.getDetail();
                final IodErrorCode errorCode = IodErrorCode.fromCode((Integer) detail.get("error"));

                iodError = new IodError.Builder()
                        .setErrorCode(errorCode)
                        .setReason(message)
                        .build();
            }
            else if (actions != null && !actions.isEmpty()) {
                // IOD has given us job style output
                iodError = actions.get(0).getErrors().get(0);
            }

            // may be null if no response body
            if (iodError != null) {
                if (iodError.getErrorCode() == IodErrorCode.UNKNOWN_ERROR_CODE) {
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
