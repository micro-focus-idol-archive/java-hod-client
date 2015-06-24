/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.token;

/**
 * Service which provides a TokenProxy, which removes the need to send a TokenProxy with every request
 */
public interface TokenProxyService {

    /**
     * @return A TokenProxy which can be exchanged for a token to query HP Haven OnDemand
     */
    TokenProxy getTokenProxy();

}
