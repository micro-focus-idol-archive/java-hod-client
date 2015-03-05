package com.hp.autonomy.iod.client.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/*
 * $Id:$
 *
 * Copyright (c) 2015, Autonomy Systems Ltd.
 *
 * Last modified by $Author:$ on $Date:$
 */
@Setter
@Accessors(chain = true)
public class QueryTextIndexRequestBuilder {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("HH:mm:ss dd/MM/yyyy G");

    private DateTime maxDate;
    private DateTime minDate;

    private Long maxDateDays;
    private Long maxDateSeconds;

    private Long minDateDays;
    private Long minDateSeconds;

    private String endTag;
    private String fieldText;
    private Highlight highlight;
    private Integer absoluteMaxResults;
    private Integer maxPageResults;
    private Integer minScore;
    private Print print;
    private List<String> printFields;
    private Integer start;
    private Sort sort;
    private String startTag;
    private Summary summary;
    private Boolean totalResults;

    public Map<String, Object> build() {
        final Map<String, Object> map = new HashMap<>();
        map.put("end_tag", endTag);
        map.put("field_text", fieldText);
        map.put("highlight", highlight);
        map.put("absolute_max_results", absoluteMaxResults);
        map.put("max_page_results", maxPageResults);
        map.put("min_score", minScore);
        map.put("print", print);
        map.put("print_fields", StringUtils.join(printFields, ','));
        map.put("sort", sort);
        map.put("start", start);
        map.put("start_tag", startTag);
        map.put("summary", summary);
        map.put("total_results", totalResults);

        // prefer the DateTime over the numeric versions
        if(minDate != null) {
            map.put("min_date", DATE_FORMAT.print(minDate));
        }
        else if(minDateDays != null) {
            map.put("min_date", minDateDays);
        }
        else if(maxDateSeconds != null) {
            map.put("min_date", minDateSeconds + "s");
        }

        if(maxDate != null) {
            map.put("max_date", DATE_FORMAT.print(maxDate));
        }
        else if(maxDateDays != null) {
            map.put("max_date", maxDateDays);
        }
        else if(maxDateSeconds != null) {
            map.put("max_date", maxDateSeconds + "s");
        }

        return map;
    }


    public enum Print {
        all,
        all_sections,
        date,
        fields,
        none,
        no_results,
        parametric,
        reference
    }

    public enum Highlight {
        off,
        terms,
        sentences
    }

    public enum Sort {
        autn_rank,
        date,
        off,
        relevance,
        reverse_date,
        reverse_relevance
    }

    public enum Summary {
        context,
        concept,
        quick,
        off
    }

}
