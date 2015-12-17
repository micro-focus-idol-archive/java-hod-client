/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.converter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import retrofit.converter.ConversionException;
import retrofit.converter.JacksonConverter;
import retrofit.mime.TypedString;

public class HodConverterTest {

    private HodConverter hodConverter;

    @Before
    public void setUp() {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JacksonConverter jacksonConverter = new JacksonConverter(objectMapper);

        this.hodConverter = new HodConverter(jacksonConverter);
    }

    @Test(expected = HodUnavailableException.class)
    public void testJsonParseException() throws ConversionException {
        hodConverter.fromBody(new TypedString("The service is unavailable."), TypedString.class);
    }

}
