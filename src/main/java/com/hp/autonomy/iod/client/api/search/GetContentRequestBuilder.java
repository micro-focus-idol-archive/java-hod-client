package com.hp.autonomy.iod.client.api.search;

import com.hp.autonomy.iod.client.util.MultiMap;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Helper class for building up optional parameters for the Get Content API. The default value for all parameters
 * is null. Null parameters will not be sent to IDOL OnDemand
 */
@Setter
@Accessors(chain = true)
public class GetContentRequestBuilder {

    /**
     * @param highlightExpression Value for the highlight_expression parameter
     */
    private String highlightExpression;

    /**
     * @param startTag Value for the start_tag parameter
     */
    private String startTag;

    /**
     * @param endTag Value for the end_tag parameter
     */
    private String endTag;

    /**
     * @param printFields Value for the print_fields parameter. This list will be joined with commas before being sent
     *                    to the server
     */
    private List<String> printFields;

    /**
     * @param print Value for the print parameter
     */
    private Print print;

    /**
     * @return A map of query parameters suitable for use with {@link QueryTextIndexService}. get is NOT supported on
     * the resulting map
     */
    public Map<String, Object> build() {
        final Map<String, Object> map = new MultiMap<>();
        map.put("highlight_expression", highlightExpression);
        map.put("start_tag", startTag);
        map.put("end_tag", endTag);
        map.put("print", print);
        map.put("print_fields", StringUtils.join(printFields, ','));

        return map;
    }

}
