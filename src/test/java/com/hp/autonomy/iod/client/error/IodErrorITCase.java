package com.hp.autonomy.iod.client.error;

import com.hp.autonomy.iod.client.AbstractIodClientIntegrationTest;
import com.hp.autonomy.iod.client.api.search.QueryTextIndexService;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/*
 * $Id:$
 *
 * Copyright (c) 2015, Autonomy Systems Ltd.
 *
 * Last modified by $Author:$ on $Date:$
 */
public class IodErrorITCase extends AbstractIodClientIntegrationTest {

    private QueryTextIndexService queryTextIndexService;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        queryTextIndexService = getRestAdapter().create(QueryTextIndexService.class);
    }

    @Test
    public void testNoQueryTextError() {
        try {
            queryTextIndexService.queryTextIndexWithText(getApiKey(), "", null);
            fail("IodClientErrorException not thrown");
        } catch (final IodErrorException e) {
            assertThat(e.getErrorCode(), is(IodErrorCode.MISSING_REQUIRED_PARAMETERS));
            assertThat(e.getMessage(), is("Missing required parameter(s)"));
        }
    }

    @Test
    public void testIodReturnsJobError() {
        try {
            queryTextIndexService.queryTextIndexWithText(getApiKey(), "OR", null);
            fail("IodErrorException not thrown");
        } catch (final IodErrorException e) {
            assertThat(e.getErrorCode(), is(IodErrorCode.BACKEND_REQUEST_FAILED));
            assertThat(e.getMessage(), is("Backend request failed"));
        }
    }

    @Test
    public void testIodReturnsApiKeyError() {
        try {
            queryTextIndexService.queryTextIndexWithText("*", null);
            fail("IodErrorException not thrown");
        } catch (final IodErrorException e) {
            assertThat(e.getErrorCode(), is(IodErrorCode.API_KEY_REQUIRED));
            assertThat(e.getMessage(), is("API key required"));
        }
    }

    @Test
    public void testIodReturnsApiKeyErrorWithDuplicateKeys() {
        final Map<String, Object> params = new HashMap<>();
        params.put("apiKey", getApiKey());

        try {
            queryTextIndexService.queryTextIndexWithText(getApiKey(), "*", params);
            fail("IodErrorException not thrown");
        } catch (final IodErrorException e) {
            assertThat(e.getErrorCode(), is(IodErrorCode.INVALID_API_KEY));
            assertThat(e.getMessage(), is("Invalid API key"));
        }
    }

}
