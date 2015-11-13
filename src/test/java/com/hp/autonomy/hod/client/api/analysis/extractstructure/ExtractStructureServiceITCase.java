/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.analysis.extractstructure;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class ExtractStructureServiceITCase extends AbstractHodClientIntegrationTest {

    private ExtractStructureService extractStructureService;

    public static final String TEST_STRING = "Column1,Column2,Column3,Column4\n" +
        "R1C1,R1C2,R1C3,R1C4\n" +
        "R2C1,R2C2,R2C3,R2C4\n" +
        "R3C1,R3C2,R3C3,R3C4\n" +
        "R4C1,R4C2,R4C3,R4C4\n" +
        "R5C1,R5C2,R5C3,R5C4\n" +
        "R6C1,R6C2,R6C3,R6C4";

    public ExtractStructureServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();

        extractStructureService = new ExtractStructureServiceImpl(getConfig());
    }

    @Test
    public void testExtractStructureForByteArray() throws HodErrorException {
        final List<LinkedHashMap<String, String>> result = extractStructureService.extractFromFile(getTokenProxy(), TEST_STRING.getBytes());

        assertThat(result, hasSize(6));
        assertThat(result.get(0).entrySet(), hasSize(4));
        assertThat(result.get(5).get("Column4"), is("R6C4"));
    }

    @Test
    public void testExtractStructureForInputStream() throws HodErrorException {
        final InputStream inputStream = new ByteArrayInputStream(TEST_STRING.getBytes());
        final List<LinkedHashMap<String, String>> result = extractStructureService.extractFromFile(getTokenProxy(), inputStream);

        assertThat(result, hasSize(6));
        assertThat(result.get(0).entrySet(), hasSize(4));
        assertThat(result.get(5).get("Column4"), is("R6C4"));
    }
}