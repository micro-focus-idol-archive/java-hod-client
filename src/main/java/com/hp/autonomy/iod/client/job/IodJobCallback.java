/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.iod.client.job;

import com.hp.autonomy.iod.client.error.IodErrorCode;

public interface IodJobCallback<T> {

    void success(final T result);

    void error(final IodErrorCode error);

    void handleException(final RuntimeException exception);

}
