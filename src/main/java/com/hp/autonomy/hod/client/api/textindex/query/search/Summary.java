/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.hp.autonomy.hod.client.converter.DoNotConvert;

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
