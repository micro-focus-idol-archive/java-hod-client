package com.hp.autonomy.hod.client.api.resource;

import com.hp.autonomy.hod.client.util.MultiMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ListResourcesRequestBuilder {
    private Set<ResourceType> types = new HashSet<>();
    private Set<ResourceFlavour> flavours = new HashSet<>();

    /**
     * @param types Resource type restriction
     */
    public void setTypes(final Set<ResourceType> types) {
        if (types != null) {
            this.types = types;
        }
    }

    /**
     * @param flavours Resource flavour restriction
     */
    public void setFlavours(final Set<ResourceFlavour> flavours) {
        if (flavours != null) {
            this.flavours = flavours;
        }
    }

    public Map<String, Object> build() {
        final Map<String, Object> parameters = new MultiMap<>();

        for (final ResourceType type : types) {
            parameters.put("type", type);
        }

        for (final ResourceFlavour flavour : flavours) {
            parameters.put("flavor", flavour);
        }

        return parameters;
    }
}
