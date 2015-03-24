package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.AbstractIodClientIntegrationTest;
import com.hp.autonomy.iod.client.error.IodErrorException;
import org.junit.Before;
import org.junit.Test;
import retrofit.mime.TypedFile;

import java.io.File;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FindRelatedConceptsServiceITCase extends AbstractIodClientIntegrationTest {

    private FindRelatedConceptsService findRelatedConceptsService;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        findRelatedConceptsService = getRestAdapter().create(FindRelatedConceptsService.class);
    }

    @Test
    public void testFindForText() throws IodErrorException {
        final Entities entities = findRelatedConceptsService.findRelatedConceptsWithText(getApiKey(), "Hewlett", null);

        final List<Entity> entitiesList = entities.getEntities();

        assertThat(entitiesList.size(), is(greaterThan(0)));

        final Entity entity0 = entitiesList.get(0);

        assertThat(entity0.getOccurrences(), is(greaterThan(0)));
    }

    @Test
    public void testFindForFile() throws IodErrorException {
        final TypedFile file = new TypedFile("text/plain", new File("src/test/resources/com/hp/autonomy/iod/client/api/search/queryText.txt"));

        final Entities entities = findRelatedConceptsService.findRelatedConceptsWithFile(getApiKey(), file, null);

        final List<Entity> entitiesList = entities.getEntities();

        assertThat(entitiesList.size(), is(greaterThan(0)));

        final Entity entity0 = entitiesList.get(0);

        assertThat(entity0.getOccurrences(), is(greaterThan(0)));
    }

}
