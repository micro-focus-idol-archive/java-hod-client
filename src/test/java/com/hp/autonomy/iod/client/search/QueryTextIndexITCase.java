package com.hp.autonomy.iod.client.search;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;
import retrofit.RestAdapter;
import retrofit.client.ApacheClient;
import retrofit.converter.JacksonConverter;
import retrofit.mime.TypedFile;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/*
 * $Id:$
 *
 * Copyright (c) 2015, Autonomy Systems Ltd.
 *
 * Last modified by $Author:$ on $Date:$
 */
public class QueryTextIndexITCase {

    private QueryTextIndexService queryTextIndexService;

    @Before
    public void setUp() {
        final HttpClientBuilder builder = HttpClientBuilder.create();

        final String proxyHost = System.getProperty("hp.iod.https.proxyHost");

        if(proxyHost != null) {
            final Integer proxyPort = Integer.valueOf(System.getProperty("hp.iod.https.proxyPort", "8080"));
            builder.setProxy(new HttpHost(proxyHost, proxyPort));
        }

        final RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint("http://api.idolondemand.com/1/api")
            .setClient(new ApacheClient(builder.build()))
            .setConverter(new JacksonConverter())
            .build();

        queryTextIndexService = restAdapter.create(QueryTextIndexService.class);
    }

    @Test
    public void testQueryForText() {
        final Map<String, Object> params = new QueryTextIndexRequestBuilder()
            .setMaxPageResults(10)
            .setAbsoluteMaxResults(10)
            .setSummary(QueryTextIndexRequestBuilder.Summary.concept)
            .setPrint(QueryTextIndexRequestBuilder.Print.all)
            .setTotalResults(true)
            .build();

        final Documents documents = queryTextIndexService.queryTextIndexWithText(System.getProperty("hp.iod.apiKey"), "*", Arrays.asList("wiki_eng"), params);

        assertThat(documents.getTotalResults(), is(greaterThan(0)));

        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(10));

        final Document document0 = documentList.get(0);

        assertThat(document0.getFields().keySet(), hasSize(greaterThan(0)));
    }

    @Test
    public void testQueryForFile() {
        final TypedFile file = new TypedFile("text/plain", new File("src/test/resources/com/hp/autonomy/iod/client/search/queryText.txt"));
        final Map<String, Object> params = new QueryTextIndexRequestBuilder()
            .setMaxPageResults(10)
            .setAbsoluteMaxResults(10)
            .build();

        final Documents documents = queryTextIndexService.queryTextIndexWithFile(System.getProperty("hp.iod.apiKey"), file, null, params);
        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(10));
    }

}
