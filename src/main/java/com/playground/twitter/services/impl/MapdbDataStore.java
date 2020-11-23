package com.playground.twitter.services.impl;

import com.playground.twitter.models.Post;
import com.playground.twitter.services.IDataStore;
import com.playground.twitter.models.User;
import lombok.NoArgsConstructor;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

@Service
@NoArgsConstructor
public class MapdbDataStore implements IDataStore {

    enum DBtype {
        Heap,
        OffHeap,
        File
    }
    private DB db;

    @Value("${fileDB:}")
    private String fileDB;

    private ConcurrentMap<String, User> userMap;
    private ConcurrentMap<String, HashSet<String>> followersMap;
    private ConcurrentMap<String, List<Post>> postsMap;

    private void initDB() {
        db = getDB();

        userMap = getMap("users");
        followersMap = getMap("followers");
        postsMap = getMap("posts");
    }

    private ConcurrentMap getMap(String name) {
        return getDB().hashMap(name)
                .keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.JAVA)
                .createOrOpen();
    }

    private DB getDB() {
        return fileDB.isEmpty() ?
                DBMaker.memoryDB().closeOnJvmShutdown().make() :
                DBMaker.fileDB(fileDB)
                        .fileMmapEnable()
                        .cleanerHackEnable()
                        .closeOnJvmShutdown().make();
    }
    @Override
    public User getUser(final String nickName) {
        return getUserMap().get(normalizeKey(nickName));
    }

    @Override
    public void addUser(final User user) {
        getUserMap().put(normalizeKey(user.getNickName()), user);
    }

    @Override
    public void addFollower(String nick, final String nickFollower) {
        nick = normalizeKey(nick);
        HashSet<String> followers = getFollowersMap().getOrDefault(nick, new HashSet<>());
        followers.add(normalizeKey(nickFollower));
        getFollowersMap().put(nick, followers);
    }

    @Override
    public Collection<User> getUsers() {
        return getUserMap().values();
    }

    @Override
    public void clearAll() {
        getUserMap().clear();
        getFollowersMap().clear();
        getPostsMap().clear();
    }

    @Override
    public boolean exists(final String nickName) {
        return getUserMap().containsKey(normalizeKey(nickName));
    }

    @Override
    public HashSet<String> getFollowers(final String nickName) {
        return getFollowersMap().getOrDefault(normalizeKey(nickName), new HashSet<>());
    }

    @Override
    public void updateUser(User updUser) {
        getUserMap().replace(normalizeKey(updUser.getNickName()), updUser);
    }

    @Override
    public List<Post> getPosts(String nickName) {
        return getPostsMap().getOrDefault(normalizeKey(nickName), new ArrayList<>());
    }

    @Override
    public void addPost(String nickName, Post post) {
        nickName = normalizeKey(nickName);
        List<Post> posts = getPosts(nickName);
        posts.add(post);
        getPostsMap().put(nickName, posts);
    }

    private String normalizeKey(final String nickName) {
        return nickName==null? "": nickName.toLowerCase().trim();
    }

    public ConcurrentMap<String, User> getUserMap() {
        if (db==null) initDB();
        return userMap;
    }

    public ConcurrentMap<String, HashSet<String>> getFollowersMap() {
        if (db==null) initDB();
        return followersMap;
    }

    public ConcurrentMap<String, List<Post>> getPostsMap() {
        if (db==null) initDB();
        return postsMap;
    }
}
