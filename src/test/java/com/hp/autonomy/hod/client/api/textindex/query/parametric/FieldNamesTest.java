/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.parametric;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.autonomy.types.requests.idol.actions.tags.QueryTagCountInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@Slf4j
public class FieldNamesTest {

    private FieldNames fieldNames;

    @Before
    public void initialize() {
        final Map<String, Integer> fieldValue0 = new LinkedHashMap<>();
        fieldValue0.put("1", 1);
        fieldValue0.put("2", 2);

        final Map<String, Integer> fieldValue1 = new LinkedHashMap<>();
        fieldValue1.put("3", 3);
        fieldValue1.put("4", 4);
        fieldValue1.put("5", 5);

        fieldNames = new FieldNames.Builder()
                .addParametricValue("zero", fieldValue0)
                .addParametricValue("one", fieldValue1)
                .build();
    }

    @Test
    public void testGetFieldSet() {
        final Set<String> fieldSet = fieldNames.getFieldNames();

        assertThat(fieldSet, hasItems("zero", "one"));
    }

    @Test
    public void testGetFieldValueCount() {
        assertThat(fieldNames.getFieldValueCount("zero", "1"), is(1));
        assertThat(fieldNames.getFieldValueCount("one", "4"), is(4));
    }

    @Test
    public void testGetValuesForFieldName() {
        final Set<String> setZero = fieldNames.getValuesForFieldName("zero");
        assertThat(setZero, hasItems("1", "2"));

        final Set<String> setOne = fieldNames.getValuesForFieldName("one");
        assertThat(setOne, hasItems("3", "4", "5"));
    }

    @Test
    public void testGetValuesAndCountsForFieldName() {
        final List<QueryTagCountInfo> parametricValues = fieldNames.getValuesAndCountsForFieldName("zero");
        final QueryTagCountInfo firstEntry = parametricValues.get(0);
        assertThat(firstEntry.getValue(), is("1"));
        assertThat(firstEntry.getCount(), is(1));
    }

    @Test
    public void getValuesAndCountsForNumericField() {
        final Map<String, Integer> numericField = new LinkedHashMap<>();
        numericField.put("0", 1);
        numericField.put("0, 4, 12", 2);
        numericField.put("4", 3);
        numericField.put("5", 1);

        final FieldNames fieldNames = new FieldNames.Builder()
                .addParametricValue("numericField", numericField)
                .build();
        final List<QueryTagCountInfo> numericParametricValues = fieldNames.getValuesAndCountsForNumericField("numericField");
        final Iterator<QueryTagCountInfo> iterator = numericParametricValues.iterator();
        assertEquals(new QueryTagCountInfo("0", 3), iterator.next());
        assertEquals(new QueryTagCountInfo("4", 5), iterator.next());
        assertEquals(new QueryTagCountInfo("5", 1), iterator.next());
        assertEquals(new QueryTagCountInfo("12", 2), iterator.next());
    }

    @Test
    public void getValuesAndCountsForNumericFieldNoResults() {
        assertThat(new FieldNames.Builder().build().getValuesAndCountsForNumericField("numericField"), empty());
    }

    @Test
    public void testIterator() {
        int x = 0;

        for (final FieldNames.ParametricValue value : fieldNames) {
            x++;

            assertThat(value.getValue(), is(String.valueOf(x)));
            assertThat(value.getCount(), is(x));

            if (x < 3) {
                assertThat(value.getFieldName(), is("zero"));
            } else {
                assertThat(value.getFieldName(), is("one"));
            }

        }

    }

    @Test
    public void testGetJson() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Writer output = new StringWriter();
        mapper.writeValue(output, fieldNames);
        final String json = output.toString();

        assertThat(json, is("{\"zero\":[{\"value\":\"1\",\"count\":1},{\"value\":\"2\",\"count\":2}],\"one\":[{\"value\":\"3\",\"count\":3},{\"value\":\"4\",\"count\":4},{\"value\":\"5\",\"count\":5}]}"));
    }

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (final ObjectOutput objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(fieldNames);
        }

        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        try (final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            @SuppressWarnings("CastToConcreteClass")
            final FieldNames fieldNamesFromStream = (FieldNames) objectInputStream.readObject();

            assertThat(fieldNamesFromStream, is(fieldNames));
        }
    }
}
