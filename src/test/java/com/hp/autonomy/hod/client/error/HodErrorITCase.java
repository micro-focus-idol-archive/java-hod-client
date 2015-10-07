/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.error;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.api.textindex.query.search.Documents;
import com.hp.autonomy.hod.client.api.textindex.query.search.QueryRequestBuilder;
import com.hp.autonomy.hod.client.api.textindex.query.search.QueryTextIndexService;
import com.hp.autonomy.hod.client.api.textindex.query.search.QueryTextIndexServiceImpl;
import com.hp.autonomy.hod.client.token.TokenProxy;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


@RunWith(Parameterized.class)
public class HodErrorITCase extends AbstractHodClientIntegrationTest {

    private QueryTextIndexService<Documents> queryTextIndexService;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        queryTextIndexService = QueryTextIndexServiceImpl.documentsService(getConfig());
    }

    public HodErrorITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testNoQueryTextError() {
        try {
            queryTextIndexService.queryTextIndexWithText(getTokenProxy(), "", new QueryRequestBuilder());
            fail("HodErrorException not thrown");
        } catch (final HodErrorException e) {
            assertThat(e.getErrorCode(), is(HodErrorCode.MISSING_REQUIRED_PARAMETERS));
            assertThat(e.getMessage(), is("Missing required parameter(s)"));
        }
    }

    @Test
    public void testHodReturnsJobError() {
        try {
            final QueryRequestBuilder params = new QueryRequestBuilder()
                .addIndexes(ResourceIdentifier.WIKI_ENG);

            queryTextIndexService.queryTextIndexWithText(getTokenProxy(), "OR", params);
            fail("HodErrorException not thrown");
        } catch (final HodErrorException e) {
            assertThat(e.getErrorCode(), is(HodErrorCode.NO_IGNORE_SPECIALS));
            assertThat(e.getMessage(), is("Invalid query text"));
        }
    }

    @Test
    public void testHodReturnsApiKeyError() throws IOException {
        final TokenProxy<EntityType.Application, TokenType.Simple> tokenProxy = getConfig().getTokenRepository().insert(new AuthenticationToken<>(
            EntityType.Application.INSTANCE,
            TokenType.Simple.INSTANCE,
            DateTime.now().plus(Hours.ONE),
            "ID",
            "SECRET",
            new DateTime(1234567890L)
        ));

        try {
            queryTextIndexService.queryTextIndexWithText(tokenProxy, "*", new QueryRequestBuilder());
            fail("HodErrorException not thrown");
        } catch (final HodErrorException e) {
            assertThat(e.getErrorCode(), is(HodErrorCode.AUTHENTICATION_FAILED));
            assertThat(e.getMessage(), is("Authentication failed"));
        }
    }

}
