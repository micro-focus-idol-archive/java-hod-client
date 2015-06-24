/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class QueryTextIndexServiceITCase extends AbstractHodClientIntegrationTest {

    private QueryTextIndexService<Documents> queryTextIndexService;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        queryTextIndexService = QueryTextIndexServiceImpl.documentsService(getConfig());
    }

    public QueryTextIndexServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testQueryForText() throws HodErrorException {
        final QueryRequestBuilder params = new QueryRequestBuilder()
                .setMaxPageResults(10)
                .setAbsoluteMaxResults(10)
                .setSummary(Summary.concept)
                .setPrint(Print.all)
                .setTotalResults(true)
                .addIndexes("wiki_eng", "wiki_ger");

        final Documents documents = queryTextIndexService.queryTextIndexWithText(getTokenProxy(), "*", params);

        assertThat(documents.getTotalResults(), is(greaterThan(0)));

        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(10));

        final Document document0 = documentList.get(0);

        assertThat(document0.getFields().keySet(), hasSize(greaterThan(0)));
    }

    @Test
    public void testQueryForFile() throws HodErrorException {
        final File file = new File("src/test/resources/com/hp/autonomy/hod/client/api/textindexing/query/queryText.txt");
        final QueryRequestBuilder params = new QueryRequestBuilder()
                .setMaxPageResults(10)
                .setAbsoluteMaxResults(10)
                .addIndexes("wiki_ger", "wiki_eng")
                .setSort(Sort.date);

        final Documents documents = queryTextIndexService.queryTextIndexWithFile(getTokenProxy(), file, params);
        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(10));
    }

    @Test
    public void testQueryForFileAsStream() throws HodErrorException, IOException {
        final InputStream stream = getClass().getResourceAsStream("/com/hp/autonomy/hod/client/api/textindexing/query/queryText.txt");

        final QueryRequestBuilder params = new QueryRequestBuilder()
                .setMaxPageResults(10)
                .setAbsoluteMaxResults(10)
                .addIndexes("wiki_ger", "wiki_eng")
                .setSort(Sort.date);

        final Documents documents = queryTextIndexService.queryTextIndexWithFile(getTokenProxy(), stream, params);
        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(10));
    }

}
