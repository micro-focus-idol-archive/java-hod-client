package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.AbstractIodClientIntegrationTest;
import com.hp.autonomy.iod.client.error.IodErrorException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GetContentServiceITCase extends AbstractIodClientIntegrationTest {

    private GetContentService getContentService;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        getContentService = getRestAdapter().create(GetContentService.class);
    }

    @Test
    public void testGetContentWithReference() throws IodErrorException {
        final Map<String, Object> params = new GetContentRequestBuilder()
                .build();

        final Documents documents = getContentService.getContent(getApiKey(),
                Arrays.asList("3ac70cc2-606e-486a-97d0-511e762b2183"), getIndex(),
                params);

        final List<Document> documentList = documents.getDocuments();

        assertThat(documentList, hasSize(1));

        final Document document0 = documentList.get(0);

        assertThat(document0.getTitle(), is("TEST DOCUMENT"));
    }
}
