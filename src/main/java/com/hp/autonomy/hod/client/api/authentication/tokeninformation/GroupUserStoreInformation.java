package com.hp.autonomy.hod.client.api.authentication.tokeninformation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Information about a user store associated with a group.
 * TODO: combine with {@link UserStoreInformation} once HOD-3394 has been resolved
 */
@Data
public class GroupUserStoreInformation implements Serializable {
    private static final long serialVersionUID = 6262539509053607621L;

    /**
     * @return The name of the user store
     */
    private final String name;
    /**
     * @return The domain of the user store
     */
    private final String domainName;
    /**
     * @return The fully qualified name of the user store (i.e. {domainName}:{name})
     */
    private final String fullyQualifiedName;

    public GroupUserStoreInformation(@JsonProperty("name") final String name,
                                     @JsonProperty("domain_name") final String domainName,
                                     @JsonProperty("fully_qualified_name") final String fullyQualifiedName) {
        this.name = name;
        this.domainName = domainName;
        this.fullyQualifiedName = fullyQualifiedName;
    }
}
