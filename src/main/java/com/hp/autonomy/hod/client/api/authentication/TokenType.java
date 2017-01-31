/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * Interface representing possible types for an {@link AuthenticationToken}.
 *
 * The static implementations declared in this interface are all enums with one instance so we get final and sensible
 * Serializable behaviour for free.
 */
public interface TokenType extends Serializable {
    /**
     * @return The name of this token type as returned from Haven OnDemand
     */
    String getName();

    /**
     * @return The value of the "token_type" parameter which must be set for authentication requests to Haven OnDemand to
     * return this token type.
     */
    String getParameter();

    /**
     * Token type for simple tokens which do not need signing.
     */
    enum Simple implements TokenType {
        INSTANCE;

        @Override
        public String getName() {
            return "SIMPLE";
        }

        @JsonValue
        @Override
        public String getParameter() {
            return "simple";
        }
    }

    /**
     * Token type for tokens which must be used as part of an HMAC SHA1 signed request.
     */
    enum HmacSha1 implements TokenType {
        INSTANCE;

        @Override
        public String getName() {
            return "HMAC_SHA1";
        }

        @JsonValue
        @Override
        public String getParameter() {
            return "hmac_sha1";
        }
    }
}
