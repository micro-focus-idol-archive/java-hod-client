package com.hp.autonomy.hod.client.api.textindex.query.parametric;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FieldRangesTest {
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testDeserialization() throws IOException {
        final FieldRanges.ValueDetails valueDetails = FieldRanges.ValueDetails.builder()
                .count(3)
                .maximum(772.5)
                .minimum(510D)
                .mean(649D)
                .sum(1947D)
                .build();

        final FieldRanges expectedOutput = FieldRanges.builder()
                .name("file_size")
                .totalRanges(2)
                .valueDetails(valueDetails)
                .valueRanges(Arrays.asList(
                        FieldRanges.ValueRange.builder().lowerBound(400D).upperBound(600D).count(1).build(),
                        FieldRanges.ValueRange.builder().lowerBound(600D).upperBound(800D).count(2).build()
                ))
                .build();

        try (final InputStream inputStream = getClass().getResourceAsStream("field-ranges.json")) {
            final FieldRanges output = objectMapper.readValue(inputStream, FieldRanges.class);
            assertThat(output, is(expectedOutput));
        }
    }
}