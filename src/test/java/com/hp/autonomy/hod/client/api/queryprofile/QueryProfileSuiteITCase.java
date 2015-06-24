/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Ignore // until we can get our own API key these tests will not be reliable because of interference from live query profiles
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateDeleteQueryProfileServiceSuiteChild.class,
        ListQueryProfilesSuiteChild.class,
        RetrieveQueryProfileServiceSuiteChild.class,
        UpdateQueryProfilesServiceSuiteChild.class
})
public class QueryProfileSuiteITCase {}
