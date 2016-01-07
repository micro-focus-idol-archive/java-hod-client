/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client;

import com.hp.autonomy.hod.client.error.HodErrorCode;
import com.hp.autonomy.hod.client.error.HodErrorException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.EnumSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Utility functions for testing properties of thrown HodErrorExceptions.
 */
@NoArgsConstructor(access = AccessLevel.NONE)
public class HodErrorTester {
    /**
     * Checks that the HodExceptionRunnable throws a HodErrorException with the given HodErrorCode.
     * @param expectedCode The expected HodErrorCode
     * @param runnable Encapsulates the method(s) under test
     */
    public static void testErrorCode(final HodErrorCode expectedCode, final HodExceptionRunnable runnable) {
        testErrorCode(EnumSet.of(expectedCode), runnable);
    }

    /**
     * Checks that the HodExceptionRunnable throws a HodErrorException with one of the given HodErrorCodes.
     * @param expectedCodes The expected HodErrorCodes
     * @param runnable Encapsulates the method(s) under test
     */
    public static void testErrorCode(final Set<HodErrorCode> expectedCodes, final HodExceptionRunnable runnable) {
        final HodErrorException exception = getException(runnable);
        assertThat(expectedCodes, hasItem(exception.getErrorCode()));
    }

    /**
     * Checks that the HodExceptionRunnable throws a HodErrorException with the given HodErrorCode and the given message.
     * @param expectedCode The expected HodErrorCode
     * @param expectedMessage The expected message
     * @param runnable Encapsulates the method(s) under test
     */
    public static void testErrorCodeAndMessage(final HodErrorCode expectedCode, final String expectedMessage, final HodExceptionRunnable runnable) {
        final HodErrorException exception = getException(runnable);
        assertThat(exception.getErrorCode(), is(expectedCode));
        assertThat(exception.getMessage(), is(expectedMessage));
    }

    private static HodErrorException getException(final HodExceptionRunnable runnable) {
        try {
            runnable.run();
        } catch (final HodErrorException e) {
            return e;
        }

        throw new AssertionError("No HodErrorException was thrown");
    }

    /**
     * Interface to encapsulate method calls which might throw a HodErrorException.
     */
    public interface HodExceptionRunnable {
        void run() throws HodErrorException;
    }
}
