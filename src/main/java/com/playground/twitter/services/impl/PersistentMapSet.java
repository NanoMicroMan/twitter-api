package com.playground.twitter.services.impl;

import java.util.HashSet;

public class PersistentMapSet<T> extends PersistentMap<HashSet<T>> {

    protected PersistentMapSet(final String name) {
        super(name);
    }
@Override
    public HashSet<T> getVal(final String key) {
        return getMap().getOrDefault(normalizeKey(key), new HashSet<>());
    }
@Override
    public void addVal(final String key, final Object val) {
        final HashSet<T> values = getVal(key);
        values.add((T)val);
        getMap().put(normalizeKey(key), values);
    }
}
