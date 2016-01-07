/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.converter;

import com.fasterxml.jackson.core.JsonParseException;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;
import retrofit.mime.TypedString;

import java.lang.reflect.Type;

/**
 * {@link retrofit.converter.Converter} implementation that respects the {@link DoNotConvert}
 * annotation.
 */
public class HodConverter implements Converter {

    private final Converter converter;

    /**
     * Creates a new HodConverter
     * @param converter Delegate converter which will do most of the work
     */
    public HodConverter(final Converter converter) {
        this.converter = converter;
    }

    /**
     * Returns the object returned by calling fromBody on the underlying converter
     */
    @Override
    public Object fromBody(final TypedInput body, final Type type) throws ConversionException {
        try {
            return converter.fromBody(body, type);
        } catch(final ConversionException e) {
            if(e.getCause() instanceof JsonParseException) {
                throw new HodUnavailableException(e.getCause());
            } else {
                throw e;
            }

        }
    }

    /**
     * If object is annotated with {@link DoNotConvert}, returns a String
     * representation of the object. Otherwise returns the object returned by calling toBody on the underlying converter
     */
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
