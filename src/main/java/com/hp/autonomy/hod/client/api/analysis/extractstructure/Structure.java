package com.hp.autonomy.hod.client.api.analysis.extractstructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
class Structure
{
    @JsonProperty("content") private List<LinkedHashMap<String, String>> content;
}
