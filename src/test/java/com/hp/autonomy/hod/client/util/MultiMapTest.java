/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.util;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsMapWithSize.aMapWithSize;
import static org.hamcrest.collection.IsMapWithSize.anEmptyMap;
import static org.junit.Assert.assertThat;

public class MultiMapTest {

    private MultiMap<String, String> map;

    @Before
    public void setUp() {
        map = new MultiMap<>();
    }

    @Test
    public void testSize() {
        assertThat(map, is(aMapWithSize(0)));

        map.put("one", "one");
        map.put("one", "two");
        map.put("two", "two");

        assertThat(map, is(aMapWithSize(3)));
    }

    @Test
    public void testIsEmpty() {
        assertThat(map, is(anEmptyMap()));

        map.put("one", "one");

        assertThat(map, is(not(anEmptyMap())));
    }

    @Test
    public void testContainsKey() {
        map.put("one", "one");

        assertThat(map.containsKey("one"), is(true));
        assertThat(map.containsKey("two"), is(false));
    }

    @Test
    public void testContainsValue() {
        map.put("one", "one");

        assertThat(map.containsValue("one"), is(true));
        assertThat(map.containsValue("two"), is(false));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGet() {
        map.get("one");
    }

    @Test
    public void testRemove() {
        map.put("one", "one");
        map.put("one", "two");
        map.put("two", "two");

        map.remove("one");

        assertThat(map, is(aMapWithSize(1)));
    }

    @Test
    public void testPutAll() {
        final Map<String, String> inputMap = new HashMap<>();
        inputMap.put("one", "one");
        inputMap.put("two", "two");

        map.putAll(inputMap);

        assertThat(map, is(aMapWithSize(2)));
    }

    @Test
    public void testClear() {
        map.put("one", "one");
        map.put("one", "two");
        map.put("two", "two");

        map.clear();

        assertThat(map, is(anEmptyMap()));
    }

    @Test
    public void testKeySet() {
        map.put("one", "one");
        map.put("one", "two");
        map.put("two", "two");

        final Set<String> keys = map.keySet();

        assertThat(keys, hasSize(2));
        assertThat(keys, hasItems("one", "two"));
    }

    @Test
    public void testValues() {
        map.put("one", "one");
        map.put("one", "two");
        map.put("one", "three");
        map.put("two", "two");

        final Collection<String> values = map.values();

        assertThat(values, hasSize(3));
        assertThat(values, hasItems("one", "two", "three"));
    }
}