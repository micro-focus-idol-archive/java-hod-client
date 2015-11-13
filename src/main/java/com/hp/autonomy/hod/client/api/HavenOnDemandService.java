/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api;

import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.JobId;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.util.Map;

/**
 * Service which allows arbitrary requests to be made to HP Haven OnDemand
 */
public interface HavenOnDemandService {

    /**
     * Sends a GET request to the given API
     * @param tokenProxy The token proxy to use
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token
     * @param returnType The expected return type from HP Haven OnDemand. Pass in Map.class if you don't have a more
     * specific response type
     * @return A representation of the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    <T> T get(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String api,
        int version,
        Map<String, Object> params,
        Class<T> returnType
    ) throws HodErrorException;

    /**
     * Sends a GET request to the given API
     * @param tokenProxy The token proxy to use
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token
     * @param returnType The expected return type from HP Haven OnDemand. Pass in Map.class if you don't have a more
     * specific response type
     * @return A representation of the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    <T> T get(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String first,
        String second,
        int version,
        Map<String, Object> params,
        Class<T> returnType
    ) throws HodErrorException;

    /**
     * Sends a GET request to the given API
     * @param tokenProxy The token proxy to use
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param third The third part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token
     * @param returnType The expected return type from HP Haven OnDemand. Pass in Map.class if you don't have a more
     * specific response type
     * @return A representation of the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    <T> T get(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String first,
        String second,
        String third,
        int version,
        Map<String, Object> params,
        Class<T> returnType
    ) throws HodErrorException;

    /**
     * Sends a GET request to the given API asynchronously
     * @param tokenProxy The token proxy to use
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return The job ID of the request
     * @throws HodErrorException
     */
    JobId getAsync(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String api,
        int version,
        Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a GET request to the given API asynchronously
     * @param tokenProxy The token proxy to use
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return The job ID of the request
     * @throws HodErrorException
     */
    JobId getAsync(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String first,
        String second,
        int version,
        Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a GET request to the given API asynchronously
     * @param tokenProxy The token proxy to use
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return The job ID of the request
     * @throws HodErrorException
     */
    JobId getAsync(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String first,
        String second,
        String third,
        int version,
        Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a POST request to the given API
     * @param tokenProxy The token proxy to use
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @param returnType The expected return type from HP Haven OnDemand. Pass in Map.class if you don't have a more
     * specific response type
     * @return A representation of the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    <T> T post(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String api,
        int version,
        Map<String, Object> params,
        Class<T> returnType
    ) throws HodErrorException;

    /**
     * Sends a POST request to the given API
     * @param tokenProxy The token proxy to use
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @param returnType The expected return type from HP Haven OnDemand. Pass in Map.class if you don't have a more
     * specific response type
     * @return A representation of the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    <T> T post(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String first,
        String second,
        int version,
        Map<String, Object> params,
        Class<T> returnType
    ) throws HodErrorException;

    /**
     * Sends a POST request to the given API
     * @param tokenProxy The token proxy to use
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param third The third part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @param returnType The expected return type from HP Haven OnDemand. Pass in Map.class if you don't have a more
     * specific response type
     * @return A representation of the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    <T> T post(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String first,
        String second,
        String third,
        int version,
        Map<String, Object> params,
        Class<T> returnType
    ) throws HodErrorException;

    /**
     * Sends a POST request to the given API asynchronously
     * @param tokenProxy The token proxy to use
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    JobId postAsync(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String api,
        int version,
        Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a POST request to the given API asynchronously
     * @param tokenProxy The token proxy to use
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    JobId postAsync(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String first,
        String second,
        int version,
        Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a POST request to the given API asynchronously
     * @param tokenProxy The token proxy to use
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param third The third part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return A Map representing the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    JobId postAsync(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String first,
        String second,
        String third,
        int version,
        Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a PUT request to the given API
     * @param tokenProxy The token proxy to use
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @param returnType The expected return type from HP Haven OnDemand. Pass in Map.class if you don't have a more
     * specific response type
     * @return A representation of the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    <T> T put(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String api,
        int version,
        Map<String, Object> params,
        Class<T> returnType
    ) throws HodErrorException;

    /**
     * Sends a PUT request to the given API
     * @param tokenProxy The token proxy to use
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @param returnType The expected return type from HP Haven OnDemand. Pass in Map.class if you don't have a more
     * specific response type
     * @return A representation of the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    <T> T put(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String first,
        String second,
        int version,
        Map<String, Object> params,
        Class<T> returnType
    ) throws HodErrorException;

    /**
     * Sends a PUT request to the given API
     * @param tokenProxy The token proxy to use
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param third The third part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @param returnType The expected return type from HP Haven OnDemand. Pass in Map.class if you don't have a more
     * specific response type
     * @return A representation of the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    <T> T put(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String first,
        String second,
        String third,
        int version,
        Map<String, Object> params,
        Class<T> returnType
    ) throws HodErrorException;

    /**
     * Sends a PUT request to the given API asynchronously
     * @param tokenProxy The token proxy to use
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return The job ID of the request
     * @throws HodErrorException
     */
    JobId putAsync(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String api,
        int version,
        Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a PUT request to the given API asynchronously
     * @param tokenProxy The token proxy to use
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return The job ID of the request
     * @throws HodErrorException
     */
    JobId putAsync(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String first,
        String second,
        int version,
        Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a PUT request to the given API asynchronously
     * @param tokenProxy The token proxy to use
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param third The third part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return The job ID of the request
     * @throws HodErrorException
     */
    JobId putAsync(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String first,
        String second,
        String third,
        int version,
        Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a DELETE request to the given API
     * @param tokenProxy The token proxy to use
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token
     * @param returnType The expected return type from HP Haven OnDemand. Pass in Map.class if you don't have a more
     * specific response type
     * @return A representation of the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    <T> T delete(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String api,
        int version,
        Map<String, Object> params,
        Class<T> returnType
    ) throws HodErrorException;

    /**
     * Sends a DELETE request to the given API
     * @param tokenProxy The token proxy to use
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token
     * @param returnType The expected return type from HP Haven OnDemand. Pass in Map.class if you don't have a more
     * specific response type
     * @return A representation of the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    <T> T delete(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String first,
        String second,
        int version,
        Map<String, Object> params,
        Class<T> returnType
    ) throws HodErrorException;

    /**
     * Sends a DELETE request to the given API
     * @param tokenProxy The token proxy to use
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param third The third part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token
     * @param returnType The expected return type from HP Haven OnDemand. Pass in Map.class if you don't have a more
     * specific response type
     * @return A representation of the result from HP Haven OnDemand
     * @throws HodErrorException
     */
    <T> T delete(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String first,
        String second,
        String third,
        int version,
        Map<String, Object> params,
        Class<T> returnType
    ) throws HodErrorException;

    /**
     * Sends a DELETE request to the given API asynchronously
     * @param tokenProxy The token proxy to use
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return The job ID of the request
     * @throws HodErrorException
     */
    JobId deleteAsync(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String api,
        int version,
        Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a DELETE request to the given API asynchronously
     * @param tokenProxy The token proxy to use
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return The job ID of the request
     * @throws HodErrorException
     */
    JobId deleteAsync(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String first,
        String second,
        int version,
        Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a DELETE request to the given API asynchronously
     * @param tokenProxy The token proxy to use
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return The job ID of the request
     * @throws HodErrorException
     */
    JobId deleteAsync(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        String first,
        String second,
        String third,
        int version,
        Map<String, Object> params
    ) throws HodErrorException;

}
