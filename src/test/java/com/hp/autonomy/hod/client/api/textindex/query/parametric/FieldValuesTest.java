package com.hp.autonomy.hod.client.api.textindex.query.parametric;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FieldValuesTest {
    private ObjectMapper objectMapper;

    @Before
    public void setUp(){
        objectMapper = new ObjectMapper();
    }

    @Test
    public void deserializesFromSimpleResponse() throws IOException {
        final List<FieldValues.ValueAndCount> values = Stream.of("DEFAULT", "ANIMAL", "PLACE")
                .map(value -> FieldValues.ValueAndCount.builder().value(value).build())
                .collect(Collectors.toList());

        final FieldValues expectedOutput = FieldValues.builder()
                .name("category")
                .values(values)
                .build();

        testDeserialization("field-values-simple.json", expectedOutput);
    }

    @Test
    public void deserializesFromTotalValuesAndDocumentCountResponse() throws IOException {
        final List<FieldValues.ValueAndCount> values = Arrays.asList(
                FieldValues.ValueAndCount.builder().value("DEFAULT").count(4).build(),
                FieldValues.ValueAndCount.builder().value("ANIMAL").count(2).build(),
                FieldValues.ValueAndCount.builder().value("PLACE").count(1).build()
        );

        final FieldValues expectedOutput = FieldValues.builder()
                .name("category")
                .totalValues(5)
                .values(values)
                .build();

        testDeserialization("field-values-total-values.json", expectedOutput);
    }

    @Test
    public void deserializesFromNestedFieldsResponse() throws IOException {
        final List<FieldValues.ValueAndCount> plainCategoryValues = Arrays.asList(
                FieldValues.ValueAndCount.builder().value("ANIMAL").count(1).build(),
                FieldValues.ValueAndCount.builder().value("AWESOME").count(1).build(),
                FieldValues.ValueAndCount.builder().value(null).count(1).build()
        );

        final FieldValues.ValueAndCount textPlainValue = FieldValues.ValueAndCount.builder()
                .value("TEXT/PLAIN")
                .count(3)
                .subField(FieldValues.builder().name("category").values(plainCategoryValues).build())
                .build();

        final FieldValues.ValueAndCount htmlCategoryValue = FieldValues.ValueAndCount.builder()
                .value(null)
                .count(1)
                .build();

        final FieldValues.ValueAndCount textHtmlValue = FieldValues.ValueAndCount.builder()
                .value("TEXT/HTML")
                .count(1)
                .subField(FieldValues.builder().name("category").values(Collections.singletonList(htmlCategoryValue)).build())
                .build();

        final List<FieldValues.ValueAndCount> contentTypeValues = Arrays.asList(
                textPlainValue,
                textHtmlValue
        );

        final FieldValues expectedOutput = FieldValues.builder()
                .name("content_type")
                .values(contentTypeValues)
                .build();

        testDeserialization("field-values-nested.json", expectedOutput);
    }

    private void testDeserialization(final String inputFile, final FieldValues expectedOutput) throws IOException {
        try (final InputStream inputStream = getClass().getResourceAsStream(inputFile)) {
            final FieldValues output = objectMapper.readValue(inputStream, FieldValues.class);
            assertThat(output, is(expectedOutput));
        }
    }
}