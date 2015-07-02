package com.hp.autonomy.hod.client.api.authentication;

import com.hp.autonomy.hod.client.config.HodServiceConfig;
import com.hp.autonomy.hod.client.util.Request;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class AuthenticationServiceTest {
    private static final String ENDPOINT = "https://api.testdomain.com";
    private static final String COMBINED_PATH = "/2/authenticate/combined";
    private static final List<String> ALLOWED_ORIGINS = Arrays.asList("https://example.idolondemand.com", "http://example2.idolondemna.com");
    private static final AuthenticationToken TOKEN = new AuthenticationToken(123, "my-token-id", "my-token-secret", "UNB:HMAC_SHA1", 456);

    private AuthenticationService service;

    @Before
    public void initialise() {
        final HodServiceConfig config = new HodServiceConfig.Builder(ENDPOINT).build();
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
        final String domain = "MY-APPLICATION-DOMAIN";
        final String application = "MY-APPLICATION-NAME";
        final SignedRequest request = service.combinedRequest(ALLOWED_ORIGINS, TOKEN, domain, application, TokenType.simple);

        assertThat(request.getVerb(), is(Request.Verb.POST));

        checkUrl(request);

        final Map<String, List<String>> body = request.getBody();
        assertThat(body.size(), is(3));
        assertThat(body.get("domain"), contains(domain));
        assertThat(body.get("application"), contains(application));
        assertThat(body.get("token_type"), contains(TokenType.simple.toString()));

        assertThat(request.getToken(), is("UNB:HMAC_SHA1:my-token-id:_emvv9HVicQYc2OOlKeeOg:mAH-zkK-OvMq7xHFbN5o4ZKd0Nw"));
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
}

