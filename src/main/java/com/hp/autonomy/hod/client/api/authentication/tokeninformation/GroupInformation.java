package com.hp.autonomy.hod.client.api.authentication.tokeninformation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.resource.Resource;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Information about groups within a particular user store to which users may belong
 */
@Data
public class GroupInformation implements Serializable {
    private static final long serialVersionUID = 7028861394100903117L;

    /**
     * @return Information about the user store containing the groups.
     */
    @SuppressWarnings("InstanceVariableOfConcreteClass")
    private final Resource userStore;
    /**
     * @return Groups within the specified the specified user store to which the user belongs.
     */
    private final Set<String> groups;

    public GroupInformation(
            @JsonProperty("user_store") final Resource userStore,
            @JsonProperty("groups") final Set<String> groups
    ) {
        this.userStore = userStore;
        this.groups = new LinkedHashSet<>(groups);
    }
}
