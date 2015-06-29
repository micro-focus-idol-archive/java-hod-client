/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.textindex.document.AddToTextIndexPollingService;
import com.hp.autonomy.hod.client.api.textindex.document.AddToTextIndexRequestBuilder;
import com.hp.autonomy.hod.client.api.textindex.document.AddToTextIndexResponse;
import com.hp.autonomy.hod.client.api.textindex.document.AddToTextIndexService;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.util.TestCallback;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import retrofit.RestAdapter;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;

@Slf4j
@RunWith(Parameterized.class)
public class FindSimilarITCase extends AbstractHodClientIntegrationTest {

    private FindSimilarService<Documents> findSimilarService;
    private AddToTextIndexService addToTextIndexService;

    public FindSimilarITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();

        findSimilarService = FindSimilarServiceImpl.documentsService(getConfig());
        addToTextIndexService = new AddToTextIndexPollingService(getConfig());
    }

    @Test
    public void testFindSimilarWithText() throws HodErrorException {
        final QueryRequestBuilder params = new QueryRequestBuilder()
                .addIndexes(WIKI_ENG);

        final Documents documents = findSimilarService.findSimilarDocumentsToText(getTokenProxy(), "cats", params);

        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(6));
    }

    @Test
    public void testFindSimilarWithFile() throws HodErrorException {
        final File file = new File("src/test/resources/com/hp/autonomy/hod/client/api/formatconversion/test-file.txt");
        final QueryRequestBuilder params = new QueryRequestBuilder()
                .addIndexes(WIKI_ENG);

        final Documents documents = findSimilarService.findSimilarDocumentsToFile(getTokenProxy(), file, params);

        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(6));
    }

    @Test
    public void testFindSimilarWithIndexReference() throws HodErrorException, InterruptedException {
        final com.hp.autonomy.hod.client.api.textindex.document.Document document1 = new com.hp.autonomy.hod.client.api.textindex.document.Document.Builder()
                .setReference("65cf2d9e-ac37-4caf-9fdc-0dc918b532af")
                .setTitle("Find Similar Index Reference Doc 1")
                .setContent("The cat sat on the mat")
                .build();

        final com.hp.autonomy.hod.client.api.textindex.document.Document document2 = new com.hp.autonomy.hod.client.api.textindex.document.Document.Builder()
                .setReference("b1c73047-fdd8-4644-a3d9-a447a2e85f73")
                .setTitle("Find Similar Index Reference Doc 2")
                .setContent("The cat sat on the hat")
                .build();

        final com.hp.autonomy.hod.client.api.textindex.document.Documents<com.hp.autonomy.hod.client.api.textindex.document.Document> documents
                = new com.hp.autonomy.hod.client.api.textindex.document.Documents<>(document1, document2);

        final AtomicReference<Documents> result = new AtomicReference<>();

        final CountDownLatch latch = new CountDownLatch(1);

        final AddToTextIndexRequestBuilder params = new AddToTextIndexRequestBuilder()
                .setDuplicateMode(AddToTextIndexRequestBuilder.DuplicateMode.replace);

        addToTextIndexService.addJsonToTextIndex(getTokenProxy(), documents, PRIVATE_INDEX, params, new TestCallback<AddToTextIndexResponse>(latch) {
            @Override
            public void success(final AddToTextIndexResponse response) {
                try {
                    final QueryRequestBuilder params = new QueryRequestBuilder()
                            .addIndexes(PRIVATE_INDEX);

                    final Documents similarDocuments = findSimilarService.findSimilarDocumentsToIndexReference(getTokenProxy(), "65cf2d9e-ac37-4caf-9fdc-0dc918b532af", params);

                    result.set(similarDocuments);
                } catch (final HodErrorException e) {
                    log.error("Error fetching similar documents: ", e);
                }

                latch.countDown();
            }
        });

        latch.await();

        final Documents similarDocuments = result.get();

        assertThat(similarDocuments, is(notNullValue()));

        final List<Document> documentList = similarDocuments.getDocuments();

        assertThat(documentList, hasSize(1));
    }

}
