/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.error;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.textindex.query.search.QueryTextIndexService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


@RunWith(Parameterized.class)
public class HodErrorITCase extends AbstractHodClientIntegrationTest {

    private QueryTextIndexService queryTextIndexService;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        queryTextIndexService = getRestAdapter().create(QueryTextIndexService.class);
    }

    public HodErrorITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testNoQueryTextError() {
        try {
            queryTextIndexService.queryTextIndexWithText(getToken(), "", null);
            fail("HodErrorException not thrown");
        } catch (final HodErrorException e) {
            assertThat(e.getErrorCode(), is(HodErrorCode.MISSING_REQUIRED_PARAMETERS));
            assertThat(e.getMessage(), is("Missing required parameter(s)"));
        }
    }

    @Test
    public void testHodReturnsJobError() {
        try {
            queryTextIndexService.queryTextIndexWithText(getToken(), "OR", null);
            fail("HodErrorException not thrown");
        } catch (final HodErrorException e) {
            assertThat(e.getErrorCode(), is(HodErrorCode.BACKEND_REQUEST_FAILED));
            assertThat(e.getMessage(), is("Backend request failed"));
        }
    }

    @Test
    public void testHodReturnsApiKeyError() {
        try {
            queryTextIndexService.queryTextIndexWithText("*", null);
            fail("HodErrorException not thrown");
        } catch (final HodErrorException e) {
            assertThat(e.getErrorCode(), is(HodErrorCode.API_KEY_REQUIRED));
            assertThat(e.getMessage(), is("API key required"));
        }
    }

    @Test
    public void testHodReturnsApiKeyErrorWithDuplicateKeys() {
        final Map<String, Object> params = new HashMap<>();
        params.put("apiKey", endpoint.getApiKey());

        try {
            queryTextIndexService.queryTextIndexWithText(getToken(), "*", params);
            fail("HodErrorException not thrown");
        } catch (final HodErrorException e) {
            assertThat(e.getErrorCode(), is(HodErrorCode.INVALID_API_KEY));
            assertThat(e.getMessage(), is("Invalid API key"));
        }
    }

}
