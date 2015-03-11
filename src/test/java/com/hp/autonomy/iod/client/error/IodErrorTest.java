/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IodErrorTest {

    @Test
    public void testFromJson() throws IOException {
        final InputStream jsonStream = getClass().getResourceAsStream("/com/hp/autonomy/iod/client/error/error.json");

        final ObjectMapper mapper = new ObjectMapper();

        final IodError error = mapper.readValue(jsonStream, IodError.class);

        assertThat(error.getErrorCode(), is(IodErrorCode.MISSING_REQUIRED_PARAMETERS));
    }

}
