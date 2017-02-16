package com.hp.autonomy.hod.client.api.textindex.query.parametric;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
class FieldsResponse<T> {
    private final List<T> fields;

    @JsonCreator
    public FieldsResponse(@JsonProperty("fields") final List<T> fields) {
        this.fields = new ArrayList<>(fields);
    }
}
