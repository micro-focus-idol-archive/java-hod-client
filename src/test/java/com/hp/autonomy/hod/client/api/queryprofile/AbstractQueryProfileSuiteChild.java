/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.error.HodErrorException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Slf4j
public abstract class AbstractQueryProfileSuiteChild extends AbstractHodClientIntegrationTest {

    private List<ResourceName> createdQueryProfiles;

    // Not under test
    private QueryProfileService queryProfileService;

    public AbstractQueryProfileSuiteChild(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    public void setUp() {
        super.setUp();
        queryProfileService = new QueryProfileServiceImpl(getConfig());
        createdQueryProfiles = new LinkedList<>();
    }

    @After
    public void tearDown() {
        for (final ResourceName profile : createdQueryProfiles) {
            try {
                queryProfileService.deleteQueryProfile(getTokenProxy(), profile);
            } catch (final HodErrorException e) {
                log.error("Error deleting query profile", e);
            }
        }
    }

    // Safely create an empty query profile
    protected ResponseAndIdentifier trackedCreateProfile() throws HodErrorException {
        return trackedCreateProfile(new QueryProfileRequestBuilder());
    }

    // Create a query profile and track it for deletion in tear down
    protected ResponseAndIdentifier trackedCreateProfile(final QueryProfileRequestBuilder parameters) throws HodErrorException {
        final String name = uniqueName();
        final ResourceName profile = new ResourceName(getEndpoint().getDomainName(), name);
        final QueryProfileStatusResponse response = queryProfileService.createQueryProfile(getTokenProxy(), name, QUERY_MANIPULATION_INDEX_NAME, parameters);
        createdQueryProfiles.add(profile);
        return new ResponseAndIdentifier(response, profile);
    }

    // Delete a query profile and remove it from the list of tracked profiles
    protected QueryProfileStatusResponse trackedDeleteProfile(final ResourceName profile) throws HodErrorException {
        if (!createdQueryProfiles.contains(profile)) {
            throw new IllegalStateException("Tried to delete profile which was not created with trackedCreateProfile: " + profile);
        }

        final QueryProfileStatusResponse response = queryProfileService.deleteQueryProfile(getTokenProxy(), profile);
        createdQueryProfiles.remove(profile);
        return response;
    }

    protected String uniqueName() {
        return "java-client-" + UUID.randomUUID().toString();
    }

    @Data
    protected static class ResponseAndIdentifier {
        private final QueryProfileStatusResponse response;
        private final ResourceName profile;
    }
}
