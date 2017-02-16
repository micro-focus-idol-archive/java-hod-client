package com.hp.autonomy.hod.client.api.textindex.query.parametric;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Builder
@JsonDeserialize(builder = FieldValues.FieldValuesBuilder.class)
@Data
public class FieldValues {
    private final String name;
    private final Integer totalValues;

    @Singular
    private final List<ValueAndCount> values;

    @JsonPOJOBuilder(withPrefix = "")
    public static class FieldValuesBuilder {
        @JsonProperty("total_values")
        private Integer totalValues;
    }

    @Builder
    @JsonDeserialize(builder = ValueAndCount.ValueAndCountBuilder.class)
    @Data
    public static class ValueAndCount {
        private final String value;
        private final Integer count;
        private final FieldValues subField;

        @JsonPOJOBuilder(withPrefix = "")
        public static class ValueAndCountBuilder {
            @JsonProperty("subfield")
            private FieldValues subField;
        }
    }
}
