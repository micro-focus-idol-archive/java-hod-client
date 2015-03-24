package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.converter.DoNotConvert;

/**
 * Enum type representing the possible options for the highlight parameter
 */
@DoNotConvert
public enum Highlight {
    off,
    terms,
    sentences
}
