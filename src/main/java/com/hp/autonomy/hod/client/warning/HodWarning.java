package com.hp.autonomy.hod.client.warning;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;


@Data
@JsonDeserialize(builder = HodWarning.Builder.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HodWarning {

    private final HodWarningCode code;
    private String details;

    @Setter
    @Accessors(chain = true)
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {

        private HodWarningCode code;
        @JsonProperty("warning")
        private String details;

        public HodWarning.Builder setCode(@JsonProperty("code") final int code) {
            this.code = HodWarningCode.fromCode(code);
            return this;
        }

        public HodWarning build() {
            return new HodWarning(code, details);
        }
    }
}
