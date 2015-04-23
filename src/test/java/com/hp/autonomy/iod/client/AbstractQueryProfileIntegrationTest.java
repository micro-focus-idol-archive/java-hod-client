/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */
package com.hp.autonomy.iod.client;

import com.hp.autonomy.iod.client.api.search.*;
import com.hp.autonomy.iod.client.error.IodErrorException;
import org.junit.After;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper for testing query profile functionality.
 */
public abstract class AbstractQueryProfileIntegrationTest extends AbstractIodClientIntegrationTest {

    private List<QueryProfile> qpTestResources;
    // Not under test
    private CreateQueryProfileService createQueryProfileService;
    private DeleteQueryProfileService deleteQueryProfileService;

    /**
     * Make sure you override this and call super(endpoint);
     *
     * @param endpoint
     */
    public AbstractQueryProfileIntegrationTest(Endpoint endpoint) {
        super(endpoint);
    }


    public void setUp() {
        super.setUp();
        createQueryProfileService = getRestAdapter().create(CreateQueryProfileService.class);
        deleteQueryProfileService = getRestAdapter().create(DeleteQueryProfileService.class);
        qpTestResources = new ArrayList<>();
    }

    /**
     * Deletes all created query profiles at the end of the test.
     */
    @After
    public void tearDown() {
        for (QueryProfile qp : qpTestResources) {
            try {
                // Try/catch as QueryProfiles might not have been created
                deleteQueryProfileService.deleteQueryProfile(endpoint.getApiKey(), qp.getName());
            } catch (IodErrorException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a QueryProfile object for using in tests.  Note that the name of the QueryProfile will not be the same as
     * the nameSuffix that is passed in.
     * @param nameSuffix  A unique suffix to append to the name of the Query Profile.
     * @return  A QueryProfile object, with name and config.
     */
    private QueryProfile createTestQueryProfile(final String nameSuffix) {
        final String profileName = "iod_java_client_query_profile_test_" + nameSuffix;

        final List<String> categories = new ArrayList<>();
        categories.add("Promotions");

        final QueryProfilePromotions promotions = new QueryProfilePromotions.Builder()
                .setEnabled(true)
                .setEveryPage(false)
                .setIdentified(false)
                .setCategories(categories)
                .build();

        final QueryProfileConfig config = new QueryProfileConfig.Builder()
                .setQueryManipulationIndex(getQueryManipulationIndex())
                .setPromotions(promotions)
                .build();

        final QueryProfile qp = new QueryProfile.Builder()
                .setName(profileName)
                .setConfig(config)
                .build();

        qpTestResources.add(qp);

        return qp;
    }

    /**
     * Creates a Query Profile in IDOL OnDemand.  Note that the nameSuffix passed in will not be the same as the
     * name IDOL OnDemand uses.  See the response object for that.
     * @param nameSuffix  Suffix to append to the query profile name.
     * @return  A QueryProfile with the name and config used.
     * @throws IodErrorException
     */
    protected QueryProfile createQueryProfile(final String nameSuffix) throws IodErrorException {
        final QueryProfile qp = createTestQueryProfile(nameSuffix);
        createQueryProfileService.createQueryProfile(endpoint.getApiKey(), qp.getName(), qp.getConfig());

        return qp;
    }
}
