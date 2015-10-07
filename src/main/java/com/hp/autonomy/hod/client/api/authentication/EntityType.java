/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import java.io.Serializable;

public interface EntityType extends Serializable {
    String getName();

    enum Combined implements EntityType {
        INSTANCE;

        @Override
        public String getName() {
            return "CMB";
        }
    }

    enum Application implements EntityType {
        INSTANCE;

        @Override
        public String getName() {
            return "APP";
        }
    }

    enum User implements EntityType {
        INSTANCE;

        @Override
        public String getName() {
            return "USR";
        }
    }

    enum Developer implements EntityType {
        INSTANCE;

        @Override
        public String getName() {
            return "DEV";
        }
    }

    enum Unbound implements EntityType {
        INSTANCE;

        @Override
        public String getName() {
            return "UNB";
        }
    }
}
