/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.util.Request;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

public class AuthenticationServiceTest {
    private static final String ENDPOINT = "https://api.testdomain.com";
    private static final String COMBINED_PATH = "/2/authenticate/combined";
    private static final List<String> ALLOWED_ORIGINS = Arrays.asList("https://example.idolondemand.com", "http://example2.idolondemna.com");

    private static final AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> TOKEN = new AuthenticationToken<>(
        EntityType.Unbound.INSTANCE,
        TokenType.HmacSha1.INSTANCE,
        new DateTime(123),
        "my-token-id",
        "my-token-secret",
        new DateTime(456)
    );

    private static final String DOMAIN = "MY-APPLICATION-DOMAIN";
    private static final String APPLICATION = "MY-APPLICATION-NAME";
    private static final String USER_STORE_DOMAIN = "MY-STORE-DOMAIN";
    private static final String USER_STORE_NAME = "MY-STORE-NAME";

    private AuthenticationService service;

    @Before
    public void initialise() {
        final HodServiceConfig<?, ?> config = new HodServiceConfig.Builder<>(ENDPOINT).build();
        service = new AuthenticationServiceImpl(config);
    }

    @Test
    public void generatesGetCombinedSignedRequest() throws URISyntaxException {
        final SignedRequest request = service.combinedGetRequest(ALLOWED_ORIGINS, TOKEN);

        assertThat(request.getVerb(), is(Request.Verb.GET));

        checkUrl(request);

        assertThat(request.getBody(), is(nullValue()));
        assertThat(request.getToken(), is("UNB:HMAC_SHA1:my-token-id::iIccQELdwLXw9zypF86bwXKbaNQ"));
    }

    @Test
    public void generatesCombinedSignedRequest() throws URISyntaxException {
        final SignedRequest request = service.combinedRequest(ALLOWED_ORIGINS, TOKEN, DOMAIN, APPLICATION, USER_STORE_DOMAIN, USER_STORE_NAME, TokenType.Simple.INSTANCE);

        assertThat(request.getVerb(), is(Request.Verb.POST));

        checkUrl(request);

        final List<NameValuePair> pairs = URLEncodedUtils.parse(request.getBody(), StandardCharsets.UTF_8);
        checkParameterPair(pairs, "domain", DOMAIN);
        checkParameterPair(pairs, "application", APPLICATION);
        checkParameterPair(pairs, "userstore_domain", USER_STORE_DOMAIN);
        checkParameterPair(pairs, "userstore_name", USER_STORE_NAME);
        checkParameterPair(pairs, "token_type", TokenType.Simple.INSTANCE.getParameter());

        assertThat(request.getToken(), is("UNB:HMAC_SHA1:my-token-id:wJkMexQxgEhW13IAeN6i6A:R9XBlyBildIbslAWyxDwQ5O-8WQ"));
    }

    @Test
    public void generatesANonce() {
        final SignedRequest request = service.combinedRequest(ALLOWED_ORIGINS, TOKEN, DOMAIN, APPLICATION, USER_STORE_DOMAIN, USER_STORE_NAME, TokenType.Simple.INSTANCE, true);
        final List<NameValuePair> bodyPairs = URLEncodedUtils.parse(request.getBody(), StandardCharsets.UTF_8);
        final NameValuePair noncePair = getParameterPair(bodyPairs, "nonce");
        assertThat(noncePair, notNullValue());
        final String nonce = noncePair.getValue();
        assertThat(nonce, notNullValue());
    }

    private void checkUrl(final SignedRequest request) throws URISyntaxException {
        final URI uri = new URI(request.getUrl());
        assertThat(uri.getScheme() + "://" + uri.getHost(), is(ENDPOINT));
        assertThat(uri.getPath(), is(COMBINED_PATH));

        final List<NameValuePair> pairs = URLEncodedUtils.parse(uri, "UTF-8");
        assertThat(pairs, hasSize(2));

        assertThat(pairs.get(0).getName(), is("allowed_origins"));
        assertThat(pairs.get(0).getValue(), is(ALLOWED_ORIGINS.get(0)));

        assertThat(pairs.get(1).getName(), is("allowed_origins"));
        assertThat(pairs.get(1).getValue(), is(ALLOWED_ORIGINS.get(1)));
    }

    private void checkParameterPair(final List<NameValuePair> pairs, final String name, final String value) {
        final NameValuePair expectedPair = getParameterPair(pairs, name);

        assertThat(expectedPair, is(notNullValue()));

        if (expectedPair != null) {
            assertThat(expectedPair.getValue(), is(value));
        }
    }

    private NameValuePair getParameterPair(final List<NameValuePair> pairs, final String name) {
        for (final NameValuePair pair : pairs) {
            if (pair.getName().equals(name)) {
                return pair;
            }
        }

        return null;
    }
}

