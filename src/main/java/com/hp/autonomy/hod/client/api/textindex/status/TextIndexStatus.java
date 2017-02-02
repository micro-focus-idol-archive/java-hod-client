package com.hp.autonomy.hod.client.api.textindex.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.hp.autonomy.hod.client.api.textindex.IndexFlavor;
import com.hp.autonomy.hod.client.api.resource.Resource;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonDeserialize(builder = TextIndexStatus.TextIndexStatusBuilder.class)
public class TextIndexStatus {
    private final long totalDocuments;
    private final long totalIndexSize;
    private final int indexUpdates24hr;
    private final int componentCount;
    private final IndexFlavor flavor;
    private final Resource userStore;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TextIndexStatusBuilder {
        @JsonProperty("total_documents") private long totalDocuments;
        @JsonProperty("total_index_size") private long totalIndexSize;
        @JsonProperty("24hr_index_updates") private int indexUpdates24hr;
        @JsonProperty("component_count") private int componentCount;
        @JsonProperty("user_store") private Resource userStore;
    }
}
