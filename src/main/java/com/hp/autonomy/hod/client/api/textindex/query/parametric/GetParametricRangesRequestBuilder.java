package com.hp.autonomy.hod.client.api.textindex.query.parametric;

import com.hp.autonomy.hod.client.api.resource.ResourceName;
import com.hp.autonomy.hod.client.util.MultiMap;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class GetParametricRangesRequestBuilder {
    /**
     * @param text The value of the text parameter
     */
    private String text;

    /**
     * @param fieldText The value of the field_text parameter
     */
    private String fieldText;

    /**
     * @param maxRanges The value of the max_ranges parameter
     */
    private Integer maxRanges;

    /**
     * @param sort The value of the sort parameter
     */
    private ParametricSort sort;

    /**
     * @param totalRanges The value of the total_ranges parameter
     */
    private Boolean totalRanges;

    /**
     * @param valueDetails The value of the value_details parameter
     */
    private Boolean valueDetails;

    /**
     * @param queryProfile The value of the query_profile parameter
     */
    private ResourceName queryProfile;

    /**
     * @param minScore The value of the min_score parameter
     */
    private Number minScore;

    private String securityInfo;

    Map<String, Object> build() {
        final Map<String, Object> map = new MultiMap<>();
        map.put("text", text);
        map.put("field_text", fieldText);
        map.put("max_ranges", maxRanges);
        map.put("min_score", minScore);
        map.put("sort", sort);
        map.put("text", text);
        map.put("total_ranges", totalRanges);
        map.put("value_details", valueDetails);
        return map;
    }
}
