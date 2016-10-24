/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.userstore.user;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CreateUserRequestBuilderTest {
    private static final String USER_MESSAGE_KEY = "user_message";
    private static final String METADATA_KEY = "metadata";

    @Test
    public void buildWithNoParameters() {
        final Map<String, Object> output = new CreateUserRequestBuilder().build();

        for (final Map.Entry<String, Object> entry : output.entrySet()) {
            assertThat(entry.getValue(), is(nullValue()));
        }
    }

    @Test
    public void buildWithNoMetadata() {
        final String userMessage = "This is a message for you!";

        final Map<String, Object> output = new CreateUserRequestBuilder()
            .setUserMessage(userMessage)
            .build();

        boolean foundUserMessage = false;

        // We cannot call output.get() to inspect individual keys since build() produces a MultiMap
        for (final Map.Entry<String, Object> entry : output.entrySet()) {
            if (entry.getKey().equals(USER_MESSAGE_KEY)) {
                assertThat((String) entry.getValue(), is(userMessage));
                foundUserMessage = true;
            } else {
                assertThat(entry.getValue(), is(nullValue()));
            }
        }

        assertTrue("User message was not in the output", foundUserMessage);
    }

    @Test
    public void buildWithMetadata() {
        final String userMessage = "This is a message for you!";

        final Map<String, Object> metadata = new HashMap<>();
        metadata.put("name", "fred");
        metadata.put("age", 24);

        final Map<String, Object> output = new CreateUserRequestBuilder()
            .setUserMessage(userMessage)
            .setMetadata(metadata)
            .build();

        boolean foundUserMessage = false;
        boolean foundMetadata = false;

        // We cannot call output.get() to inspect individual keys since build() produces a MultiMap
        for (final Map.Entry<String, Object> entry : output.entrySet()) {
            final String key = entry.getKey();

            switch (key) {
                case USER_MESSAGE_KEY:
                    assertThat(entry.getValue(), is(userMessage));
                    foundUserMessage = true;
                    break;
                case METADATA_KEY:
                    //noinspection unchecked
                    assertThat((List<Metadata<?>>) entry.getValue(), Matchers.<Object>containsInAnyOrder(
                            new Metadata<>("name", "fred"),
                            new Metadata<>("age", 24)
                    ));

                    foundMetadata = true;
                    break;
                default:
                    assertThat(entry.getValue(), is(nullValue()));
                    break;
            }
        }

        assertTrue("User message was not in the output", foundUserMessage);
        assertTrue("Metadata was not in the output", foundMetadata);
    }
}
