package com.playground.twitter.services.impl;

import com.playground.twitter.TwitterApplication;
import com.playground.twitter.services.IPersistentMap;
import lombok.Data;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

@Data
public class PersistentMap<T> implements IPersistentMap<T> {

    private final ConcurrentMap<String, T> map;

    protected PersistentMap(final String name) {
        this.map = getDB(name).hashMap(name)
                .keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.JAVA)
                .createOrOpen();
    }

    private DB getDB(final String name) {
        final var db = switch (TwitterApplication.AppConfig.configDB.getType()) {
            case HEAP -> DBMaker.heapDB();
            case OFF_HEAP -> DBMaker.memoryDB();
            case FILE -> DBMaker.fileDB(TwitterApplication.AppConfig.configDB.getDir() + name + ".db").fileMmapEnable().cleanerHackEnable();
        };
        return db.closeOnJvmShutdown().make();
    }

    protected String normalizeKey(final String key) {
        return key==null? "": key.toLowerCase().trim();
    }

    @Override
    public boolean exists(final String key) {
        return getMap().containsKey(normalizeKey(key));
    }

    @Override
    public void update(final String key, final T val) {
        getMap().replace(normalizeKey(key), val);
    }

    @Override
    public T getVal(final String key) {
        return getMap().get(normalizeKey(key));
    }

    @Override
    public void addVal(final String key, final Object val) {
        getMap().put(normalizeKey(key), (T) val);
    }

    @Override
    public Collection<T> values() {
        return getMap().values();
    }

    @Override
    public void clear() {
        getMap().clear();
    }
}
