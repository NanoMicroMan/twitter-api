package com.playground.twitter.services;

import java.util.Collection;

public interface IPersistentMap<T> {
    boolean exists(String key);

    void update(final String key, T val);

    T getVal(final String key);

    void addVal(final String key, Object val);

    Collection<T> values();

    void clear();
}
