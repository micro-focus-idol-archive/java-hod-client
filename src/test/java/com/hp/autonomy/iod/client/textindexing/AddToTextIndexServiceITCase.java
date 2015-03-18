/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.textindexing;

import com.hp.autonomy.iod.client.AbstractIodClientIntegrationTest;
import com.hp.autonomy.iod.client.error.IodErrorCode;
import com.hp.autonomy.iod.client.error.IodErrorException;
import com.hp.autonomy.iod.client.job.IodJobCallback;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import retrofit.mime.TypedFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@Slf4j
public class AddToTextIndexServiceITCase extends AbstractIodClientIntegrationTest {

    private static final String REFERENCE = "3ac70cc2-606e-486a-97d0-511e762b2183";

    private AddToTextIndexServiceImpl addToTextIndexService;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        addToTextIndexService = new AddToTextIndexServiceImpl(getRestAdapter().create(AddToTextIndexService.class));
    }

    @After
    public void tearDown() {
        addToTextIndexService.destroy();
    }

    @Test
    public void testAddJsonToTextIndex() throws IodErrorException, InterruptedException {
        final Document document = new Document.Builder()
                .setReference(REFERENCE)
                .setTitle("TEST DOCUMENT")
                .setContent("I heartily endorse this event or product")
                .addField("coolstuff", "This is so cool!")
                .build();

        final Map<String, Object> params = new AddToTextIndexRequestBuilder()
                .setDuplicateMode(AddToTextIndexRequestBuilder.DuplicateMode.replace)
                .build();

        final CountDownLatch latch = new CountDownLatch(1);
        final TestCallback callback = new TestCallback(latch);

        addToTextIndexService.addJsonToTextIndex(getApiKey(), new Documents<>(document), getIndex(), params, callback);

        latch.await();

        final AddToTextIndexResponse result = callback.result;

        assertThat(result, is(notNullValue()));
        assertThat(result.getIndex(), is(getIndex()));
        assertThat(result.getReferences(), hasSize(1));

        final AddToTextIndexReference referenceObject = result.getReferences().get(0);

        assertThat(referenceObject.getReference(), is(REFERENCE));
    }

    @Test
    public void testAddFileToTextIndex() throws IodErrorException, InterruptedException {
        final TypedFile file = new TypedFile("text/plain", new File("src/test/resources/com/hp/autonomy/iod/client/textindexing/the-end.txt"));
        final String reference = "63edb67f-c930-4b7b-8c33-2cd28e5cc670";

        final Map<String, Object> additionalMetadata = new HashMap<>();
        additionalMetadata.put("reference", reference);
        additionalMetadata.put("title", "The End");

        final Map<String, Object> params = new AddToTextIndexRequestBuilder()
                .setDuplicateMode(AddToTextIndexRequestBuilder.DuplicateMode.replace)
                .addAdditionalMetadata(additionalMetadata)
                .build();

        final CountDownLatch latch = new CountDownLatch(1);
        final TestCallback callback = new TestCallback(latch);

        addToTextIndexService.addFileToTextIndex(getApiKey(), file, getIndex(), params, callback);

        latch.await();

        final AddToTextIndexResponse result = callback.result;

        assertThat(result, is(notNullValue()));
        assertThat(result.getIndex(), is(getIndex()));
        assertThat(result.getReferences(), hasSize(1));

        final AddToTextIndexReference referenceObject = result.getReferences().get(0);

        assertThat(referenceObject.getReference(), is(reference));
    }

    private static class TestCallback implements IodJobCallback<AddToTextIndexResponse> {

        private final CountDownLatch latch;

        private volatile AddToTextIndexResponse result;

        public TestCallback(final CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void success(final AddToTextIndexResponse result) {
            log.debug("Result from IDOL OnDemand: {}", result);

            this.result = result;

            latch.countDown();
        }

        @Override
        public void error(final IodErrorCode error) {
            log.error("Error code " + error + " returned from IDOL OnDemand");

            latch.countDown();
        }

        @Override
        public void handleException(final RuntimeException exception) {
            log.error("Runtime exception thrown", exception);

            latch.countDown();
        }
    }

}
