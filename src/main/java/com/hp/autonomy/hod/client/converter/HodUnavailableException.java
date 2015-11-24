package com.hp.autonomy.hod.client.converter;

public class HodUnavailableException extends RuntimeException {
    HodUnavailableException(final Throwable e) {
        super("The service is unavailable", e);
    }
}
