package com.hp.autonomy.hod.client.util;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Hmac {
    private static final String HMAC_SHA1 = "HmacSHA1";
    private static final String UTF8 = "UTF-8";
    public static final String MD5 = "MD5";

    private static final String COLON = ":";
    private static final String EMPTY = "";
    private static final String NEW_LINE = "\n";

    /**
     * Generate the token header for an HMAC signed request to Haven OnDemand.
     * @param request The request to authenticate
     * @param token The HMAC SHA1 authentication token
     * @return The token parameter for the request
     */
    public String generateToken(final Request request, final AuthenticationToken token) {
        final String bodyHash = createBodyHash(request.getBody());
        final String message = createMessage(request, bodyHash);
        final String signature = base64EncodeForUri(hmacSha1(message, token.getSecret()));

        final List<String> components = Arrays.asList(
                token.getType(),
                token.getId(),
                bodyHash,
                signature
        );

        return StringUtils.join(components, COLON);
    }

    // Creates the representation of the request for HMAC signing, given a request and it's body hash
    private String createMessage(final Request request, final String bodyHash) {
        final List<String> components = new LinkedList<>();
        components.add(encodeVerb(request.getVerb()));
        components.add(encodePath(request.getPath()));
        components.addAll(encodeQueryParameters(request.getQueryParameters()));
        components.add(urlEncode(bodyHash));
        return StringUtils.join(components, NEW_LINE);
    }

    private List<String> encodeQueryParameters(final Map<String, List<?>> queryParameters) {
        if (queryParameters == null || queryParameters.isEmpty()) {
            return Collections.emptyList();
        } else {
            return encodeAndSpreadParameterMap(queryParameters, new ValueEncoder() {
                @Override
                public String encode(final Object input) {
                    return urlEncode(input.toString());
                }
            });
        }
    }

    private String createBodyHash(final Map<String, List<?>> body) {
        if (body == null || body.isEmpty()) {
            // If no body, the body hash must be the empty string
            return EMPTY;
        } else {
            final List<String> components = encodeAndSpreadParameterMap(body, new ValueEncoder() {
                @Override
                public String encode(final Object input) {
                    final byte[] bytes;

                    if (input instanceof byte[]) {
                        bytes = (byte[]) input;
                    } else {
                        bytes = bytesFromString(input.toString());
                    }

                    return Base64.encodeBase64String(md5Hash(bytes));
                }
            });

            final String bodyRepresentation = StringUtils.join(components, NEW_LINE);
            return base64EncodeForUri(md5Hash(bytesFromString(bodyRepresentation)));
        }
    }

    /*
        Takes a map of parameter name to list of parameter values. URI encodes every key and encodes every value using
        the ValueEncoder, then returns a list containing each encoded value adjacent to it's encoded key. The list is
        sorted by the encoded key, but maintains the order of each value associated with a given key.

        {key1: [value11, value12], key2: [value21]}
        uri(key2) < uri(key1)

        => [uri(key2), encode(value21), uri(key1), encode(value11), uri(key1), encode(value12)]
    */
    private List<String> encodeAndSpreadParameterMap(final Map<String, List<?>> parameterMap, final ValueEncoder encoder) {
        final List<Parameter> parameters = new LinkedList<>();

        for (final Map.Entry<String, List<?>> entry : parameterMap.entrySet()) {
            final String encodedKey = urlEncode(entry.getKey());
            final List<?> values = entry.getValue();

            for (final Object value : values) {
                parameters.add(new Parameter(encodedKey, encoder.encode(value)));
            }
        }

        // Sort is guaranteed to be stable, so parameters for a given key will remain in the same order
        Collections.sort(parameters);

        final List<String> components = new LinkedList<>();

        for (final Parameter parameter : parameters) {
            components.add(parameter.key);
            components.add(parameter.value);
        }

        return components;
    }

    private String encodePath(final String path) {
        // Path must be url encoded and have no leading or trailing slashes
        return urlEncode(path.replaceAll("^/|/$", EMPTY));
    }

    private String encodeVerb(final Request.Verb verb) {
        return verb.name();
    }

    private byte[] bytesFromString(final String input) {
        try {
            return input.getBytes(UTF8);
        } catch (final UnsupportedEncodingException e) {
            // This should never happen on a sensible JVM
            throw new AssertionError("UTF8 is not supported", e);
        }
    }

    private String base64EncodeForUri(final byte[] bytes) {
        // Some base64 characters are not valid in a URI
        return Base64.encodeBase64String(bytes).replaceAll("=", EMPTY).replaceAll("/", "-").replaceAll("[+]", "_");
    }

    private String urlEncode(final String input) {
        try {
            return URLEncoder.encode(input, UTF8);
        } catch (final UnsupportedEncodingException e) {
            // This should never happen on a sensible JVM
            throw new AssertionError("UTF8 is not supported", e);
        }
    }

    private byte[] md5Hash(final byte[] input) {
        try {
            return MessageDigest.getInstance(MD5).digest(input);
        } catch (final NoSuchAlgorithmException e) {
            // This should never happen on a sensible JVM
            throw new AssertionError("UTF8 or MD5 is not supported");
        }
    }

    private byte[] hmacSha1(final String message, final String secret) {
        try {
            final Mac mac = Mac.getInstance(HMAC_SHA1);
            final Key key = new SecretKeySpec(bytesFromString(secret), HMAC_SHA1);
            mac.init(key);
            return mac.doFinal(bytesFromString(message));
        } catch (final NoSuchAlgorithmException e) {
            // This should never happen on a sensible JVM
            throw new AssertionError("HMAC SHA1 is not supported", e);
        } catch (final InvalidKeyException e) {
            // In practice, this means that the token secret was invalid
            throw new IllegalArgumentException("Invalid token secret", e);
        }
    }

    private static class Parameter implements Comparable<Parameter> {
        private final String key;
        private final String value;

        private Parameter(final String key, final String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(final Parameter other) {
            return key.compareTo(other.key);
        }
    }

    private interface ValueEncoder {
        String encode(Object input);
    }
}
