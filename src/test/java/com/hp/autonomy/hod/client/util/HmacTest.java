/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.util;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.EntityType;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HmacTest {
    private Hmac hmac;

    @Before
    public void initialise() {
        hmac = new Hmac();
    }

    @Test
    public void generatesToken() {
        testHmacSign("UNB:HMAC_SHA1:DF7aRd8VEeSiCdSFZKbA7w:-UTk_c6xZSCH2-MMLPiLJg:cPJN8CsxX6chmGif3TLdTLEpMd8", "IOD-TEST-APPLICATION");
    }

    @Test
    public void generateTokenWithSpacesInParameters() {
        testHmacSign("UNB:HMAC_SHA1:DF7aRd8VEeSiCdSFZKbA7w:xgAlHiMaGMVmddPDmaSc6A:R8dbS5_aZf5jVx6rLB3uY-V6B50", "IOD TEST APPLICATION");
    }

    private void testHmacSign(final String expectedHmacToken, final String application) {
        final String tokenId = "DF7aRd8VEeSiCdSFZKbA7w";
        final String tokenSecret = "Ba90fFmxdioyouz06xr1fhn6Nxq4nB90jWEQ2UzDQr8";

        final AuthenticationToken<EntityType.Unbound, TokenType.HmacSha1> token = new AuthenticationToken<>(
            EntityType.Unbound.INSTANCE,
            TokenType.HmacSha1.INSTANCE,
            new DateTime(123),
            tokenId,
            tokenSecret,
            new DateTime(456)
        );

        final Map<String, List<String>> queryParameters = new HashMap<>();
        queryParameters.put("allowed_origins", Collections.singletonList("http://localhost:8080"));

        final Map<String, List<Object>> body = new HashMap<>();
        body.put("domain", Collections.<Object>singletonList("IOD-TEST-DOMAIN"));
        body.put("application", Collections.<Object>singletonList(application));
        body.put("token_type", Collections.<Object>singletonList(TokenType.Simple.INSTANCE.getParameter()));

        final Request<String, Object> request = new Request<>(Request.Verb.POST, "/2/authenticate/combined", queryParameters, body);
        final String hmacToken = hmac.generateToken(request, token);

        assertThat(hmacToken, is(expectedHmacToken));
    }
}
