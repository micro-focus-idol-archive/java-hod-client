/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.token;

public class TokenRepositoryException extends RuntimeException {
    private static final long serialVersionUID = -6597917993324896531L;

    public TokenRepositoryException(final Throwable cause) {
        super("Error retrieving token from repository", cause);
    }
}
