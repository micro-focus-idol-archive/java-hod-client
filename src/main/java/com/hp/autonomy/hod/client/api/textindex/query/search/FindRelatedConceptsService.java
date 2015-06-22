/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface FindRelatedConceptsService {

    /**
     * Query HP Haven OnDemand for related concepts matching query text using a token proxy provided by a
     * {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param text The query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of related concepts that match the query text
     */
    List<Entity> findRelatedConceptsWithText(
        String text,
        FindRelatedConceptsRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for related concepts matching query text using the given token proxy
     * @param tokenProxy The token proxy to use to authenticate the request
     * @param text The query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of related concepts that match the query text
     */
    List<Entity> findRelatedConceptsWithText(
        TokenProxy tokenProxy,
        String text,
        FindRelatedConceptsRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for related concepts using query text from an object store object using a token proxy provided by a
     * {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param reference An HP Haven OnDemand reference obtained from either the Expand Container or Store Object API.
     * The contents of the object will be used as the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of related concepts that match the query text
     */
    List<Entity> findRelatedConceptsWithReference(
        String reference,
        FindRelatedConceptsRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for related concepts using query text from an object store object using the given token proxy
     * @param tokenProxy The token proxy to use to authenticate the request
     * @param reference An HP Haven OnDemand reference obtained from either the Expand Container or Store Object API.
     * The contents of the object will be used as the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of related concepts that match the query text
     */
    List<Entity> findRelatedConceptsWithReference(
        TokenProxy tokenProxy,
        String reference,
        FindRelatedConceptsRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for related concepts using query text from a url using a token proxy provided by a
     * {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param url A publicly accessible HTTP URL from which the query text can be retrieved
     * @param params Additional parameters to be sent as part of the request
     * @return A list of related concepts that match the query text
     */
    List<Entity> findRelatedConceptsWithUrl(
        String url,
        FindRelatedConceptsRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for related concepts using query text from a url using the given token proxy
     * @param tokenProxy The token proxy to use to authenticate the request
     * @param url A publicly accessible HTTP URL from which the query text can be retrieved
     * @param params Additional parameters to be sent as part of the request
     * @return A list of related concepts that match the query text
     */
    List<Entity> findRelatedConceptsWithUrl(
        TokenProxy tokenProxy,
        String url,
        FindRelatedConceptsRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for related concepts using query text in a file using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param file A file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of related concepts that match the query text
     */
    List<Entity> findRelatedConceptsWithFile(
        File file,
        FindRelatedConceptsRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for related concepts using query text in a file using the given token proxy
     * @param tokenProxy The token proxy to use to authenticate the request
     * @param file A file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of related concepts that match the query text
     */
    List<Entity> findRelatedConceptsWithFile(
        TokenProxy tokenProxy,
        File file,
        FindRelatedConceptsRequestBuilder params
    ) throws HodErrorException;
    
    /**
     * Query HP Haven OnDemand for related concepts using query text in a file using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param bytes The bytes of a file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of related concepts that match the query text
     */
    List<Entity> findRelatedConceptsWithFile(
        byte[] bytes,
        FindRelatedConceptsRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for related concepts using query text in a file using the given token proxy
     * @param tokenProxy The token proxy to use to authenticate the request
     * @param bytes The bytes of a file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of related concepts that match the query text
     */
    List<Entity> findRelatedConceptsWithFile(
        TokenProxy tokenProxy,
        byte[] bytes,
        FindRelatedConceptsRequestBuilder params
    ) throws HodErrorException;
    
    /**
     * Query HP Haven OnDemand for related concepts using query text in a file using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param inputStream A file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of related concepts that match the query text
     */
    List<Entity> findRelatedConceptsWithFile(
        InputStream inputStream,
        FindRelatedConceptsRequestBuilder params
    ) throws HodErrorException;

    /**
     * Query HP Haven OnDemand for related concepts using query text in a file using the given token proxy
     * @param tokenProxy The token proxy to use to authenticate the request
     * @param inputStream A file containing the query text
     * @param params Additional parameters to be sent as part of the request
     * @return A list of related concepts that match the query text
     */
    List<Entity> findRelatedConceptsWithFile(
        TokenProxy tokenProxy,
        InputStream inputStream,
        FindRelatedConceptsRequestBuilder params
    ) throws HodErrorException;

}
