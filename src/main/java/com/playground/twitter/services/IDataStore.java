package com.playground.twitter.services;

import com.playground.twitter.models.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface IDataStore {
    User getUser(String nick);

    void putUser(User user);

    void addFollower(String nick, String nickFollower);

    Collection<User> getUsers();

    void clearAll();

    boolean exists(String nickName);

    Collection<String> getFollowers(String nickName);
}
