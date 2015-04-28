/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */
package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.AbstractQueryProfileIntegrationTest;
import com.hp.autonomy.iod.client.Endpoint;
import com.hp.autonomy.iod.client.error.IodErrorException;
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
    public void testRetrieval() throws IodErrorException {
        final QueryProfile qp = createQueryProfile("retr001");
        final QueryProfile retrievedQP = retrieveQueryProfileService.retrieveQueryProfile(endpoint.getApiKey(), qp.getName());

        assertThat(retrievedQP.getName(), is(qp.getName()));
        assertThat(retrievedQP.getConfig(), is(qp.getConfig()));
    }

    @Test(expected = IodErrorException.class)
    public void testFailure() throws IodErrorException {
        retrieveQueryProfileService.retrieveQueryProfile(endpoint.getApiKey(), "sometest");
    }
}
