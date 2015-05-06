/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textanalysis;

import com.hp.autonomy.hod.client.converter.DoNotConvert;

/**
 * Enum representing possible languages to use for sentiment analysis
 */
@DoNotConvert
public enum SentimentAnalysisLanguage {
    /**
     * English
     */
    eng,

    /**
     * French
     */
    fre,

    /**
     * Spanish
     */
    spa,

    /**
     * German
     */
    ger,

    /**
     * Italian
     */
    ita,

    /**
     * Chinese
     */
    chi,

    /**
     * Portuguese
     */
    por,

    /**
     * Dutch
     */
    dut,

    /**
     * Russian
     */
    rus,

    /**
     * Czech
     */
    cze,

    /**
     * Turkish
     */
    tur
}
