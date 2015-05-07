/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import com.hp.autonomy.hod.client.converter.DoNotConvert;

/**
 * The type of a token returned by HP Haven OnDemand
 */
@DoNotConvert
public enum TokenType {
    /**
     * A simple token to be used with normal API requests. If you are unsure which type you want, you probably want
     * simple
     */
    simple,

    /**
     * A token to be used with HMAC signed requests. API requests made with the resulting token will need to be HMAC
     * signed in accordance with the HP Haven OnDemand documentation
     */
    hmac_sha1
}
