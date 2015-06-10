/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.util.TypedByteArrayWithFilename;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
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

@RunWith(Parameterized.class)
public class QueryTextIndexITCase extends AbstractHodClientIntegrationTest {

    private QueryTextIndexBackend queryTextIndexBackend;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        queryTextIndexBackend = getRestAdapter().create(QueryTextIndexBackend.class);
    }

    public QueryTextIndexITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testQueryForText() throws HodErrorException {
        final Map<String, Object> params = new QueryRequestBuilder()
                .setMaxPageResults(10)
                .setAbsoluteMaxResults(10)
                .setSummary(Summary.concept)
                .setPrint(Print.all)
                .setTotalResults(true)
                .addIndexes("wiki_eng", "wiki_ita")
                .build();

        final Documents documents = queryTextIndexBackend.queryTextIndexWithText(getToken(), "*", params);

        assertThat(documents.getTotalResults(), is(greaterThan(0)));

        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(10));

        final Document document0 = documentList.get(0);

        assertThat(document0.getFields().keySet(), hasSize(greaterThan(0)));
    }

    @Test
    public void testQueryForFile() throws HodErrorException {
        final TypedFile file = new TypedFile("text/plain", new File("src/test/resources/com/hp/autonomy/hod/client/api/textindexing/query/queryText.txt"));
        final Map<String, Object> params = new QueryRequestBuilder()
                .setMaxPageResults(10)
                .setAbsoluteMaxResults(10)
                .addIndexes("wiki_ger", "wiki_eng")
                .setSort(Sort.date)
                .build();

        final Documents documents = queryTextIndexBackend.queryTextIndexWithFile(getToken(), file, params);
        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(10));
    }

    @Test
    public void testQueryForFileAsStream() throws HodErrorException, IOException {
        final InputStream stream = getClass().getResourceAsStream("/com/hp/autonomy/hod/client/api/textindexing/query/queryText.txt");

        final TypedOutput file = new TypedByteArrayWithFilename("text/plain", IOUtils.toByteArray(stream));
        final Map<String, Object> params = new QueryRequestBuilder()
                .setMaxPageResults(10)
                .setAbsoluteMaxResults(10)
                .addIndexes("wiki_ger", "wiki_eng")
                .setSort(Sort.date)
                .build();

        final Documents documents = queryTextIndexBackend.queryTextIndexWithFile(getToken(), file, params);
        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(10));
    }

}
