/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.textindexing;

import com.hp.autonomy.iod.client.AbstractIodClientIntegrationTest;
import com.hp.autonomy.iod.client.Endpoint;
import com.hp.autonomy.iod.client.error.IodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class CreateAndDeleteTextIndexITCase extends AbstractIodClientIntegrationTest {

    private CreateTextIndexService createTextIndexService;
    private DeleteTextIndexService deleteTextIndexService;
    private Endpoint endpoint;

    @Before
    public void setUp() {
        super.setUp(endpoint);

        createTextIndexService = getRestAdapter().create(CreateTextIndexService.class);
        deleteTextIndexService = getRestAdapter().create(DeleteTextIndexService.class);
    }

    public CreateAndDeleteTextIndexITCase(final Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Test
    public void testCreateAndDeleteTextIndex() throws IodErrorException {
        final Map<String, Object> createParams = new CreateTextIndexRequestBuilder()
                .setDescription("A text index")
                .build();

        final CreateTextIndexResponse testResponse = createTextIndexService.createTextIndex(
                endpoint.getApiKey(),
                "ice cream",
                "standard",
                createParams);

        assertThat(testResponse.getIndex(), is("ice cream"));

        final DeleteTextIndexResponse queryResponse = deleteTextIndexService.deleteTextIndex(
                endpoint.getApiKey(),
                "ice cream",
                null);

        final Map<String, Object> deleteParams = new DeleteTextIndexRequestBuilder()
                .setConfirm(queryResponse.getConfirm())
                .build();

        final DeleteTextIndexResponse confirmResponse = deleteTextIndexService.deleteTextIndex(
                endpoint.getApiKey(),
                "ice cream",
                deleteParams);

        assertThat(confirmResponse.getIndex(), is("ice cream"));
    }
}
