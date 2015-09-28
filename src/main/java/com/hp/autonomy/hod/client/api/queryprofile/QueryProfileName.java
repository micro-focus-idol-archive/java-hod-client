/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */
package com.hp.autonomy.hod.client.api.queryprofile;

import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

/**
 * Represents the name and date created of a query profile.  Part of the response from ListQueryProfile.
 */
@Data
public class QueryProfileName {

    /**
     * The name of a query profile
     */
    private final String name;

    /**
     * The date the query profile was created
     */
    private final DateTime dateCreated;

    private QueryProfileName(final Builder builder) {
        this.name = builder.name;
        this.dateCreated = builder.dateCreated;
    }

    @Setter
    @Accessors(chain = true)
    public static class Builder {
        private DateTime dateCreated;
        private String name;

        public QueryProfileName build() {
            return new QueryProfileName(this);
        }
    }

}
