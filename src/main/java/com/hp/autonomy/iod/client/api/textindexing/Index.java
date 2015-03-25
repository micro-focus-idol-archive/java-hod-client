package com.hp.autonomy.iod.client.api.textindexing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@JsonDeserialize(builder = Index.Builder.class)
public class Index {
    private final String index;
    private final String type;
    private final String flavor;
    private final String description;
    private final String dateCreated;

    private Index(final Builder builder) {
        index = builder.index;
        type = builder.type;
        flavor = builder.flavor;
        description = builder.description;
        dateCreated = builder.dateCreated;
    }

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private String index;
        private String type;
        private String flavor;
        private String description;

        @JsonProperty("date_created")
        private String dateCreated;

        public Index build() {
            return new Index(this);
        }

    }
}
