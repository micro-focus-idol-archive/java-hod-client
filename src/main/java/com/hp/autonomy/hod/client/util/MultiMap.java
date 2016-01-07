/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.util;

import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Map implementation which supports multiple values for keys. This allow converting items in a list to multiple HTTP
 * query parameters/parts. Adding a key pairing multiple times has no effect beyond the first.
 * <p/>
 * Somewhat unusually, get is NOT supported on this map, as it makes little semantic sense.
 * <p/>
 * This map does not support null keys and values
 * @param <K> The type of the map's keys
 * @param <V> The type of the map's values
 */
@SuppressWarnings("NullableProblems")
public class MultiMap<K, V> implements Map<K, V> {

    private final Set<Map.Entry<K, V>> entries = new LinkedHashSet<>();

    /**
     * @return The number of entries in the map
     */
    @Override
    public int size() {
        return entries.size();
    }

    /**
     * @return True if the map has no entries; false otherwise
     */
    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    /**
     * @param key The key to test
     * @return True if at least one value exists for the key; false otherwise
     */
    @Override
    public boolean containsKey(final Object key) {
        for (final Map.Entry<K, V> entry : entries) {
            if (key.equals(entry.getKey())) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param value The key to test
     * @return True if at least one key exists for the given value; false otherwise
     */
    @Override
    public boolean containsValue(final Object value) {
        for (final Map.Entry<K, V> entry : entries) {
            if (value.equals(entry.getValue())) {
                return true;
            }
        }

        return false;
    }

    /**
     * This method is NOT supported.
     * @param key The key
     * @return This method will not return
     * @throws java.lang.UnsupportedOperationException Under all circumstances
     */
    @Override
    public V get(final Object key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Add an entry for the given key-value pair to the map
     * @param key The key
     * @param value The value
     * @return value
     */
    @Override
    public V put(final K key, final V value) {
        entries.add(new Entry(key, value));

        return value;
    }

    /**
     * Remove all entries for the given key
     * @param key The key
     * @return Some value that was paired with the key. The exact value is indeterminate
     */
    @Override
    public V remove(final Object key) {
        final Iterator<Map.Entry<K, V>> iterator = entries.iterator();
        V lastValue = null;

        while (iterator.hasNext()) {
            final Map.Entry<K, V> entry = iterator.next();
            lastValue = entry.getValue();

            if (key.equals(entry.getKey())) {
                iterator.remove();
            }
        }

        return lastValue;
    }

    /**
     * Add all the entries in the given map to this map
     * @param m The map to add entries from
     */
    @Override
    public void putAll(final Map<? extends K, ? extends V> m) {
        for (final Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Removes all entries from the map
     */
    @Override
    public void clear() {
        entries.clear();
    }

    /**
     * @return A set containing all the keys in the map. Note that this will not have the same size as the map
     */
    @Override
    public Set<K> keySet() {
        final Set<K> keys = new HashSet<>();

        for (final Map.Entry<K, V> entry : entries) {
            keys.add(entry.getKey());
        }

        return keys;
    }

    /**
     * @return A collection containing all the values in the map
     */
    @Override
    public Collection<V> values() {
        final Set<V> values = new HashSet<>();

        for (final Map.Entry<K, V> entry : entries) {
            values.add(entry.getValue());
        }

        return values;
    }

    /**
     * @return A set containing all the entries in the map
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return entries;
    }

    @EqualsAndHashCode
    private class Entry implements Map.Entry<K, V> {

        private final K key;
        private V value;

        private Entry(final K key, final V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(final V value) {
            final V oldValue = this.value;

            this.value = value;

            return oldValue;
        }
    }
}
