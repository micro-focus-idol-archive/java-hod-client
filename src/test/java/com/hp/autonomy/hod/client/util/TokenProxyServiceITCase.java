/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.util;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.HodServiceConfigFactory;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.api.textindex.query.search.Documents;
import com.hp.autonomy.hod.client.api.textindex.query.search.QueryRequestBuilder;
import com.hp.autonomy.hod.client.api.textindex.query.search.QueryTextIndexService;
import com.hp.autonomy.hod.client.api.textindex.query.search.QueryTextIndexServiceImpl;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import com.hp.autonomy.hod.client.token.TokenProxyService;
import com.hp.autonomy.hod.client.token.TokenRepositoryException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(Parameterized.class)
public class TokenProxyServiceITCase extends AbstractHodClientIntegrationTest {

    private QueryTextIndexService<Documents> queryTextIndexService;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        // Creating the HodServiceConfig requires the TokenProxyService, but the TokenProxyService requires the
        // TokenRepository, which requires the HodServiceConfig
        final AtomicReference<TokenProxy<EntityType.Application, TokenType.Simple>> tokenProxyAtomicReference = new AtomicReference<>();

        final TokenProxyService<EntityType.Application, TokenType.Simple> tokenProxyService = new TokenProxyService<EntityType.Application, TokenType.Simple>() {
            @Override
            public TokenProxy<EntityType.Application, TokenType.Simple> getTokenProxy() {
                return tokenProxyAtomicReference.get();
            }
        };

        final HodServiceConfig<EntityType.Application, TokenType.Simple> hodServiceConfig = HodServiceConfigFactory.getHodServiceConfig(tokenProxyService, getEndpoint());

        try {
            tokenProxyAtomicReference.set(hodServiceConfig.getTokenRepository().insert(getToken()));
        } catch (final IOException e) {
            throw new TokenRepositoryException(e);
        }

        queryTextIndexService = QueryTextIndexServiceImpl.documentsService(hodServiceConfig);
    }

    public TokenProxyServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testInterceptor() throws HodErrorException {
        final QueryRequestBuilder params = new QueryRequestBuilder()
            .addIndexes(ResourceIdentifier.WIKI_ENG)
            .setTotalResults(true);

        final Documents documents = queryTextIndexService.queryTextIndexWithText("*", params);

        assertThat(documents.getTotalResults(), is(greaterThan(0)));
    }

}
