/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.HodErrorTester;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.error.HodErrorCode;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.HashSet;
import java.util.Set;

import static com.hp.autonomy.hod.client.HodErrorTester.testErrorCode;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class CreateDeleteQueryProfileServiceSuiteChild extends AbstractQueryProfileSuiteChild {
    private QueryProfileService service;

    public CreateDeleteQueryProfileServiceSuiteChild(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        service = new QueryProfileServiceImpl(getConfig());
    }

    @Test
    public void create() throws HodErrorException {
        final QueryProfileRequestBuilder builder = new QueryProfileRequestBuilder()
            .setPromotionsEnabled(true)
            .setPromotionsIdentified(false)
            .addPromotionCategories("Promotions");

        final ResponseAndIdentifier responseAndIdentifier = trackedCreateProfile(builder);
        assertThat(responseAndIdentifier.getResponse().getMessage(), not(nullValue()));
        assertThat(responseAndIdentifier.getResponse().getQueryProfile(), is(responseAndIdentifier.getProfile().getName()));
    }

    @Test
    public void createDuplicateFails() throws HodErrorException {
        final ResponseAndIdentifier responseAndIdentifier = trackedCreateProfile();

        testErrorCode(HodErrorCode.QUERY_PROFILE_NAME_INVALID, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.createQueryProfile(getTokenProxy(), responseAndIdentifier.getProfile().getName(), QUERY_MANIPULATION_INDEX_NAME, new QueryProfileRequestBuilder());
            }
        });
    }

    @Test
    public void createWithNonExistentIndexFails() {
        testErrorCode(HodErrorCode.INDEX_NAME_INVALID, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.createQueryProfile(getTokenProxy(), uniqueName(), uniqueName(), new QueryProfileRequestBuilder());
            }
        });
    }

    @Test
    public void createAndDelete() throws HodErrorException {
        final ResponseAndIdentifier responseAndIdentifier = trackedCreateProfile();
        final QueryProfileStatusResponse response = trackedDeleteProfile(responseAndIdentifier.getProfile());
        assertThat(response.getQueryProfile(), is(responseAndIdentifier.getProfile().getName()));
    }

    @Test
    public void deleteNonExistentFails() {
        final Set<HodErrorCode> errorCodes = new HashSet<>();
        errorCodes.add(HodErrorCode.QUERY_PROFILE_NAME_INVALID);
        errorCodes.add(HodErrorCode.INSUFFICIENT_PRIVILEGES);

        testErrorCode(errorCodes, new HodErrorTester.HodExceptionRunnable() {
            @Override
            public void run() throws HodErrorException {
                service.deleteQueryProfile(getTokenProxy(), new ResourceIdentifier(getEndpoint().getDomainName(), uniqueName()));
            }
        });
    }
}
