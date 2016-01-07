/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.hp.autonomy.hod.client.util.MultiMap;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Map;

class TimeSelector {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("HH:mm:ss dd/MM/yyyy G");

    static Map<String, Object> max(final DateTime maxDate, final Long maxDateDays, final Long maxDateSeconds) {
        final Map<String, Object> map = new MultiMap<>();

        if (maxDate != null) {
            map.put("max_date", DATE_FORMAT.print(maxDate));
        }
        else if (maxDateDays != null) {
            map.put("max_date", maxDateDays);
        }
        else if (maxDateSeconds != null) {
            map.put("max_date", maxDateSeconds + "s");
        }

        return map;
    }

    static Map<String, Object> min(final DateTime minDate, final Long minDateDays, final Long minDateSeconds) {
        final Map<String, Object> map = new MultiMap<>();

        if (minDate != null) {
            map.put("min_date", DATE_FORMAT.print(minDate));
        }
        else if (minDateDays != null) {
            map.put("min_date", minDateDays);
        }
        else if (minDateSeconds != null) {
            map.put("min_date", minDateSeconds + "s");
        }

        return map;
    }
}
