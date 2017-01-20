/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.developer;

import com.hp.autonomy.hod.client.api.authentication.ApiKey;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.error.HodErrorException;

import java.util.List;

public interface ApplicationService {

    /**
     * List applications in the given domain.
     * @param token The developer token
     * @param domain The domain name
     * @return Applications and their privileges
     * @throws HodErrorException
     */
    List<Application> list(AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token, String domain) throws HodErrorException;

    /**
     * Create an application in the given domain.
     * @param token The developer token to use for authentication
     * @param domain The domain in which to create the application
     * @param name The name of the new application
     * @param description The description of the new application
     * @throws HodErrorException
     */
    void create(AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token, String domain, String name, String description) throws HodErrorException;

    /**
     * Delete an application from a domain.
     * @param token The developer token to use for authentication
     * @param domain The domain of the application
     * @param name The name of the application
     * @throws HodErrorException
     */
    void delete(AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token, String domain, String name) throws HodErrorException;

    /**
     * List the authentications associated with the given application.
     * @param token The developer token to use for authentication
     * @param domain The domain of the application
     * @param name The name of the application
     * @return The authentications associated with the application
     * @throws HodErrorException
     */
    List<Authentication> listAuthentications(AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token, String domain, String name) throws HodErrorException;

    /**
     * Create a new authentication and associate it with the given application.
     * @param token The developer token to use for authentication
     * @param domain The domain of the application
     * @param name The name of the application
     * @return The API key of the new authentication
     * @throws HodErrorException
     */
    ApiKey addAuthentication(AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token, String domain, String name) throws HodErrorException;

    /**
     * Add an authentication mode to the given application.
     * @param token The developer token to use for authentication
     * @param domain The domain of the application
     * @param name The name of the application
     * @param applicationMode The mode by which the application can authenticate
     * @param userMode The mode by which users must authenticate to create a combined token
     * @param returnTokenType The token type associated with this authentication mode
     * @throws HodErrorException
     */
    void addAuthenticationMode(
        AuthenticationToken<EntityType.Developer, TokenType.HmacSha1> token,
        String domain,
        String name,
        ApplicationAuthMode applicationMode,
        UserAuthMode userMode,
        TokenType returnTokenType
    ) throws HodErrorException;

}
