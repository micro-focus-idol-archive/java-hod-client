/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DocumentTest {

    @Test
    public void testSimpleDocumentSerialization() throws IOException, ClassNotFoundException {
        final Document document = new Document.Builder()
            .setReference("my-reference")
            .setTitle("My Title")
            .setContent("Some cool stuff here")
            .setIndex("cool_stuff")
            .setLinks(new HashSet<>(Arrays.asList("cool", "stuff")))
            .setSection(0)
            .setSummary("cool stuff")
            .setWeight(99)
            .build();

        serializeAndDeserialize(document);
    }

    @Test
    public void testDocumentWithCustomFieldsSerialization() throws IOException, ClassNotFoundException {
        final Document document = new Document.Builder()
            .setReference("my-reference")
            .setTitle("My Title")
            .setContent("Some cool stuff here")
            .setIndex("cool_stuff")
            .setLinks(new HashSet<>(Arrays.asList("cool", "stuff")))
            .setSection(0)
            .setSummary("cool stuff")
            .setWeight(99)
            .setPromotionType(PromotionType.DYNAMIC_PROMOTION)
            .addField("string", "hello world")
            .addField("double", 42.0)
            // Arrays.asList returns List which is not serializable
            .addField("list", new ArrayList<>(Arrays.asList("stuff", "more stuff")))
            .build();

        serializeAndDeserialize(document);
    }

    private void serializeAndDeserialize(final Document document) throws IOException, ClassNotFoundException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try(final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(document);
        }

        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        try(final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            final Document documentFromStream = (Document) objectInputStream.readObject();

            assertThat(documentFromStream, is(document));
        }
    }

}
