package com.hp.autonomy.hod.client.api.textindex.query.fields;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds the response from the RetrieveIndexFields API
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = RetrieveIndexFieldsByIndexResponseWrapper.Builder.class)
class RetrieveIndexFieldsByIndexResponseWrapper {
    private Map<String, RetrieveIndexFieldsResponse> responseMap;

    @JsonPOJOBuilder(withPrefix = "set")
    @Setter
    @Accessors(chain = true)
    public static class Builder {
        private final Map<String, RetrieveIndexFieldsResponse> responseMap = new HashMap<>();

        @JsonAnySetter
        public Builder populateResponseMap(final String key, final RetrieveIndexFieldsResponse value) {
            responseMap.put(key, value);
            return this;
        }

        RetrieveIndexFieldsByIndexResponseWrapper build() {
            return new RetrieveIndexFieldsByIndexResponseWrapper(responseMap);
        }
    }
}
