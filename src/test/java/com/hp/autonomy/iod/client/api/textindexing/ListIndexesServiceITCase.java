package com.hp.autonomy.iod.client.api.textindexing;

import com.hp.autonomy.iod.client.AbstractIodClientIntegrationTest;
import com.hp.autonomy.iod.client.Endpoint;
import com.hp.autonomy.iod.client.error.IodErrorException;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import static com.hp.autonomy.iod.client.api.textindexing.ListIndexesServiceITCase.IndexMatcher.hasIndex;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Slf4j
@RunWith(Parameterized.class)
public class ListIndexesServiceITCase extends AbstractIodClientIntegrationTest{

    private ListIndexesService listIndexesService;

    @Before
    public void setUp() {
        super.setUp();

        listIndexesService = getRestAdapter().create(ListIndexesService.class);
    }

    public ListIndexesServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testListIndexes() throws IodErrorException {
        final Map<String, Object> params = new ListIndexesRequestBuilder()
                .setIndexFlavors(EnumSet.of(IndexFlavor.explorer))
                .setIndexTypes(EnumSet.of(IndexType.content))
                .build();

        final Indexes indexes = listIndexesService.listIndexes(endpoint.getApiKey(), params);

        assertThat(indexes.getPublicIndexes(), is(not(empty())));
        assertThat(indexes.getIndexes(), hasIndex(getIndex()));
    }

    static class IndexMatcher extends BaseMatcher<List<Index>> {

        private final String indexName;

        private IndexMatcher(final String indexName) {
            this.indexName = indexName;
        }

        static IndexMatcher hasIndex(final String indexName) {
            return new IndexMatcher(indexName);
        }

        @Override
        public boolean matches(final Object o) {
            if(!(o instanceof List)) {
                return false;
            }

            final List<Index> indexes = (List<Index>) o;

            for(final Index index : indexes) {
                if (indexName.equals(index.getIndex())) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText("A list containing the index with name " + indexName);
        }
    }


}
