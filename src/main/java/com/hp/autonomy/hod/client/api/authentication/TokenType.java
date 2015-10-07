/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import java.io.Serializable;

public interface TokenType extends Serializable {
    String getName();
    String getParameter();

    enum Simple implements TokenType {
        INSTANCE;

        @Override
        public String getName() {
            return "SIMPLE";
        }

        @Override
        public String getParameter() {
            return "simple";
        }
    }

    enum HmacSha1 implements TokenType {
        INSTANCE;

        @Override
        public String getName() {
            return "HMAC_SHA1";
        }

        @Override
        public String getParameter() {
            return "hmac_sha1";
        }
    }
}
