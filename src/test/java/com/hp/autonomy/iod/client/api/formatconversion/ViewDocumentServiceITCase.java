/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.formatconversion;

import com.hp.autonomy.iod.client.AbstractIodClientIntegrationTest;
import com.hp.autonomy.iod.client.Endpoint;
import com.hp.autonomy.iod.client.error.IodErrorException;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

@RunWith(Parameterized.class)
public class ViewDocumentServiceITCase extends AbstractIodClientIntegrationTest {

    private ViewDocumentService viewDocumentService;

    @Before
    public void setUp() {
        super.setUp();

        viewDocumentService = getRestAdapter().create(ViewDocumentService.class);
    }

    public ViewDocumentServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testViewFile() throws IodErrorException, IOException {
        final File file = new File("src/test/resources/com/hp/autonomy/iod/client/api/formatconversion/test-file.txt");

        final Map<String, Object> params = new ViewDocumentRequestBuilder()
                .addHighlightExpressions("ventilation")
                .addStartTags("<highlight>")
                .build();

        final Response response = viewDocumentService.viewFile(endpoint.getApiKey(), new TypedFile("text/plain", file), params);

        final InputStream inputStream = response.getBody().in();

        final String html = IOUtils.toString(inputStream, "UTF-8");

        inputStream.close();

        assertThat(html, containsString("telephone"));
        assertThat(html, containsString("<highlight>ventilation</highlight>"));
    }

    @Test
    public void testViewFileAsHtmlString() throws IodErrorException {
        final File file = new File("src/test/resources/com/hp/autonomy/iod/client/api/formatconversion/test-file.txt");

        final Map<String, Object> params = new ViewDocumentRequestBuilder()
                .addHighlightExpressions("ventilation")
                .addStartTags("<highlight>")
                .setRawHtml(false)
                .build();

        final ViewDocumentResponse response = viewDocumentService.viewFileAsHtmlString(endpoint.getApiKey(), new TypedFile("text/plain", file), params);

        final String html = response.getDocument();

        assertThat(html, containsString("telephone"));
        assertThat(html, containsString("<highlight>ventilation</highlight>"));
    }
}