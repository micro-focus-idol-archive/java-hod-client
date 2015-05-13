/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.joda.time.DateTime;

/**
 * A token which can be used to make API requests to HP Haven OnDemand
 */
@Getter
@EqualsAndHashCode
public class AuthenticationToken {

    /**
     * @return The expiry time of the token
     */
    private final DateTime expiry;

    /**
     * @return The id of the token
     */
    private final String id;

    /**
     * @return The secret of the token
     */
    private final String secret;

    /**
     * @return The type of the token
     */
    private final String type;

    /**
     * @return The time when a the token will begin to refresh
     */
    private final DateTime startRefresh;

    @JsonCreator
    public AuthenticationToken(
            @JsonProperty("expiry") final long expiry,
            @JsonProperty("id") final String id,
            @JsonProperty("secret") final String secret,
            @JsonProperty("type") final String type,
            @JsonProperty("startRefresh") final long startRefresh
    ) {
        this.expiry = new DateTime(expiry * 1000L);
        this.id = id;
        this.secret = secret;
        this.type = type;
        this.startRefresh = new DateTime(startRefresh * 1000L);
    }

    /**
     * @return True if the token has expired; false otherwise
     */
    @JsonIgnore
    public boolean hasExpired() {
        return this.expiry.isBeforeNow();
    }

    @Override
    public String toString() {
        return type + ':' + id + ':' + secret;
    }

}
