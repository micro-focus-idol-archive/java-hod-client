package com.hp.autonomy.hod.client.api.textindex.query.parametric;

import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.util.Collection;
import java.util.List;

/**
 * Service representing the GetParametricRanges API.
 */
public interface GetParametricRangesService {

    /**
     * Query parametric ranges for the fieldNames using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     *
     * @param fieldNames A list of field names to return ranges for
     * @param indexes    The indexes to get ranges from
     * @param ranges     String specifying the ranges to query for
     * @param params     Additional parameters to be sent as part of the request
     * @return A list of field names with their parametric ranges
     * @throws NullPointerException                                                           If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     *                                                                                        with the token proxy has expired
     */
    List<FieldRanges> getParametricRanges(
            Collection<String> fieldNames,
            Collection<ResourceName> indexes,
            String ranges,
            GetParametricRangesRequestBuilder params
    ) throws HodErrorException;

    /**
     * Get parametric ranges for the fieldName using the given token proxy
     *
     * @param tokenProxy The token to use to authenticate the request
     * @param fieldNames A list of field names to return ranges for
     * @param indexes    The indexes to get ranges from
     * @param ranges     String specifying the ranges to query for
     * @param params     Additional parameters to be sent as part of the request
     * @return A list of field names with their parametric ranges
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     *                                                                                        with the token proxy has expired
     */
    List<FieldRanges> getParametricRanges(
            TokenProxy<?, TokenType.Simple> tokenProxy,
            Collection<String> fieldNames,
            Collection<ResourceName> indexes,
            String ranges,
            GetParametricRangesRequestBuilder params
    ) throws HodErrorException;

}
