package com.hp.autonomy.iod.client.api.textindexing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = Indexes.Builder.class)
public class Indexes {
    private final List<Index> indexes;
    private final List<Index> publicIndexes;

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {

        @SuppressWarnings("FieldMayBeFinal")
        @JsonProperty("index")
        private List<Index> indexes = Collections.emptyList();

        @SuppressWarnings("FieldMayBeFinal")
        @JsonProperty("public_index")
        private List<Index> publicIndexes = Collections.emptyList();

        public Indexes build() {
            return new Indexes(indexes, publicIndexes);
        }
    }
}
