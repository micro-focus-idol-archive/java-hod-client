package com.hp.autonomy.hod.client.api.userstore.user;

import com.hp.autonomy.hod.client.api.developer.UserAuthMode;
import com.hp.autonomy.hod.client.util.MultiMap;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * Helper class for building up optional parameters for the create user API. The default value
 * for all parameters is null. Null parameters will not be sent to HP Haven OnDemand
 */
@Setter
@Accessors(chain = true)
public class CreateUserRequestBuilder {

    /**
     * @param userMessage The user_message create user parameter
     */
    private String userMessage;

    /**
     * @param userMode The mode by which users must authenticate
     */
    private UserAuthMode userMode;

    /**
     * @param metadata Metadata to associate with the user
     */
    private Object metadata;

    Map<String, Object> build() {
        final Map<String, Object> map = new MultiMap<>();
        map.put("user_message", userMessage);
        map.put("user_mode", userMode);
        map.put("meta_data", metadata);

        return map;
    }
}
