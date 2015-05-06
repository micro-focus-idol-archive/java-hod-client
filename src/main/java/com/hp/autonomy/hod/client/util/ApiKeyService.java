/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.util;

/**
 * Service providing an API key
 */
public interface ApiKeyService {

    /**
     * @return An API key for use with HP Haven OnDemand
     */
    String getApiKey();

}
