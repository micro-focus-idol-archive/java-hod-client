/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateDeleteQueryProfileServiceSuiteChild.class,
        RetrieveQueryProfileServiceSuiteChild.class,
        UpdateQueryProfilesServiceSuiteChild.class
})
public class QueryProfileSuiteITCase {}
