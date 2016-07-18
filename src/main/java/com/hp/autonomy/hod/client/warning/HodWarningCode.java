package com.hp.autonomy.hod.client.warning;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public enum HodWarningCode {

    // General warnings
    INPUT_FILE_IS_EMPTY(20000),

    // Query manipulation warnings
    QUERY_MANIPULATION_PROMOTION_RULES_RESTRICTED(40001),
    QUERY_MANIPULATION_PROMOTION_DOCUMENT_RESTRICTED(40002),
    PROCESSING_QUERY_MANIPULATION_PROMOTION_ERROR(40003),
    INVALID_QUERY_MANIPULATION_RULE_ACTIVATED(40004),

    /**
     * Default code used as a placeholder for codes returned by Haven OnDemand which are not yet enumerated
     */
    UNKNOWN_ERROR_CODE(-1);

    private static final Map<Integer, HodWarningCode> LOOKUP = new HashMap<>();

    static {
        for (final HodWarningCode hodWarningCode : values()) {
            LOOKUP.put(hodWarningCode.code, hodWarningCode);
        }
    }

    private final int code;

    HodWarningCode(final int code) {
        this.code = code;
    }

    public static HodWarningCode fromCode(final int code) {
        final HodWarningCode warningCode = LOOKUP.get(code);

        if (warningCode != null) {
            return warningCode;
        }
        else {
            return UNKNOWN_ERROR_CODE;
        }
    }
}
