/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import java.io.Serializable;

/**
 * Interface representing possible entities which could be authenticated by an {@link AuthenticationToken}.
 *
 * The static implementations declared in this interface are all enums with one instance so we get final and sensible
 * Serializable behaviour for free.
 */
public interface EntityType extends Serializable {
    /**
     * @return The name of this entity type as returned from Haven OnDemand
     */
    String getName();

    /**
     * Entity type for combined tokens.
     */
    enum Combined implements EntityType {
        INSTANCE;

        @Override
        public String getName() {
            return "CMB";
        }
    }

    /**
     * Entity type for application tokens.
     */
    enum Application implements EntityType {
        INSTANCE;

        @Override
        public String getName() {
            return "APP";
        }
    }

    /**
     * Entity type for user tokens.
     */
    enum User implements EntityType {
        INSTANCE;

        @Override
        public String getName() {
            return "USR";
        }
    }

    /**
     * Entity type for developer tokens.
     */
    enum Developer implements EntityType {
        INSTANCE;

        @Override
        public String getName() {
            return "DEV";
        }
    }

    /**
     * Entity type for unbound tokens.
     */
    enum Unbound implements EntityType {
        INSTANCE;

        @Override
        public String getName() {
            return "UNB";
        }
    }
}
