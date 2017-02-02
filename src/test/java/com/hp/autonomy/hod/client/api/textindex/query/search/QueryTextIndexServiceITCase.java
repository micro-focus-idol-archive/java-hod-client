/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.queryprofile.QueryProfileRequestBuilder;
import com.hp.autonomy.hod.client.api.queryprofile.QueryProfileService;
import com.hp.autonomy.hod.client.api.queryprofile.QueryProfileServiceImpl;
import com.hp.autonomy.hod.client.api.resource.*;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class QueryTextIndexServiceITCase extends AbstractHodClientIntegrationTest {

    private QueryTextIndexService<Document> queryTextIndexService;

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
                .addIndexes(ResourceName.WIKI_ENG, ResourceName.WIKI_GER);

        final QueryResults<Document> documents = queryTextIndexService.queryTextIndexWithText(getTokenProxy(), "*", params);

        assertThat(documents.getTotalResults(), is(greaterThan(0)));

        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(10));

        final Document document0 = documentList.get(0);

        assertThat(document0.getFields().keySet(), hasSize(greaterThan(0)));
    }

    @Test
    public void testQueryForTextWithIndexUuidAndQueryProfileUuid() throws HodErrorException {
        final ResourceName queryProfileName = new ResourceName(getEndpoint().getDomainName(), UUID.randomUUID().toString());

        final QueryProfileService queryProfileService = new QueryProfileServiceImpl(getConfig());

        queryProfileService.createQueryProfile(
                getTokenProxy(),
                queryProfileName.getName(),
                QUERY_MANIPULATION_INDEX_NAME,
                new QueryProfileRequestBuilder()
        );

        try {
            final ListResourcesRequestBuilder listResourcesRequestBuilder = new ListResourcesRequestBuilder()
                    .setTypes(new HashSet<>(Arrays.asList(ResourceType.TEXT_INDEX, ResourceType.QUERY_PROFILE)));

            final List<ResourceDetails> resources = new ResourcesServiceImpl(getConfig()).list(getTokenProxy(), listResourcesRequestBuilder);

            final ResourceUuid wikiEngUuid = resources.stream()
                    .filter(resourceDetails -> ResourceName.WIKI_ENG.equals(resourceDetails.getResource().getResourceName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Could not determine UUID for wiki_eng"))
                    .getResource().getResourceUuid();

            final ResourceUuid queryProfileUuid = resources.stream()
                    .filter(resourceDetails -> queryProfileName.equals(resourceDetails.getResource().getResourceName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Could not determine UUID for query profile"))
                    .getResource().getResourceUuid();

            final QueryRequestBuilder params = new QueryRequestBuilder()
                    .setMaxPageResults(1)
                    .setAbsoluteMaxResults(1)
                    .setQueryProfile(queryProfileUuid)
                    .addIndexes(wikiEngUuid);

            final QueryResults<Document> documents = queryTextIndexService.queryTextIndexWithText(getTokenProxy(), "*", params);
            assertThat(documents.getDocuments().size(), is(greaterThan(0)));
        } finally {
            queryProfileService.deleteQueryProfile(getTokenProxy(), new ResourceName(getEndpoint().getDomainName(), queryProfileName.getName()));
        }
    }

    @Test
    public void testQueryForFile() throws HodErrorException {
        final File file = new File("src/test/resources/com/hp/autonomy/hod/client/api/textindex/query/queryText.txt");
        final QueryRequestBuilder params = new QueryRequestBuilder()
                .setMaxPageResults(10)
                .setAbsoluteMaxResults(10)
                .addIndexes(ResourceName.WIKI_GER, ResourceName.WIKI_ENG)
                .setSort(Sort.date);

        final QueryResults<Document> documents = queryTextIndexService.queryTextIndexWithFile(getTokenProxy(), file, params);
        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(10));
    }

    @Test
    public void testQueryForFileAsStream() throws HodErrorException, IOException {
        final InputStream stream = getClass().getResourceAsStream("/com/hp/autonomy/hod/client/api/textindex/query/queryText.txt");

        final QueryRequestBuilder params = new QueryRequestBuilder()
                .setMaxPageResults(10)
                .setAbsoluteMaxResults(10)
                .addIndexes(ResourceName.WIKI_GER, ResourceName.WIKI_ENG)
                .setSort(Sort.date);

        final QueryResults<Document> documents = queryTextIndexService.queryTextIndexWithFile(getTokenProxy(), stream, params);
        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(10));
    }

    @Test
    public void testSpellCheckSuggestion() throws HodErrorException {
        final QueryRequestBuilder params = new QueryRequestBuilder()
            .setCheckSpelling(CheckSpelling.suggest)
            .addIndexes(ResourceName.WIKI_ENG);

        final QueryResults<Document> documents = queryTextIndexService.queryTextIndexWithText(getTokenProxy(), "ludwig van beethofen", params);

        assertThat(documents.getSuggestion().getCorrectedQuery(), is("ludwig van Beethoven"));
    }

    @Test
    public void testSpellCheckAutocomplete() throws HodErrorException {
        final QueryRequestBuilder params = new QueryRequestBuilder()
            .setCheckSpelling(CheckSpelling.autocorrect)
            .addIndexes(ResourceName.WIKI_ENG);

        final QueryResults<Document> documents = queryTextIndexService.queryTextIndexWithText(getTokenProxy(), "ludwig van beethofen", params);

        assertThat(documents.getAutoCorrection().getCorrectedQuery(), is("ludwig van Beethoven"));
    }

}
