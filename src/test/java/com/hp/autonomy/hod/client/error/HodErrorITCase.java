/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.error;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.HodErrorTester;
import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.api.textindex.query.search.Document;
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

import static com.hp.autonomy.hod.client.HodErrorTester.testErrorCodeAndMessage;


@RunWith(Parameterized.class)
public class HodErrorITCase extends AbstractHodClientIntegrationTest {

    private QueryTextIndexService<Document> queryTextIndexService;

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
        testErrorCodeAndMessage(HodErrorCode.MISSING_REQUIRED_PARAMETERS, "Missing required parameter(s)", () -> queryTextIndexService.queryTextIndexWithText(getTokenProxy(), "", new QueryRequestBuilder()));
    }

    @Test
    public void testHodReturnsJobError() {
        final QueryRequestBuilder params = new QueryRequestBuilder()
            .addIndexes(ResourceIdentifier.WIKI_ENG);

        testErrorCodeAndMessage(HodErrorCode.NO_IGNORE_SPECIALS, "Invalid query text", () -> queryTextIndexService.queryTextIndexWithText(getTokenProxy(), "OR", params));
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

        testErrorCodeAndMessage(HodErrorCode.AUTHENTICATION_FAILED, "Authentication failed", () -> queryTextIndexService.queryTextIndexWithText(tokenProxy, "*", new QueryRequestBuilder()));
    }

}
