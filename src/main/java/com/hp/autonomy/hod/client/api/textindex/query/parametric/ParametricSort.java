/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.parametric;

import com.hp.autonomy.types.requests.idol.actions.tags.params.SortParam;

/**
 * Values for the GetParametricValues API sort parameter
 */
@SuppressWarnings("WeakerAccess")
public enum ParametricSort {
    document_count,
    reverse_document_count,
    alphabetical,
    reverse_alphabetical,
    number_increasing,
    number_decreasing,
    date,
    reverse_date,
    off;

    @SuppressWarnings("unused")
    public static ParametricSort fromParam(final SortParam param) {
        ParametricSort sort = off;
        switch (param) {
            case Alphabetical:
                sort = alphabetical;
                break;
            case Date:
                sort = date;
                break;
            case DocumentCount:
                sort = document_count;
                break;
            case NumberDecreasing:
                sort = number_decreasing;
                break;
            case NumberIncreasing:
                sort = number_increasing;
                break;
            case ReverseAlphabetical:
                sort = reverse_alphabetical;
                break;
            case ReverseDate:
                sort = reverse_date;
                break;
            case ReverseDocumentCount:
                sort = reverse_document_count;
                break;
            case Off:
                sort = off;
                break;
        }

        return sort;
    }
}
