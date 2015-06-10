/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.util;

import com.hp.autonomy.hod.client.config.HodRequest;

/**
 * Intercepts requests made to HP Haven OnDemand
 */
public interface HodRequestInterceptor {

    /**
     * Modifies a request before it is sent to HP Haven OnDemand
     * @param hodRequest The request to modify
     */
    void intercept(HodRequest hodRequest);

}
