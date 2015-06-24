/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.search;

import com.hp.autonomy.hod.client.config.HodServiceConfig;

/**
 * {@link QueryTextIndexServiceImpl} which uses a default return type
 */
public class DocumentsQueryTextIndexService extends QueryTextIndexServiceImpl<Documents> {
    public DocumentsQueryTextIndexService(final HodServiceConfig config) {
        super(config, Documents.class);
    }
}
