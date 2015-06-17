/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.job;

import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

public interface JobService<T extends JobStatus<?>> {

    T getJobStatus(JobId jobId) throws HodErrorException;

    T getJobStatus(TokenProxy tokenProxy, JobId jobId) throws HodErrorException;

    T getJobResult(JobId jobId) throws HodErrorException;

    T getJobResult(TokenProxy tokenProxy, JobId jobId) throws HodErrorException;

}
