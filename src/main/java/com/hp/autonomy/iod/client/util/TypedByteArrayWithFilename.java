/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.util;

import retrofit.mime.TypedByteArray;

import java.util.UUID;

public class TypedByteArrayWithFilename extends TypedByteArray {
    public TypedByteArrayWithFilename(final String mimeType, final byte[] bytes) {
        super(mimeType, bytes);
    }

    @Override
    public String fileName() {
        return UUID.randomUUID().toString();
    }
}
