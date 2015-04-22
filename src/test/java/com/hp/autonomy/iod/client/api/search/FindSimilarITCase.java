/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.AbstractIodClientIntegrationTest;
import com.hp.autonomy.iod.client.Endpoint;
import com.hp.autonomy.iod.client.api.textindexing.AddToTextIndexJobService;
import com.hp.autonomy.iod.client.api.textindexing.AddToTextIndexRequestBuilder;
import com.hp.autonomy.iod.client.api.textindexing.AddToTextIndexResponse;
import com.hp.autonomy.iod.client.api.textindexing.AddToTextIndexService;
import com.hp.autonomy.iod.client.error.IodErrorException;
import com.hp.autonomy.iod.client.util.TestCallback;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import retrofit.RestAdapter;
import retrofit.mime.TypedFile;

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
public class FindSimilarITCase extends AbstractIodClientIntegrationTest {

    private FindSimilarService findSimilarService;
    private AddToTextIndexJobService addToTextIndexService;

    public FindSimilarITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Before
    public void setUp() {
        super.setUp();

        final RestAdapter restAdapter = getRestAdapter();
        findSimilarService = restAdapter.create(FindSimilarService.class);
        addToTextIndexService = new AddToTextIndexJobService(restAdapter.create(AddToTextIndexService.class));
    }

    @Test
    public void testFindSimilarWithText() throws IodErrorException {
        final Map<String, Object> params = new QueryRequestBuilder()
                .addIndexes("wiki_eng")
                .build();

        final Documents documents = findSimilarService.findSimilarDocumentsToText(endpoint.getApiKey(), "cats", params);

        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(6));
    }

    @Test
    public void testFindSimilarWithFile() throws IodErrorException {
        final TypedFile file = new TypedFile("text/plain", new File("src/test/resources/com/hp/autonomy/iod/client/api/formatconversion/test-file.txt"));
        final Map<String, Object> params = new QueryRequestBuilder()
                .addIndexes("wiki_eng")
                .build();

        final Documents documents = findSimilarService.findSimilarDocumentsToFile(endpoint.getApiKey(), file, params);

        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(6));
    }

    @Test
    public void testFindSimilarWithIndexReference() throws IodErrorException, InterruptedException {
        final com.hp.autonomy.iod.client.api.textindexing.Document document1 = new com.hp.autonomy.iod.client.api.textindexing.Document.Builder()
                .setReference("65cf2d9e-ac37-4caf-9fdc-0dc918b532af")
                .setTitle("Find Similar Index Reference Doc 1")
                .setContent("The cat sat on the mat")
                .build();

        final com.hp.autonomy.iod.client.api.textindexing.Document document2 = new com.hp.autonomy.iod.client.api.textindexing.Document.Builder()
                .setReference("b1c73047-fdd8-4644-a3d9-a447a2e85f73")
                .setTitle("Find Similar Index Reference Doc 2")
                .setContent("The cat sat on the hat")
                .build();

        final com.hp.autonomy.iod.client.api.textindexing.Documents<com.hp.autonomy.iod.client.api.textindexing.Document> documents
                = new com.hp.autonomy.iod.client.api.textindexing.Documents<>(document1, document2);

        final AtomicReference<Documents> result = new AtomicReference<>();

        final CountDownLatch latch = new CountDownLatch(1);

        final Map<String, Object> params = new AddToTextIndexRequestBuilder()
                .setDuplicateMode(AddToTextIndexRequestBuilder.DuplicateMode.replace)
                .build();

        addToTextIndexService.addJsonToTextIndex(endpoint.getApiKey(), documents, getIndex(), params, new TestCallback<AddToTextIndexResponse>(latch) {
            @Override
            public void success(final AddToTextIndexResponse response) {
                try {
                    final Map<String, Object> params = new QueryRequestBuilder()
                            .addIndexes(getIndex())
                            .build();

                    final Documents similarDocuments = findSimilarService.findSimilarDocumentsToIndexReference(endpoint.getApiKey(), "65cf2d9e-ac37-4caf-9fdc-0dc918b532af", params);

                    result.set(similarDocuments);
                } catch (final IodErrorException e) {
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
