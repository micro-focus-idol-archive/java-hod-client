/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */
package com.hp.autonomy.hod.client.api.search;

import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.AbstractQueryProfileIntegrationTest;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class RetrieveQueryProfileServiceSuiteChild extends AbstractQueryProfileIntegrationTest {
    public RetrieveQueryProfileServiceSuiteChild(Endpoint endpoint) {
        super(endpoint);
    }

    // Service under test
    private RetrieveQueryProfileService retrieveQueryProfileService;

    @Before
    public void setUp() {
        super.setUp();
        retrieveQueryProfileService = getRestAdapter().create(RetrieveQueryProfileService.class);
    }

    @Test
    public void testRetrieval() throws HodErrorException {
        final QueryProfile qp = createQueryProfile("retr001");
        final QueryProfile retrievedQP = retrieveQueryProfileService.retrieveQueryProfile(endpoint.getApiKey(), qp.getName());

        assertThat(retrievedQP.getName(), is(qp.getName()));
        assertThat(retrievedQP.getConfig(), is(qp.getConfig()));
    }

    @Test(expected = HodErrorException.class)
    public void testFailure() throws HodErrorException {
        retrieveQueryProfileService.retrieveQueryProfile(endpoint.getApiKey(), "sometest");
    }
}
