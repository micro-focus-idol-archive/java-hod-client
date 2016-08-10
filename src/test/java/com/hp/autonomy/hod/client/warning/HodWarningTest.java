package com.hp.autonomy.hod.client.warning;

/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class HodWarningTest {

    @Test
    public void testFromJson() throws IOException {
        final InputStream jsonStream = getClass().getResourceAsStream("/com/hp/autonomy/hod/client/warning/warning.json");

        final ObjectMapper mapper = new ObjectMapper();

        final HodWarning warning = mapper.readValue(jsonStream, HodWarning.class);

        assertThat(warning.getCode(), is(HodWarningCode.PROCESSING_QUERY_MANIPULATION_PROMOTION_ERROR));
        assertEquals(warning.getDetails(), "{\"reason\":\"Document for reference 161-23/12/2015-11:00 and index tvguide2 is missing.\"}");
    }

}