/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.job;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import retrofit.client.Response;

/*
 * $Id:$
 *
 * Copyright (c) 2015, Autonomy Systems Ltd.
 *
 * Last modified by $Author:$ on $Date:$
 */
public class JobServiceImpl<T extends JobStatus<?>> implements JobService<T> {

    private final Class<? extends T> returnType;
    private final JobBackend jobBackend;
    private final Requester requester;

    public JobServiceImpl(final HodServiceConfig hodServiceConfig, final Class<? extends T> returnType) {
        this.returnType = returnType;

        jobBackend = hodServiceConfig.getRestAdapter().create(JobBackend.class);
        requester = hodServiceConfig.getRequester();
    }

    @Override
    public T getJobStatus(final JobId jobId) throws HodErrorException {
        return requester.makeRequest(returnType, getStatusBackendCaller(jobId));
    }

    @Override
    public T getJobStatus(final TokenProxy tokenProxy, final JobId jobId) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, getStatusBackendCaller(jobId));
    }

    @Override
    public T getJobResult(final JobId jobId) throws HodErrorException {
        return requester.makeRequest(returnType, getResultBackendCaller(jobId));
    }

    @Override
    public T getJobResult(final TokenProxy tokenProxy, final JobId jobId) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, getResultBackendCaller(jobId));
    }

    private Requester.BackendCaller getStatusBackendCaller(final JobId jobId) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return jobBackend.getJobStatus(authenticationToken, jobId);
            }
        };
    }

    private Requester.BackendCaller getResultBackendCaller(final JobId jobId) {
        return new Requester.BackendCaller() {
            @Override
            public Response makeRequest(final AuthenticationToken authenticationToken) throws HodErrorException {
                return jobBackend.getJobResult(authenticationToken, jobId);
            }
        };
    }
}
