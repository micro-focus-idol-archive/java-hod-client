/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.util;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.HodServiceConfigFactory;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.textindex.query.search.Documents;
import com.hp.autonomy.hod.client.api.textindex.query.search.QueryRequestBuilder;
import com.hp.autonomy.hod.client.api.textindex.query.search.QueryTextIndexBackend;
import com.hp.autonomy.hod.client.error.HodErrorException;
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
public class RequestInterceptorITCase extends AbstractHodClientIntegrationTest {

    private QueryTextIndexBackend queryTextIndexBackend;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        final RestAdapter restAdapter = HodServiceConfigFactory.getHodServiceConfig(new AuthenticationTokenService() {
            @Override
            public AuthenticationToken getToken() {
                return RequestInterceptorITCase.this.getToken();
            }
        }, endpoint).getRestAdapter();

        queryTextIndexBackend = restAdapter.create(QueryTextIndexBackend.class);
    }

    public RequestInterceptorITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testInterceptor() throws HodErrorException {
        final Map<String, Object> params = new QueryRequestBuilder()
                .addIndexes("wiki_eng")
                .setTotalResults(true)
                .build();

        final Documents documents = queryTextIndexBackend.queryTextIndexWithText("*", params);

        assertThat(documents.getTotalResults(), is(greaterThan(0)));
    }

}
