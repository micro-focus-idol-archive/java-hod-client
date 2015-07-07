package com.hp.autonomy.hod.client.api.authentication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.error.HodErrorCode;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

/*
 * $Id:$
 *
 * Copyright (c) 2015, Autonomy Systems Ltd.
 *
 * Last modified by $Author:$ on $Date:$
 */
@RunWith(Parameterized.class)
@Slf4j
public class AuthenticationServiceITCase extends AbstractHodClientIntegrationTest {
    private static final TypeReference<List<ApplicationAndUsers>> GET_APPLICATION_RESPONSE_REFERENCE = new TypeReference<List<ApplicationAndUsers>>() {};
    private static final TypeReference<AuthenticationTokenResponse> TOKEN_RESPONSE_REFERENCE = new TypeReference<AuthenticationTokenResponse>() {};

    private final String endpointUrl;
    private final ObjectMapper mapper = new ObjectMapper();

    private AuthenticationServiceImpl authenticationService;
    private ApiKey apiKey;

    public AuthenticationServiceITCase(final Endpoint endpoint) {
        super(endpoint);
        endpointUrl = endpoint.getUrl();
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        apiKey = new ApiKey(System.getProperty("hp.dev.placeholder.hod.apiKey"));
        authenticationService = new AuthenticationServiceImpl(getConfig());
    }

    @Test
    public void testAuthenticateApplication() throws HodErrorException {
        final TokenProxy tokenProxy = authenticationService.authenticateApplication(
                apiKey,
                APPLICATION_NAME,
                DOMAIN_NAME,
                TokenType.simple
        );

        assertThat(tokenProxy, is(notNullValue()));
    }

    @Test
    public void testAuthenticateUser() throws HodErrorException {
        final TokenProxy tokenProxy = authenticationService.authenticateUser(apiKey, APPLICATION_NAME, DOMAIN_NAME, TokenType.simple);

        assertThat(tokenProxy, is(notNullValue()));
    }

    @Test
    public void unboundAuthentication() throws HodErrorException {
        final AuthenticationToken token = authenticationService.authenticateUnbound(apiKey);
        assertThat(token, is(notNullValue()));
    }

    @Test
    public void failsWithInvalidApiKey() {
        HodErrorCode errorCode = null;

        try {
            authenticationService.authenticateUnbound(new ApiKey("PROBABLY_NOT_A_REAL_API_KEY"));
        } catch (final HodErrorException e) {
            errorCode = e.getErrorCode();
        }

        assertThat(errorCode, is(HodErrorCode.AUTHENTICATION_FAILED));
    }

    @Test
    public void sso() throws HodErrorException, IOException, URISyntaxException {
        final String origin = "http://example.com";
        final Collection<String> allowedOrigins = Arrays.asList("https://another-example.com", origin);

        // Use an HttpClient to represent the browser in the SSO process
        final HttpClient browser = createBrowser();

        // Authenticate the user, creating a cookie in the browser on the HOD endpoint domain (replaces the SSO page)
        // TODO: The API key used to authenticate the user should be configurable
        authenticateUserUnbound(browser, apiKey);

        // Authenticate the application
        // TODO: The API key used to authenticate the application should be configurable
        final AuthenticationToken unboundToken = authenticationService.authenticateUnbound(apiKey);

        // Get a list of applications and users which match the application and user authentications by executing a
        // signed request in the browser
        final SignedRequest getApplicationRequest = authenticationService.combinedGetRequest(allowedOrigins, unboundToken);
        final List<ApplicationAndUsers> applicationResponse = makeSignedRequest(browser, getApplicationRequest, origin, GET_APPLICATION_RESPONSE_REFERENCE);

        assertThat(applicationResponse, not(empty()));

        final ApplicationAndUsers applicationAndUsers = applicationResponse.get(0);
        assertThat(applicationAndUsers.domain, notNullValue());
        assertThat(applicationAndUsers.name, notNullValue());
        assertThat(applicationAndUsers.users, not(empty()));

        final ApplicationAndUsers.User user = applicationAndUsers.users.get(0);
        assertThat(user.storeDomain, notNullValue());
        assertThat(user.storeName, notNullValue());

        // Sign a request to obtain a combined token from the browser
        final SignedRequest combinedRequest = authenticationService.combinedRequest(
                allowedOrigins,
                unboundToken,
                applicationAndUsers.domain,
                applicationAndUsers.name,
                user.storeDomain,
                user.storeName,
                TokenType.simple,
                true
        );

        // Get the combined token
        final AuthenticationTokenResponse authenticationTokenResponse = makeSignedRequest(browser, combinedRequest, origin, TOKEN_RESPONSE_REFERENCE);
        final AuthenticationToken combinedToken = authenticationTokenResponse.getToken();

        assertThat(combinedToken, notNullValue());
    }

    private HttpClient createBrowser() {
        final HttpClientBuilder browserBuilder = HttpClientBuilder.create();
        final String proxyHost = System.getProperty("hp.hod.https.proxyHost");

        if (proxyHost != null) {
            final Integer proxyPort = Integer.valueOf(System.getProperty("hp.hod.https.proxyPort", "8080"));
            browserBuilder.setProxy(new HttpHost(proxyHost, proxyPort));
        }

        return browserBuilder.build();
    }

    private void authenticateUserUnbound(final HttpClient browser, final ApiKey apiKey) throws URISyntaxException, IOException {
        final URI uri = new URIBuilder(endpointUrl + "/2/authenticate/unbound")
                .addParameter("apiKey", apiKey.toString())
                .build();

        final HttpPost postRequest = new HttpPost(uri);
        postRequest.setHeader("Content-Type", "application-x-www-form-urlencoded");

        final List<NameValuePair> bodyPairs = new LinkedList<>();
        bodyPairs.add(new BasicNameValuePair("enable_sso", "true"));
        bodyPairs.add(new BasicNameValuePair("token_type", TokenType.simple.toString()));
        final String bodyString = URLEncodedUtils.format(bodyPairs, StandardCharsets.UTF_8);

        final HttpEntity body = new ByteArrayEntity(bodyString.getBytes(StandardCharsets.UTF_8));
        postRequest.setEntity(body);

        makeRequest(browser, postRequest, TOKEN_RESPONSE_REFERENCE);
    }

    private <T> T makeSignedRequest(final HttpClient browser, final SignedRequest request, final String origin, final TypeReference<T> responseType) throws IOException {
        final RequestBuilder uriRequestBuilder = RequestBuilder.create(request.getVerb().toString())
                .addHeader("token", request.getToken())
                .addHeader("Origin", origin)
                .setUri(request.getUrl());

        if (request.getBody() != null) {
            final HttpEntity body = new ByteArrayEntity(request.getBody().getBytes(StandardCharsets.UTF_8));
            uriRequestBuilder.setEntity(body);
            uriRequestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded");
        }

        return makeRequest(browser, uriRequestBuilder.build(), responseType);
    }

    private <T> T makeRequest(final HttpClient browser, final HttpUriRequest request, final TypeReference<T> responseType) throws IOException {
        final HttpResponse response = browser.execute(request);
        final int statusCode = response.getStatusLine().getStatusCode();

        assertThat(statusCode, is(200));

        try(InputStream content = response.getEntity().getContent()){
            return mapper.readValue(content, responseType);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ApplicationAndUsers {
        private final String domain;
        private final String name;
        private final List<User> users;

        private ApplicationAndUsers(
                @JsonProperty("domain") final String domain,
                @JsonProperty("name") final String name,
                @JsonProperty("users") final List<User> users
        ) {
            this.domain = domain;
            this.name = name;
            this.users = users;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        private static class User {
            private final String storeDomain;
            private final String storeName;

            private User(
                    @JsonProperty("userStore") final String storeName,
                    @JsonProperty("domain") final String storeDomain
            ) {
                this.storeDomain = storeDomain;
                this.storeName = storeName;
            }
        }
    }
}
