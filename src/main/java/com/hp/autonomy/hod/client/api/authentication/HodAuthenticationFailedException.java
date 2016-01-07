/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import com.hp.autonomy.hod.client.token.TokenProxy;
import lombok.Getter;

@Getter
public class HodAuthenticationFailedException extends RuntimeException {
    private static final long serialVersionUID = 4189975984173937763L;

    private final TokenProxy<?, ?> tokenProxy;

    public HodAuthenticationFailedException(final String message, final TokenProxy<?, ?> tokenProxy) {
        super(message);

        this.tokenProxy = tokenProxy;
    }
}
