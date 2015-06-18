/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */
package com.hp.autonomy.hod.client.api.queryprofile;

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

    private QueryProfileService queryProfileService;

    public RetrieveQueryProfileServiceSuiteChild(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        queryProfileService = new QueryProfileServiceImpl(getConfig());
    }

    @Test
    public void testRetrieval() throws HodErrorException {
        final QueryProfile qp = createQueryProfile("retr001");
        final QueryProfile retrievedQP = queryProfileService.retrieveQueryProfile(getTokenProxy(), qp.getName());

        assertThat(retrievedQP, is(qp));
    }

    @Test(expected = HodErrorException.class)
    public void testFailure() throws HodErrorException {
        queryProfileService.retrieveQueryProfile(getTokenProxy(), "sometest");
    }
}
