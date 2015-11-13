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

public class ResourceFlavourTest {

    @Test
    public void testSerializeAndDeserialize() throws IOException, ClassNotFoundException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(ResourceFlavour.EXPLORER);

        final ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        final ResourceFlavour resourceFlavour = (ResourceFlavour) objectInputStream.readObject();

        assertThat(resourceFlavour, is(ResourceFlavour.EXPLORER));
    }

    // this test uses reflection to check allOf contains all the types
    // we don't use reflection in the class for performance reasons
    @Test
    public void testAllOf() throws IllegalAccessException {
        final Field[] fields = ResourceFlavour.class.getFields();

        final Set<ResourceFlavour> resourceFlavours = new HashSet<>();

        final ResourceFlavour dummy = ResourceFlavour.EXPLORER;

        for(final Field field : fields) {
            final int modifiers = field.getModifiers();
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers) && field.getType() == ResourceFlavour.class) {
                resourceFlavours.add((ResourceFlavour) field.get(dummy));
            }
        }

        assertThat(ResourceFlavour.allOf(), is(resourceFlavours));
    }

    @Test
    public void testOf() {
        final Set<ResourceFlavour> resourceFlavours = ResourceFlavour.of(ResourceFlavour.EXPLORER, ResourceFlavour.STANDARD);

        assertThat(resourceFlavours, hasSize(2));
        assertThat(resourceFlavours, hasItems(ResourceFlavour.EXPLORER, ResourceFlavour.STANDARD));
    }

    @Test
    public void testComplementOf() {
        final Set<ResourceFlavour> complement = ResourceFlavour.complementOf(ResourceFlavour.of(ResourceFlavour.EXPLORER));

        assertThat(complement, hasSize(ResourceFlavour.allOf().size() - 1));
        assertThat(complement, not(hasItem(ResourceFlavour.EXPLORER)));
    }

}