package com.hp.autonomy.hod.client.api.resource;

import lombok.Data;

import java.util.regex.Pattern;

/**
 * Represents a domain-qualified resource identifier; for example the identifier for a text index or user store.
 */
@Data
public class ResourceIdentifier {
    private static final String SEPARATOR = ":";
    private static final Pattern ESCAPE_PATTERN = Pattern.compile("([\\\\:])");

    private final String domain;
    private final String name;

    public ResourceIdentifier(final String domain, final String name) {
        this.domain = domain;
        this.name = name;
    }

    /**
     * Escapes the domain and name and joins them with a colon.
     * @return The HOD resource identifier string
     */
    @Override
    public String toString() {
        return escapeComponent(domain) + SEPARATOR + escapeComponent(name);
    }

    /**
     * HOD resource names (text index names, domain names etc) must have : escaped to \: and \ escaped to \\ when
     * combined into a resource identifier.
     * @param input The string to escape
     * @return The escaped string
     */
    private static String escapeComponent(final String input) {
        return ESCAPE_PATTERN.matcher(input).replaceAll("\\\\$1");
    }
}
