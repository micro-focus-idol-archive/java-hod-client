/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.converter.DoNotConvert;

/**
 * Enum type representing the possible options for the print parameter
 */
@DoNotConvert
public enum Print {
    all,
    all_sections,
    date,
    fields,
    none,
    no_results,
    parametric,
    reference
}
