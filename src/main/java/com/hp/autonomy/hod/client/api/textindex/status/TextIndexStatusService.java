package com.hp.autonomy.hod.client.api.textindex.status;

import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

public interface TextIndexStatusService {

    /**
     * Get status information for the given text index, using a TokenProxyService for authentication.
     * @param index The index identifier
     * @return Status information for the text index
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws HodErrorException If HOD returns an error
     */
    TextIndexStatus getIndexStatus(ResourceName index) throws HodErrorException;

    /**
     * Get status information for the given text index, using the given token proxy for authentication.
     * @param tokenProxy Token proxy to authenticate the request
     * @param index The index identifier
     * @return Status information for the text index
     * @throws HodErrorException If HOD returns an error
     */
    TextIndexStatus getIndexStatus(TokenProxy<?, TokenType.Simple> tokenProxy, ResourceName index) throws HodErrorException;

}
