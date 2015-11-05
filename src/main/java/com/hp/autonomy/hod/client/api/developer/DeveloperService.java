/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.developer;

import com.hp.autonomy.hod.client.api.authentication.ApiKey;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.error.HodErrorException;

import java.util.UUID;

/**
 * Service for managing HOD developers.
 */
public interface DeveloperService {

    /**
     * Add an API as an authentication source for the developer with the given UUID.
     * @param token The developer token to use for authentication
     * @param developerUuid The UUID of the developer
     * @return The new API key
     */
    ApiKey addAuthentication(AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token, UUID developerUuid) throws HodErrorException;

}
