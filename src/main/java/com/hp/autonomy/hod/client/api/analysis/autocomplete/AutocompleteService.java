/*
 * Copyright 2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.analysis.autocomplete;

import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.util.List;

/**
 * Interface representing the autocomplete API.
 */
public interface AutocompleteService {

    /**
     * Get autocomplete suggestions for the given query text, using a configured TokenProxyService for authentication.
     * @param text The input text
     * @return A list of suggestions
     * @throws HodErrorException
     * @throws NullPointerException If no TokenProxyService was configured
     */
    List<String> getSuggestions(String text) throws HodErrorException;

    /**
     * Get autocomplete suggestions for the given query text, using the given TokenProxy for authentication.
     * @param text The input text
     * @param tokenProxy The token proxy
     * @return A list of suggestions
     * @throws HodErrorException
     */
    List<String> getSuggestions(String text, TokenProxy<?, TokenType.Simple> tokenProxy) throws HodErrorException;

}
