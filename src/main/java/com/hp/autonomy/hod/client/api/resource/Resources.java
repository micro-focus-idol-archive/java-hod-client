/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Setter(AccessLevel.NONE)
public class Resources implements Serializable {
    private static final long serialVersionUID = 4268453026558953307L;

    /**
     * @return A list of private resources
     */
    private transient List<Resource> resources;

    /**
     * @return A list of public resources
     */
    private transient List<Resource> publicResources;

    public Resources(
            @JsonProperty("private_resources") final List<Resource> resources,
            @JsonProperty("public_resources") final List<Resource> publicResources
    ) {
        this.resources = resources;
        this.publicResources = publicResources;
    }

    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();

        resources = new ArrayList<>();

        final int numberOfResources = objectInputStream.readInt();

        for(int i = 0; i < numberOfResources; i++) {
            resources.add((Resource) objectInputStream.readObject());
        }

        publicResources = new ArrayList<>();

        final int numberOfPublicResources = objectInputStream.readInt();

        for(int i = 0; i < numberOfPublicResources; i++) {
            publicResources.add((Resource) objectInputStream.readObject());
        }
    }

    /**
     * @serialData Writes out the number of resources, followed by the resources, followed by the number of public
     * resources, followed by the public resources.
     */
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        objectOutputStream.writeInt(resources.size());

        for(final Resource resource : resources) {
            objectOutputStream.writeObject(resource);
        }

        objectOutputStream.writeInt(publicResources.size());

        for(final Resource resource : publicResources) {
            objectOutputStream.writeObject(resource);
        }
    }
}
