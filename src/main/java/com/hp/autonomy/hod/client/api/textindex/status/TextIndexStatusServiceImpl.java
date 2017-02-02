package com.hp.autonomy.hod.client.api.textindex.status;

import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.config.Requester;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

public class TextIndexStatusServiceImpl implements TextIndexStatusService {
    private final TextIndexStatusBackend backend;
    private final Requester<?, TokenType.Simple> requester;

    public TextIndexStatusServiceImpl(final HodServiceConfig<?, TokenType.Simple> config) {
        backend = config.getRestAdapter().create(TextIndexStatusBackend.class);
        requester = config.getRequester();
    }

    @Override
    public TextIndexStatus getIndexStatus(final ResourceIdentifier index) throws HodErrorException {
        return requester.makeRequest(TextIndexStatus.class, backendCaller(index));
    }

    @Override
    public TextIndexStatus getIndexStatus(final TokenProxy<?, TokenType.Simple> tokenProxy, final ResourceIdentifier index) throws HodErrorException {
        return requester.makeRequest(tokenProxy, TextIndexStatus.class, backendCaller(index));
    }

    private Requester.BackendCaller<EntityType, TokenType.Simple> backendCaller(final ResourceIdentifier index) {
        return authenticationToken -> backend.getIndexStatus(authenticationToken, index);
    }
}
