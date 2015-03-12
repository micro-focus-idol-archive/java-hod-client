/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.converter;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;
import retrofit.mime.TypedString;

import java.lang.reflect.Type;

public class IodConverter implements Converter {

    private final Converter converter;

    public IodConverter(final Converter converter) {
        this.converter = converter;
    }

    @Override
    public Object fromBody(final TypedInput body, final Type type) throws ConversionException {
        return converter.fromBody(body, type);
    }

    @Override
    public TypedOutput toBody(final Object object) {
        if (object.getClass().isAnnotationPresent(DoNotConvert.class)) {
            return new TypedString(object.toString());
        }
        else {
            return converter.toBody(object);
        }
    }
}
