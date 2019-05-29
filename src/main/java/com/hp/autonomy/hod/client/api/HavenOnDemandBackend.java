/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodErrorException;
import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.PartMap;
import retrofit.http.Path;
import retrofit.http.QueryMap;

import java.util.Map;

/**
 * Service for calling Micro Focus Haven OnDemand APIs which do not have a dedicated service. These methods provide no assistance
 * with calling Micro Focus Haven OnDemand, so prefer dedicated services where available
 */
interface HavenOnDemandBackend {

    String SYNC_URL_ONE = "/2/api/sync/{first}/v{version}";
    String SYNC_URL_TWO = "/2/api/sync/{first}/{second}/v{version}";
    String SYNC_URL_THREE = "/2/api/sync/{first}/{second}/{third}/v{version}";
    String ASYNC_URL_ONE = "/2/api/async/{first}/v{version}";
    String ASYNC_URL_TWO = "/2/api/async/{first}/{second}/v{version}";
    String ASYNC_URL_THREE = "/2/api/async/{first}/{second}/{third}/v{version}";

    /**
     * Sends a GET request to the given API
     * @param token The token used to authenticate the request
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token if not using a RequestInterceptor
     * @return A Map representing the result from Micro Focus Haven OnDemand
     * @throws HodErrorException
     */
    @GET(SYNC_URL_ONE)
    Response get(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String api,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a GET request to the given API
     * @param token The token used to authenticate the request
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token if not using a RequestInterceptor
     * @return A Map representing the result from Micro Focus Haven OnDemand
     * @throws HodErrorException
     */
    @GET(SYNC_URL_TWO)
    Response get(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String first,
        @Path("second") String second,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a GET request to the given API
     * @param token The token used to authenticate the request
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param third The third part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token if not using a RequestInterceptor
     * @return A Map representing the result from Micro Focus Haven OnDemand
     * @throws HodErrorException
     */
    @GET(SYNC_URL_THREE)
    Response get(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String first,
        @Path("second") String second,
        @Path("third") String third,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a GET request to the given API asynchronously
     * @param token The token used to authenticate the request
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @GET(ASYNC_URL_ONE)
    Response getAsync(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String api,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a GET request to the given API asynchronously
     * @param token The token used to authenticate the request
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @GET(ASYNC_URL_TWO)
    Response getAsync(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String first,
        @Path("second") String second,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a GET request to the given API asynchronously
     * @param token The token used to authenticate the request
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @GET(ASYNC_URL_THREE)
    Response getAsync(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String first,
        @Path("second") String second,
        @Path("third") String third,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a POST request to the given API
     * @param token The token used to authenticate the request
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return A Map representing the result from Micro Focus Haven OnDemand
     * @throws HodErrorException
     */
    @POST(SYNC_URL_ONE)
    @Multipart
    Response post(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String api,
        @Path("version") int version,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a POST request to the given API
     * @param token The token used to authenticate the request
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return A Map representing the result from Micro Focus Haven OnDemand
     * @throws HodErrorException
     */
    @POST(SYNC_URL_TWO)
    @Multipart
    Response post(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String first,
        @Path("second") String second,
        @Path("version") int version,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a POST request to the given API
     * @param token The token used to authenticate the request
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param third The third part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return A Map representing the result from Micro Focus Haven OnDemand
     * @throws HodErrorException
     */
    @POST(SYNC_URL_THREE)
    @Multipart
    Response post(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String first,
        @Path("second") String second,
        @Path("third") String third,
        @Path("version") int version,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a POST request to the given API asynchronously
     * @param token The token used to authenticate the request
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return A Map representing the result from Micro Focus Haven OnDemand
     * @throws HodErrorException
     */
    @POST(ASYNC_URL_ONE)
    @Multipart
    Response postAsync(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("api") String api,
        @Path("version") int version,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a POST request to the given API asynchronously
     * @param token The token used to authenticate the request
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return A Map representing the result from Micro Focus Haven OnDemand
     * @throws HodErrorException
     */
    @POST(ASYNC_URL_TWO)
    @Multipart
    Response postAsync(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String first,
        @Path("second") String second,
        @Path("version") int version,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a POST request to the given API asynchronously
     * @param token The token used to authenticate the request
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param third The third part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return A Map representing the result from Micro Focus Haven OnDemand
     * @throws HodErrorException
     */
    @POST(ASYNC_URL_THREE)
    @Multipart
    Response postAsync(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String first,
        @Path("second") String second,
        @Path("third") String third,
        @Path("version") int version,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a PUT request to the given API
     * @param token The token used to authenticate the request
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return A Map representing the result from Micro Focus Haven OnDemand
     * @throws HodErrorException
     */
    @PUT(SYNC_URL_ONE)
    @Multipart
    Response put(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String api,
        @Path("version") int version,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a PUT request to the given API
     * @param token The token used to authenticate the request
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return A Map representing the result from Micro Focus Haven OnDemand
     * @throws HodErrorException
     */
    @PUT(SYNC_URL_TWO)
    @Multipart
    Response put(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String first,
        @Path("second") String second,
        @Path("version") int version,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a PUT request to the given API
     * @param token The token used to authenticate the request
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param third The third part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return A Map representing the result from Micro Focus Haven OnDemand
     * @throws HodErrorException
     */
    @PUT(SYNC_URL_THREE)
    @Multipart
    Response put(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String first,
        @Path("second") String second,
        @Path("third") String third,
        @Path("version") int version,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a PUT request to the given API asynchronously
     * @param token The token used to authenticate the request
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @PUT(ASYNC_URL_ONE)
    @Multipart
    Response putAsync(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String api,
        @Path("version") int version,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a PUT request to the given API asynchronously
     * @param token The token used to authenticate the request
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @PUT(ASYNC_URL_TWO)
    @Multipart
    Response putAsync(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String first,
        @Path("second") String second,
        @Path("version") int version,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a PUT request to the given API asynchronously
     * @param token The token used to authenticate the request
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param third The third part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @PUT(ASYNC_URL_THREE)
    @Multipart
    Response putAsync(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String first,
        @Path("second") String second,
        @Path("third") String third,
        @Path("version") int version,
        @PartMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a DELETE request to the given API
     * @param token The token used to authenticate the request
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token if not using a RequestInterceptor
     * @return A Map representing the result from Micro Focus Haven OnDemand
     * @throws HodErrorException
     */
    @DELETE(SYNC_URL_ONE)
    Response delete(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String api,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a DELETE request to the given API
     * @param token The token used to authenticate the request
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token if not using a RequestInterceptor
     * @return A Map representing the result from Micro Focus Haven OnDemand
     * @throws HodErrorException
     */
    @DELETE(SYNC_URL_TWO)
    Response delete(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String first,
        @Path("second") String second,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a DELETE request to the given API
     * @param token The token used to authenticate the request
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param third The third part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include a token if not using a RequestInterceptor
     * @return A Map representing the result from Micro Focus Haven OnDemand
     * @throws HodErrorException
     */
    @DELETE(SYNC_URL_THREE)
    Response delete(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String first,
        @Path("second") String second,
        @Path("third") String third,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a DELETE request to the given API asynchronously
     * @param token The token used to authenticate the request
     * @param api The name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @DELETE(ASYNC_URL_ONE)
    Response deleteAsync(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String api,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a DELETE request to the given API asynchronously
     * @param token The token used to authenticate the request
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @DELETE(ASYNC_URL_TWO)
    Response deleteAsync(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String first,
        @Path("second") String second,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

    /**
     * Sends a DELETE request to the given API asynchronously
     * @param token The token used to authenticate the request
     * @param first The first part of the name of the API
     * @param second The second part of the name of the API
     * @param version The version of the API
     * @param params The query parameters sent to the API. These should include an token if not using a RequestInterceptor
     * @return The job ID of the request
     * @throws HodErrorException
     */
    @DELETE(ASYNC_URL_THREE)
    Response deleteAsync(
        @Header("token") AuthenticationToken<?, ?> token,
        @Path("first") String first,
        @Path("second") String second,
        @Path("third") String third,
        @Path("version") int version,
        @QueryMap Map<String, Object> params
    ) throws HodErrorException;

}
