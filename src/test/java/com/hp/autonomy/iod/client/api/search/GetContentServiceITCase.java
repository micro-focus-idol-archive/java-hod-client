/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.AbstractIodClientIntegrationTest;
import com.hp.autonomy.iod.client.Endpoint;
import com.hp.autonomy.iod.client.error.IodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class GetContentServiceITCase extends AbstractIodClientIntegrationTest {

    private GetContentService getContentService;

    @Before
    public void setUp() {
        super.setUp();

        getContentService = getRestAdapter().create(GetContentService.class);
    }

    public GetContentServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testGetContentWithReference() throws IodErrorException {
        final Map<String, Object> params = new GetContentRequestBuilder()
                .setPrint(Print.all)
                .build();

        final Documents documents = getContentService.getContent(endpoint.getApiKey(),
                Arrays.asList("3ac70cc2-606e-486a-97d0-511e762b2183"), getIndex(),
                params);

        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(1));

        final Document document0 = documentList.get(0);

        assertThat(document0.getTitle(), is("TEST DOCUMENT"));
    }
}
