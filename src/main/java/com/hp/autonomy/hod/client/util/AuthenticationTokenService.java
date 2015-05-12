/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.util;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;

/**
 * Service providing a token
 */
public interface AuthenticationTokenService {

    /**
     * @return A token for use with HP Haven OnDemand
     */
    AuthenticationToken getToken();

}
