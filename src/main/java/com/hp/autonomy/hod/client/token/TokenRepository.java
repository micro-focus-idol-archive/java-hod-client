/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.token;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;

import java.io.IOException;

/**
 * A TokenRepository stores tokens such that they can be refreshed as instructed by HP Haven OnDemand. Note that the
 * token retrieved by the get method may not be the same token that was originally inserted, but will have the same
 * capabilities.
 *
 * Repositories may automatically remove expired tokens
 */
public interface TokenRepository {

    /**
     * Insert a token into the repository for later retrieval
     * @param token The {@link AuthenticationToken} to store
     * @return A key which can be used to retrieve the token
     * @throws IOException If an IO error occurred inserting the token
     * @throws IllegalArgumentException If the token has expired
     */
    <E extends EntityType, T extends TokenType> TokenProxy<E, T> insert(AuthenticationToken<E, T> token) throws IOException;

    /**
     * Update the token in the repository associated with the given key. If the key is not in the repository, the new
     * token will not be stored
     * @param key The key to associate with the token
     * @param newToken The new token for the key
     * @return The old token associated with the key, or null if no key was associated
     * @throws IOException If an IO error occurred updating the token
     * @throws IllegalArgumentException If the token has expired
     */
    <E extends EntityType, T extends TokenType> AuthenticationToken<E, T> update(TokenProxy<E, T> key, AuthenticationToken<E, T> newToken) throws IOException;

    /**
     * Retrieve the token associated with the given key
     * @param key The key with which to retrieve a token
     * @return The token associated with the key, or null if no token is associated with the key
     * @throws IOException If an IO error occurred retrieving the token
     */
    <E extends EntityType, T extends TokenType> AuthenticationToken<E, T> get(TokenProxy<E, T> key) throws IOException;

    /**
     * Removes the token associated with the given key
     * @param key The key for which a token should be removed
     * @return The old token associated with the key
     * @throws IOException If an IO error occurred removing the token
     */
    <E extends EntityType, T extends TokenType> AuthenticationToken<E, T> remove(TokenProxy<E, T> key) throws IOException;

}
