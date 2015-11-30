/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.content;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.textindex.query.search.Document;
import com.hp.autonomy.hod.client.api.textindex.query.search.Print;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.types.requests.Documents;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class GetContentServiceITCase extends AbstractHodClientIntegrationTest {

    private GetContentService<Document> getContentService;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        getContentService = GetContentServiceImpl.documentsService(getConfig());
    }

    public GetContentServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testGetContentWithReference() throws HodErrorException {
        final GetContentRequestBuilder params = new GetContentRequestBuilder()
            .setPrint(Print.all);

        final Documents<Document> documents = getContentService.getContent(
            getTokenProxy(),
            Collections.singletonList("f6eef7b0-eb5c-4458-a22d-faadb4785539"),
            getPrivateIndex(),
            params
        );

        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(1));

        final Document document0 = documentList.get(0);

        assertThat(document0.getTitle(), is("DOCUMENT FOR GET CONTENT"));
    }
}
