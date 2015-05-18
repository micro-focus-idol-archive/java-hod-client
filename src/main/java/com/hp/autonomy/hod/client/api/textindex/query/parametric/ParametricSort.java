/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.parametric;

/**
 * Values for the GetParametricValues API sort parameter
 */
public enum ParametricSort {
    document_count,
    alphabetical,
    reverse_alphabetical,
    number_increasing,
    number_decreasing,
    off
}
