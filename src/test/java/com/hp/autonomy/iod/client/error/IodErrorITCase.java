package com.hp.autonomy.iod.client.error;

import com.hp.autonomy.iod.client.AbstractIodClientIntegrationTest;
import com.hp.autonomy.iod.client.api.search.QueryTextIndexService;
import org.junit.Before;
import org.junit.Test;

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
            queryTextIndexService.queryTextIndexWithText(System.getProperty("hp.iod.apiKey"), "", null);
            fail("IodClientErrorException not thrown");
        } catch (final IodErrorException e) {
            assertThat(e.getErrorCode(), is(IodErrorCode.MISSING_REQUIRED_PARAMETERS));
            assertThat(e.getMessage(), is("Missing required parameter(s)"));
        }
    }

    @Test
    public void testIodReturnsJobError() {
        try {
            queryTextIndexService.queryTextIndexWithText(System.getProperty("hp.iod.apiKey"), "OR", null);
            fail("IodErrorException not thrown");
        } catch (final IodErrorException e) {
            assertThat(e.getErrorCode(), is(IodErrorCode.BACKEND_REQUEST_FAILED));
            assertThat(e.getMessage(), is("Backend request failed"));
        }
    }

}
