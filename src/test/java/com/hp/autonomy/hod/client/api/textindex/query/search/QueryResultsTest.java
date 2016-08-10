package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.autonomy.hod.client.warning.HodWarning;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

public class QueryResultsTest {

    @Test
    public void testReadingInResponse() throws IOException {
        final InputStream jsonStream = getClass().getResourceAsStream("/com/hp/autonomy/hod/client/queryResults/query-results.json");

        ObjectMapper mapper = new ObjectMapper();

        JavaType returnType = mapper.getTypeFactory().constructParametrizedType(QueryResults.class, QueryResults.class, Document.class);
        QueryResults results = mapper.readValue(jsonStream, returnType);

        assertThat(results.getDocuments().size(), is(2));
        assertThat(results.getTotalResults(), is(2));
        assertEquals(results.getExpandedQuery(), "Longer string than the original query with + lots + of + things + added");
    }

    @Test
    public void testReadingInResponseWithWarnings() throws IOException {
        final InputStream jsonStream = getClass().getResourceAsStream("/com/hp/autonomy/hod/client/queryResults/query-results-with-warnings.json");

        ObjectMapper mapper = new ObjectMapper();

        JavaType returnType = mapper.getTypeFactory().constructParametrizedType(QueryResults.class, QueryResults.class, Document.class);
        QueryResults results = mapper.readValue(jsonStream, returnType);

        assertThat(results.getDocuments().size(), is(2));

        HodWarning warning = new HodWarning.Builder()
                .setDetails("{\"reason\":\"Document for reference 161-23/12/2015-11:00 and index tvguide2 is missing.\",}")
                .setCode(40003)
                .build();

        assertThat(results.getHodWarnings().size(), is(1));
        assertEquals(results.getHodWarnings().get(0), warning);

    }
}
