package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.converter.DoNotConvert;

/**
 * Enum type representing the possible options for the sort parameter
 */
@DoNotConvert
public enum Sort {
    autn_rank,
    date,
    off,
    relevance,
    reverse_date,
    reverse_relevance
}
