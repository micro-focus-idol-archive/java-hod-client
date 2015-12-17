/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;

import java.io.Serializable;

@Data
public class Resource implements Serializable {
    private static final long serialVersionUID = -2332288355411288129L;

    private static final DateTimeFormatter FORMAT = new DateTimeFormatterBuilder()
        .append(null, new DateTimeParser[]{
            DateTimeFormat.forPattern("EEE MMM dd yyyy HH:mm:ss zZ ('UTC)").getParser(),
            DateTimeFormat.forPattern("EEE MMM dd yyyy HH:mm:ss zZ").getParser()
        })
        .toFormatter();

    /**
     * @return The name of the resource
     */
    private final String resource;

    /**
     * @return The display name of the resource
     */
    private final String displayName;

    /**
     * @return The resource description
     */
    private final String description;

    /**
     * @return The resource type
     */
    private final ResourceType type;

    /**
     * @return The resource flavour
     */
    private final ResourceFlavour flavour;

    /**
     * @return The date created
     */
    private final DateTime dateCreated;

    public Resource(
            @JsonProperty("resource") final String resource,
            @JsonProperty("description") final String description,
            @JsonProperty("type") final ResourceType type,
            @JsonProperty("flavor") final ResourceFlavour flavour,
            @JsonProperty("date_created") final String dateCreated,
            @JsonProperty("display_name") final String displayName
    ) {
        this.resource = resource;
        this.description = description;
        this.type = type;
        this.flavour = flavour;

        if (dateCreated != null) {
            this.dateCreated = FORMAT.parseDateTime(dateCreated);
        }
        else {
            this.dateCreated = null;
        }
        this.displayName = displayName != null ? displayName : resource;
    }
}
