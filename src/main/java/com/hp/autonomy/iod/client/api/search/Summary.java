package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.converter.DoNotConvert;

/**
 * Enum type representing the possible options for the summary parameter
 */
@DoNotConvert
public enum Summary {
    context,
    concept,
    quick,
    off
}
