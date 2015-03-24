/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.AbstractIodClientIntegrationTest;
import com.hp.autonomy.iod.client.error.IodErrorException;
import com.hp.autonomy.iod.client.util.TypedByteArrayWithFilename;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedOutput;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class QueryTextIndexITCase extends AbstractIodClientIntegrationTest {

    private QueryTextIndexService queryTextIndexService;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        queryTextIndexService = getRestAdapter().create(QueryTextIndexService.class);
    }

    @Test
    public void testQueryForText() throws IodErrorException {
        final Map<String, Object> params = new QueryTextIndexRequestBuilder()
            .setMaxPageResults(10)
            .setAbsoluteMaxResults(10)
            .setSummary(QueryTextIndexRequestBuilder.Summary.concept)
            .setPrint(Print.all)
            .setTotalResults(true)
            .addIndexes("wiki_eng", "wiki_ita")
            .build();

        final Documents documents = queryTextIndexService.queryTextIndexWithText(getApiKey(), "*", params);

        assertThat(documents.getTotalResults(), is(greaterThan(0)));

        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(10));

        final Document document0 = documentList.get(0);

        assertThat(document0.getFields().keySet(), hasSize(greaterThan(0)));
    }

    @Test
    public void testQueryForFile() throws IodErrorException {
        final TypedFile file = new TypedFile("text/plain", new File("src/test/resources/com/hp/autonomy/iod/client/api/search/queryText.txt"));
        final Map<String, Object> params = new QueryTextIndexRequestBuilder()
            .setMaxPageResults(10)
            .setAbsoluteMaxResults(10)
            .addIndexes("wiki_ita", "wiki_eng")
            .setSort(QueryTextIndexRequestBuilder.Sort.date)
            .build();

        final Documents documents = queryTextIndexService.queryTextIndexWithFile(getApiKey(), file, params);
        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(10));
    }

    @Test
    public void testQueryForFileAsStream() throws IodErrorException, IOException {
        final InputStream stream = getClass().getResourceAsStream("/com/hp/autonomy/iod/client/api/search/queryText.txt");

        final TypedOutput file = new TypedByteArrayWithFilename("text/plain", IOUtils.toByteArray(stream));
        final Map<String, Object> params = new QueryTextIndexRequestBuilder()
            .setMaxPageResults(10)
            .setAbsoluteMaxResults(10)
            .addIndexes("wiki_ita", "wiki_eng")
            .setSort(QueryTextIndexRequestBuilder.Sort.date)
            .build();

        final Documents documents = queryTextIndexService.queryTextIndexWithFile(getApiKey(), file, params);
        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(10));
    }

}
