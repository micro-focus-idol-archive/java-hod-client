/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.util;

import com.hp.autonomy.iod.client.AbstractIodClientIntegrationTest;
import com.hp.autonomy.iod.client.Endpoint;
import com.hp.autonomy.iod.client.RestAdapterFactory;
import com.hp.autonomy.iod.client.api.search.Documents;
import com.hp.autonomy.iod.client.api.search.QueryRequestBuilder;
import com.hp.autonomy.iod.client.api.search.QueryTextIndexService;
import com.hp.autonomy.iod.client.error.IodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import retrofit.RestAdapter;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(Parameterized.class)
public class RequestInterceptorITCase extends AbstractIodClientIntegrationTest {

    private QueryTextIndexService queryTextIndexService;

    @Before
    public void setUp() {
        final RestAdapter restAdapter = RestAdapterFactory.getRestAdapter(true, endpoint);

        queryTextIndexService = restAdapter.create(QueryTextIndexService.class);
    }

    public RequestInterceptorITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testInterceptor() throws IodErrorException {
        final Map<String, Object> params = new QueryRequestBuilder()
                .addIndexes("wiki_eng")
                .setTotalResults(true)
                .build();

        final Documents documents = queryTextIndexService.queryTextIndexWithText("*", params);

        assertThat(documents.getTotalResults(), is(greaterThan(0)));
    }

}
