/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.error;

import com.hp.autonomy.hod.client.job.Action;
import lombok.extern.slf4j.Slf4j;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;
import java.util.Map;

@Slf4j
public class HodErrorHandler implements ErrorHandler {
    @Override
    public Throwable handleError(final RetrofitError cause) {
        final RetrofitError.Kind kind = cause.getKind();

        if (kind == RetrofitError.Kind.HTTP) {
            // HOD returned an unsuccessful status code, parse the JSON response and throw something better
            HodError hodError = (HodError) cause.getBodyAs(HodError.class);

            // HOD errors are tediously non standard
            // if it has message (rather than reason, it's probably an API key error)
            final String message = hodError.getMessage();

            // if has actions, pull the error out of the action as it will be more useful
            final List<Action<?>> actions = hodError.getActions();

            int error = hodError.getError();

            if (message != null && hodError.getErrorCode() == null) {
                // HOD has given us an API key error
                final Map<String, Object> detail = (Map<String, Object>) hodError.getDetail();
                error = (Integer) detail.get("error");
                final HodErrorCode errorCode = HodErrorCode.fromCode(error);

                hodError = new HodError.Builder()
                        .setErrorCode(errorCode)
                        .setReason(message)
                        .build();
            }
            else if (actions != null && !actions.isEmpty()) {
                // HOD has given us job style output
                hodError = actions.get(0).getErrors().get(0);
            }

            // may be null if no response body
            if (hodError != null) {
                if (hodError.getErrorCode() == HodErrorCode.UNKNOWN_ERROR_CODE) {
                    // a code we've not seen before has been returned, log it
                    log.error("UNKNOWN ERROR CODE {}", error);
                }

                final Response response = cause.getResponse();
                final int status = response.getStatus();

                log.error("{} error communicating with HP Haven OnDemand", status);

                log.error("Detail was : {}", hodError.getDetail());

                return new HodErrorException(hodError, status);
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
