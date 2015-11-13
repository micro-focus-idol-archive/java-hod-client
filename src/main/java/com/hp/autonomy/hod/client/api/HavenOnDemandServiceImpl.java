/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.JobId;
import com.hp.autonomy.hod.client.token.TokenProxy;
import retrofit.client.Response;

import java.util.Map;

public class HavenOnDemandServiceImpl implements HavenOnDemandService {

    private final HavenOnDemandBackend havenOnDemandBackend;
    private final Requester<?, TokenType.Simple> requester;

    public HavenOnDemandServiceImpl(final HodServiceConfig<?, TokenType.Simple> hodServiceConfig) {
        this.havenOnDemandBackend = hodServiceConfig.getRestAdapter().create(HavenOnDemandBackend.class);
        this.requester = hodServiceConfig.getRequester();
    }

    @Override
    public <T> T get(final TokenProxy<?, TokenType.Simple> tokenProxy, final String api, final int version, final Map<String, Object> params, final Class<T> returnType) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.get(authenticationToken, api, version, params);
            }
        });
    }

    @Override
    public <T> T get(final TokenProxy<?, TokenType.Simple> tokenProxy, final String first, final String second, final int version, final Map<String, Object> params, final Class<T> returnType) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.get(authenticationToken, first, second, version, params);
            }
        });
    }

    @Override
    public <T> T get(final TokenProxy<?, TokenType.Simple> tokenProxy, final String first, final String second, final String third, final int version, final Map<String, Object> params, final Class<T> returnType) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.get(authenticationToken, first, second, third, version, params);
            }
        });
    }

    @Override
    public JobId getAsync(final TokenProxy<?, TokenType.Simple> tokenProxy, final String api, final int version, final Map<String, Object> params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, JobId.class, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.getAsync(authenticationToken, api, version, params);
            }
        });
    }

    @Override
    public JobId getAsync(final TokenProxy<?, TokenType.Simple> tokenProxy, final String first, final String second, final int version, final Map<String, Object> params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, JobId.class, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.getAsync(authenticationToken, first, second, version, params);
            }
        });
    }

    @Override
    public JobId getAsync(final TokenProxy<?, TokenType.Simple> tokenProxy, final String first, final String second, final String third, final int version, final Map<String, Object> params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, JobId.class, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.getAsync(authenticationToken, first, second, third, version, params);
            }
        });
    }

    @Override
    public <T> T post(final TokenProxy<?, TokenType.Simple> tokenProxy, final String api, final int version, final Map<String, Object> params, final Class<T> returnType) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.post(authenticationToken, api, version, params);
            }
        });
    }

    @Override
    public <T> T post(final TokenProxy<?, TokenType.Simple> tokenProxy, final String first, final String second, final int version, final Map<String, Object> params, final Class<T> returnType) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.post(authenticationToken, first, second, version, params);
            }
        });
    }

    @Override
    public <T> T post(final TokenProxy<?, TokenType.Simple> tokenProxy, final String first, final String second, final String third, final int version, final Map<String, Object> params, final Class<T> returnType) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.post(authenticationToken, first, second, third, version, params);
            }
        });
    }

    @Override
    public JobId postAsync(final TokenProxy<?, TokenType.Simple> tokenProxy, final String api, final int version, final Map<String, Object> params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, JobId.class, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.postAsync(authenticationToken, api, version, params);
            }
        });
    }

    @Override
    public JobId postAsync(final TokenProxy<?, TokenType.Simple> tokenProxy, final String first, final String second, final int version, final Map<String, Object> params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, JobId.class, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.postAsync(authenticationToken, first, second, version, params);
            }
        });
    }

    @Override
    public JobId postAsync(final TokenProxy<?, TokenType.Simple> tokenProxy, final String first, final String second, final String third, final int version, final Map<String, Object> params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, JobId.class, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.postAsync(authenticationToken, first, second, third, version, params);
            }
        });
    }

    @Override
    public <T> T put(final TokenProxy<?, TokenType.Simple> tokenProxy, final String api, final int version, final Map<String, Object> params, final Class<T> returnType) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.put(authenticationToken, api, version, params);
            }
        });
    }

    @Override
    public <T> T put(final TokenProxy<?, TokenType.Simple> tokenProxy, final String first, final String second, final int version, final Map<String, Object> params, final Class<T> returnType) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.put(authenticationToken, first, second, version, params);
            }
        });
    }

    @Override
    public <T> T put(final TokenProxy<?, TokenType.Simple> tokenProxy, final String first, final String second, final String third, final int version, final Map<String, Object> params, final Class<T> returnType) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.put(authenticationToken, first, second, third, version, params);
            }
        });
    }

    @Override
    public JobId putAsync(final TokenProxy<?, TokenType.Simple> tokenProxy, final String api, final int version, final Map<String, Object> params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, JobId.class, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.putAsync(authenticationToken, api, version, params);
            }
        });
    }

    @Override
    public JobId putAsync(final TokenProxy<?, TokenType.Simple> tokenProxy, final String first, final String second, final int version, final Map<String, Object> params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, JobId.class, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.putAsync(authenticationToken, first, second, version, params);
            }
        });
    }

    @Override
    public JobId putAsync(final TokenProxy<?, TokenType.Simple> tokenProxy, final String first, final String second, final String third, final int version, final Map<String, Object> params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, JobId.class, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.putAsync(authenticationToken, first, second, third, version, params);
            }
        });
    }

    @Override
    public <T> T delete(final TokenProxy<?, TokenType.Simple> tokenProxy, final String api, final int version, final Map<String, Object> params, final Class<T> returnType) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.delete(authenticationToken, api, version, params);
            }
        });
    }

    @Override
    public <T> T delete(final TokenProxy<?, TokenType.Simple> tokenProxy, final String first, final String second, final int version, final Map<String, Object> params, final Class<T> returnType) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.delete(authenticationToken, first, second, version, params);
            }
        });
    }

    @Override
    public <T> T delete(final TokenProxy<?, TokenType.Simple> tokenProxy, final String first, final String second, final String third, final int version, final Map<String, Object> params, final Class<T> returnType) throws HodErrorException {
        return requester.makeRequest(tokenProxy, returnType, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.delete(authenticationToken, first, second, third, version, params);
            }
        });
    }

    @Override
    public JobId deleteAsync(final TokenProxy<?, TokenType.Simple> tokenProxy, final String api, final int version, final Map<String, Object> params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, JobId.class, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.deleteAsync(authenticationToken, api, version, params);
            }
        });
    }

    @Override
    public JobId deleteAsync(final TokenProxy<?, TokenType.Simple> tokenProxy, final String first, final String second, final int version, final Map<String, Object> params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, JobId.class, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.deleteAsync(authenticationToken, first, second, version, params);
            }
        });
    }

    @Override
    public JobId deleteAsync(final TokenProxy<?, TokenType.Simple> tokenProxy, final String first, final String second, final String third, final int version, final Map<String, Object> params) throws HodErrorException {
        return requester.makeRequest(tokenProxy, JobId.class, new Requester.BackendCaller<EntityType, TokenType.Simple>() {
            @Override
            public Response makeRequest(final AuthenticationToken<?, ? extends TokenType.Simple> authenticationToken) throws HodErrorException {
                return havenOnDemandBackend.deleteAsync(authenticationToken, first, second, third, version, params);
            }
        });
    }
}
