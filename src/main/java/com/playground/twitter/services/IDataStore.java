package com.playground.twitter.services;

import com.playground.twitter.models.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public interface IDataStore {
    User getUser(String nick);

    void addUser(User user);

    void addFollower(String nick, String nickFollower);

    Collection<User> getUsers();

    void clearAll();

    boolean exists(String nickName);

    HashSet<String> getFollowers(String nickName);

    void updateUser(User updUser);
}
