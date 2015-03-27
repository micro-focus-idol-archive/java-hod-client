/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class QueryRequestBuilderTest {

    @Test
    public void testMinDateFormatFromJodaString() {
        final Map<String, Object> queryParams = new HashMap<>(new QueryRequestBuilder()
            .setMinDate(DateTime.parse("2015-03-04T10:39:00"))
            .build());

        assertThat(queryParams.get("min_date").toString(), is("10:39:00 04/03/2015 AD"));
    }

    @Test
    public void testMinDateFormatJodaLong() {
        final Map<String, Object> queryParams = new HashMap<>(new QueryRequestBuilder()
            .setMinDate(new DateTime(1425465540000L))
            .build());

        assertThat(queryParams.get("min_date").toString(), is("10:39:00 04/03/2015 AD"));
    }

    @Test
    public void testMaxDateFormatFromJodaString() {
        final Map<String, Object> queryParams = new HashMap<>(new QueryRequestBuilder()
            .setMaxDate(DateTime.parse("2015-03-04T10:39:00"))
            .build());

        assertThat(queryParams.get("max_date").toString(), is("10:39:00 04/03/2015 AD"));
    }

    @Test
    public void testMaxDateFormatJodaLong() {
        final Map<String, Object> queryParams = new HashMap<>(new QueryRequestBuilder()
            .setMaxDate(new DateTime(1425465540000L))
            .build());

        assertThat(queryParams.get("max_date").toString(), is("10:39:00 04/03/2015 AD"));
    }

    @Test
    public void testMinDatePrecedence() {
        final Map<String, Object> queryParams = new HashMap<>(new QueryRequestBuilder()
            .setMinDate(new DateTime(1425465540000L))
            .setMinDateDays(20L)
            .setMinDateSeconds(20L)
            .build());

        assertThat(queryParams.get("min_date").toString(), is("10:39:00 04/03/2015 AD"));

        final Map<String, Object> queryParams2 = new HashMap<>(new QueryRequestBuilder()
            .setMinDateDays(20L)
            .setMinDateSeconds(20L)
            .build());

        assertThat(queryParams2.get("min_date").toString(), is("20"));

        final Map<String, Object> queryParams3 = new HashMap<>(new QueryRequestBuilder()
            .setMinDate(new DateTime(1425465540000L))
            .setMinDateSeconds(20L)
            .build());

        assertThat(queryParams3.get("min_date").toString(), is("10:39:00 04/03/2015 AD"));
    }

    @Test
    public void testMaxDatePrecedence() {
        final Map<String, Object> queryParams = new HashMap<>(new QueryRequestBuilder()
            .setMaxDate(new DateTime(1425465540000L))
            .setMaxDateDays(20L)
            .setMaxDateSeconds(20L)
            .build());

        assertThat(queryParams.get("max_date").toString(), is("10:39:00 04/03/2015 AD"));

        final Map<String, Object> queryParams2 = new HashMap<>(new QueryRequestBuilder()
            .setMaxDateDays(20L)
            .setMaxDateSeconds(20L)
            .build());

        assertThat(queryParams2.get("max_date").toString(), is("20"));

        final Map<String, Object> queryParams3 = new HashMap<>(new QueryRequestBuilder()
            .setMaxDate(new DateTime(1425465540000L))
            .setMaxDateSeconds(20L)
            .build());

        assertThat(queryParams3.get("max_date").toString(), is("10:39:00 04/03/2015 AD"));
    }

    @Test
    public void testPrintFields() {
        final Map<String, Object> queryParams = new HashMap<>(new QueryRequestBuilder()
            .setPrintFields(Arrays.asList("TITLE", "CHAPTER"))
            .build());

        assertThat(queryParams.get("print_fields").toString(), is("TITLE,CHAPTER"));
    }

}
