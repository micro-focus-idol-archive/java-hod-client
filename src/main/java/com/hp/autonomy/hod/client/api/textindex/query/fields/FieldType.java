/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.fields;

import com.hp.autonomy.hod.client.converter.DoNotConvert;

/**
 * Enum type representing the possible options for the fieldtype parameter (see retrieve index fields api)
 */
@DoNotConvert
public enum FieldType {
    index,
    parametric,
    numeric,
    autnrank,
    reference,
    date,
    stored
}
