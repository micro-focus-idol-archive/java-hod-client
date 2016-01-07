/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.token;

import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;

/**
 * Service which provides a TokenProxy, which removes the need to send a TokenProxy with every request
 */
public interface TokenProxyService<E extends EntityType, T extends TokenType> {

    /**
     * @return A TokenProxy which can be exchanged for a token to query HP Haven OnDemand
     */
    TokenProxy<E, T> getTokenProxy();

}
