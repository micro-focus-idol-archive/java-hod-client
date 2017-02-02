/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ResourceTypeTest {

    @Test
    public void testSerializeAndDeserialize() throws IOException, ClassNotFoundException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(ResourceType.TEXT_INDEX);

        final ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        final ResourceType resourceType = (ResourceType) objectInputStream.readObject();

        assertThat(resourceType, is(ResourceType.TEXT_INDEX));
    }

    // this test uses reflection to check allOf contains all the types
    // we don't use reflection in the class for performance reasons
    @Test
    public void testAllOf() throws IllegalAccessException {
        final Field[] fields = ResourceType.class.getFields();

        final Set<ResourceType> resourceTypes = new HashSet<>();

        final ResourceType dummy = ResourceType.CONNECTOR;

        for(final Field field : fields) {
            final int modifiers = field.getModifiers();
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers) && field.getType() == ResourceType.class) {
                resourceTypes.add((ResourceType) field.get(dummy));
            }
        }

        assertThat(ResourceType.allOf(), is(resourceTypes));
    }

    @Test
    public void testOf() {
        final Set<ResourceType> resourceTypes = ResourceType.of(ResourceType.TEXT_INDEX, ResourceType.CONNECTOR);

        assertThat(resourceTypes, hasSize(2));
        assertThat(resourceTypes, hasItems(ResourceType.TEXT_INDEX, ResourceType.CONNECTOR));
    }

    @Test
    public void testComplementOf() {
        final Set<ResourceType> complement = ResourceType.complementOf(ResourceType.of(ResourceType.CONNECTOR));

        assertThat(complement, hasSize(ResourceType.allOf().size() - 1));
        assertThat(complement, not(hasItem(ResourceType.CONNECTOR)));
    }
}