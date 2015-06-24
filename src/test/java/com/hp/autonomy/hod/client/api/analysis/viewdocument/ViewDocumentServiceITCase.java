/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.analysis.viewdocument;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

@RunWith(Parameterized.class)
public class ViewDocumentServiceITCase extends AbstractHodClientIntegrationTest {

    private ViewDocumentService viewDocumentService;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        viewDocumentService = new ViewDocumentServiceImpl(getConfig());
    }

    public ViewDocumentServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testViewFile() throws HodErrorException, IOException {
        final File file = new File("src/test/resources/com/hp/autonomy/hod/client/api/formatconversion/test-file.txt");

        final ViewDocumentRequestBuilder params = new ViewDocumentRequestBuilder()
                .addHighlightExpressions("ventilation")
                .addStartTags("<highlight>");

        try(final InputStream inputStream = viewDocumentService.viewFile(getTokenProxy(), file, params)) {
            final String html = IOUtils.toString(inputStream, "UTF-8");

            assertThat(html, containsString("telephone"));
            assertThat(html, containsString("<highlight>ventilation</highlight>"));
        }
    }

    @Test
    public void testViewFileAsHtmlString() throws HodErrorException {
        final File file = new File("src/test/resources/com/hp/autonomy/hod/client/api/formatconversion/test-file.txt");

        final ViewDocumentRequestBuilder params = new ViewDocumentRequestBuilder()
                .addHighlightExpressions("ventilation")
                .addStartTags("<highlight>");

        final String html = viewDocumentService.viewFileAsHtmlString(getTokenProxy(), file, params);

        assertThat(html, containsString("telephone"));
        assertThat(html, containsString("<highlight>ventilation</highlight>"));
    }
}