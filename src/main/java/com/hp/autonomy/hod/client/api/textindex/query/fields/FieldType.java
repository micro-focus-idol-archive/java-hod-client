/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.fields;

import com.hp.autonomy.hod.client.converter.DoNotConvert;
import com.hp.autonomy.types.requests.idol.actions.tags.params.FieldTypeParam;

/**
 * Enum type representing the possible options for the field type parameter (see retrieve index fields api)
 */
@SuppressWarnings({"WeakerAccess", "unused"})
@DoNotConvert
public enum FieldType {
    index,
    parametric,
    numeric,
    autnrank,
    reference,
    date,
    stored;

    public static FieldType fromParam(final FieldTypeParam fieldTypeParam) {
        final FieldType fieldType;
        //noinspection EnumSwitchStatementWhichMissesCases
        switch (fieldTypeParam) {
            case AutnRank:
                fieldType = autnrank;
                break;
            case Index:
                fieldType = index;
                break;
            case NumericDate:
                fieldType = date;
                break;
            case Numeric:
                fieldType = numeric;
                break;
            case Parametric:
                fieldType = parametric;
                break;
            case Reference:
                fieldType = reference;
                break;
            default:
                fieldType = stored;
                break;
        }

        return fieldType;
    }
}
