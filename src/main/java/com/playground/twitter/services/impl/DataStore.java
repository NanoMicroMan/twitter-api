package com.playground.twitter.services.impl;

import com.playground.twitter.services.IDataStore;
import com.playground.twitter.models.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@NoArgsConstructor
@Service
public class DataStore implements IDataStore {

    private Map<String, User> userMap = new HashMap<>();
    private Map<String, Set<String>> followersMap = new HashMap<>();

    @Override
    public User getUser(final String nickName) {
        return userMap.get(normalizeKey(nickName));
    }

    @Override
    public void putUser(final User user) {
        userMap.put(normalizeKey(user.getNickName()), user);
    }

    @Override
    public void addFollower(String nick, final String nickFollower) {
        nick = normalizeKey(nick);
        Set<String> followers = followersMap.get(nick);
        if (followers==null) {
            followers = new HashSet<>();
        }
        followers.add(normalizeKey(nickFollower));
        followersMap.put(nick, followers);
    }

    @Override
    public Collection<User> getUsers() {
        return userMap.values();
    }

    @Override
    public void clearAll() {
        userMap.clear();
    }

    @Override
    public boolean exists(final String nickName) {
        return userMap.containsKey(normalizeKey(nickName));
    }

    @Override
    public Collection<String> getFollowers(String nickName) {
        return followersMap.get(normalizeKey(nickName));
    }

    private String normalizeKey(final String nickName) {
        return nickName.toLowerCase();
    }
}
