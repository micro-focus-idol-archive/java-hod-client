package com.hp.autonomy.hod.client.api.textindex.query.parametric;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Builder
@Data
@JsonDeserialize(builder = FieldRanges.FieldRangesBuilder.class)
public class FieldRanges {
    private final String name;
    private final Integer totalRanges;
    private final ValueDetails valueDetails;
    private final List<ValueRange> valueRanges;

    @JsonPOJOBuilder(withPrefix = "")
    public static class FieldRangesBuilder {
        @JsonProperty("total_ranges")
        private Integer totalRanges;

        @JsonProperty("value_details")
        private ValueDetails valueDetails;

        @JsonProperty("value_ranges")
        private List<ValueRange> valueRanges;
    }

    @Builder
    @Data
    @JsonDeserialize(builder = ValueDetails.ValueDetailsBuilder.class)
    public static class ValueDetails {
        private final Integer count;
        private final Double sum;
        private final Double mean;
        private final Double minimum;
        private final Double maximum;

        @JsonPOJOBuilder(withPrefix = "")
        public static class ValueDetailsBuilder {}
    }

    @Builder
    @Data
    @JsonDeserialize(builder = ValueRange.ValueRangeBuilder.class)
    public static class ValueRange {
        private final Double lowerBound;
        private final Double upperBound;
        private final Integer count;

        @JsonPOJOBuilder(withPrefix = "")
        public static class ValueRangeBuilder {
            @JsonProperty("lower_bound")
            private Double lowerBound;

            @JsonProperty("upper_bound")
            private Double upperBound;
        }
    }
}
