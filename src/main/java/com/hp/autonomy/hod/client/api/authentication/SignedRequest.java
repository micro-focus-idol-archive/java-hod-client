package com.hp.autonomy.hod.client.api.authentication;

import com.hp.autonomy.hod.client.util.Hmac;
import com.hp.autonomy.hod.client.util.Request;
import lombok.Data;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Represents a request to HP Haven OnDemand which must be made from a user's browser. The token header should be set to
 * the value of the {@link #token} field. If there is a body, the Content-Type header should be set to "application/x-www-form-urlencoded".
 */
@Data
public class SignedRequest {
    private static final String UTF8 = "UTF-8";

    private final String url;
    private final Request.Verb verb;
    private final String body;
    private final String token;

    private SignedRequest(final String url, final Request.Verb verb, final String body, final String token) {
        this.url = url;
        this.verb = verb;
        this.body = body;
        this.token = token;
    }

    static SignedRequest sign(final Hmac hmac, final String endpoint, final AuthenticationToken token, final Request<String, String> request) {
        final URIBuilder uriBuilder;

        try {
            uriBuilder = new URIBuilder(endpoint + request.getPath());
        } catch (final URISyntaxException e) {
            throw new IllegalArgumentException("Invalid endpoint or request path");
        }

        if (request.getQueryParameters() != null) {
            for (final Map.Entry<String, List<String>> entry : request.getQueryParameters().entrySet()) {
                for (final String value : entry.getValue()) {
                    uriBuilder.addParameter(entry.getKey(), value);
                }
            }
        }

        final String bodyString;

        if (request.getBody() == null) {
            bodyString = null;
        } else {
            final List<NameValuePair> pairs = new LinkedList<>();

            for (final Map.Entry<String, List<String>> entry : request.getBody().entrySet()) {
                for (final String value: entry.getValue()) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }

            bodyString = URLEncodedUtils.format(pairs, UTF8);
        }

        final String tokenString = hmac.generateToken(request, token);
        return new SignedRequest(uriBuilder.toString(), request.getVerb(), bodyString, tokenString);
    }
}
