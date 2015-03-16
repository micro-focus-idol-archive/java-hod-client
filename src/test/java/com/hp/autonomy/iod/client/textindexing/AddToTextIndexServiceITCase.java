/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.textindexing;

import com.hp.autonomy.iod.client.AbstractIodClientIntegrationTest;
import com.hp.autonomy.iod.client.async.JobId;
import com.hp.autonomy.iod.client.error.IodErrorException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import retrofit.mime.TypedFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@Slf4j
public class AddToTextIndexServiceITCase extends AbstractIodClientIntegrationTest {

    private static final String REFERENCE = "3ac70cc2-606e-486a-97d0-511e762b2183";

    private AddToTextIndexService addToTextIndexService;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        addToTextIndexService = getRestAdapter().create(AddToTextIndexService.class);
    }

    @Test
    public void testAddJsonToTextIndex() throws IodErrorException {
        final Document document = new Document.Builder()
                .setReference(REFERENCE)
                .setTitle("TEST DOCUMENT")
                .setContent("I heartily endorse this event or product")
                .addField("coolstuff", "This is so cool!")
                .build();

        final Map<String, Object> params = new AddToTextIndexRequestBuilder()
                .setDuplicateMode(AddToTextIndexRequestBuilder.DuplicateMode.replace)
                .build();

        final JobId jobId = addToTextIndexService.addJsonToTextIndex(getApiKey(), new Documents<>(document), getIndex(), params);

        log.debug("Job ID was {}", jobId.getJobId());

        assertThat(jobId.getJobId(), is(notNullValue()));
    }

    @Test
    public void testAddFileToTextIndex() throws IodErrorException {
        final TypedFile file = new TypedFile("text/plain", new File("src/test/resources/com/hp/autonomy/iod/client/textindexing/the-end.txt"));

        final Map<String, Object> additionalMetadata = new HashMap<>();
        additionalMetadata.put("reference", "63edb67f-c930-4b7b-8c33-2cd28e5cc670");
        additionalMetadata.put("title", "The End");

        final Map<String, Object> params = new AddToTextIndexRequestBuilder()
                .setDuplicateMode(AddToTextIndexRequestBuilder.DuplicateMode.replace)
                .addAdditionalMetadata(additionalMetadata)
                .build();

        final JobId jobId = addToTextIndexService.addFileToTextIndex(getApiKey(), file, getIndex(), params);

        log.debug("Job ID was {}", jobId.getJobId());

        assertThat(jobId.getJobId(), is(notNullValue()));
    }

}
