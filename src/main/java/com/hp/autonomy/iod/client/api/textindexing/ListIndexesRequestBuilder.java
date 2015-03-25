package com.hp.autonomy.iod.client.api.textindexing;

import com.hp.autonomy.iod.client.util.MultiMap;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.Set;

@Setter
@Accessors(chain = true)
public class ListIndexesRequestBuilder {
    private Set<IndexType> indexTypes;
    private Set<IndexFlavor> indexFlavors;

    public Map<String, Object> build() {
        final Map<String, Object> map = new MultiMap<>();

        for(final IndexType indexType : indexTypes) {
            map.put("type", indexType);
        }

        for (final IndexFlavor indexFlavor : indexFlavors) {
            map.put("flavor", indexFlavor);
        }

        return map;
    }
}
