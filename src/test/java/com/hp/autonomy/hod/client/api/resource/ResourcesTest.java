/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ResourcesTest {

    @Test
    public void testSerializeAndDeserialize() throws IOException, ClassNotFoundException {
        final List<Resource> privateResources = Arrays.asList(
            new Resource("foo", "bar", ResourceType.CONTENT, ResourceFlavour.EXPLORER, "Wed Sep 30 2015 12:00:00 GMT+0000 (UTC)"),
            new Resource("bar", "bar", ResourceType.CONTENT, ResourceFlavour.EXPLORER, "Wed Sep 30 2015 12:00:00 GMT+0000 (UTC)")
        );

        final List<Resource> publicResources = Collections.singletonList(
            new Resource(ResourceIdentifier.NEWS_ENG.getName(), "bar", ResourceType.CONTENT, ResourceFlavour.EXPLORER, "Wed Sep 30 2015 12:00:00 GMT+0000 (UTC)")
        );

        final Resources resources = new Resources(privateResources, publicResources);

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(resources);

        final ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        final Resources resourceOut = (Resources) objectInputStream.readObject();

        assertThat(resourceOut, is(resources));
    }


}