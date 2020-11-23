package com.playground.twitter.services;

import com.playground.twitter.models.Post;
import com.playground.twitter.models.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public interface IDataStore {
    User getUser(String nick);

    void addUser(User user);

    void addFollower(String nick, String nickFollower);

    Collection<User> getUsers();

    void clearAll();

    boolean exists(String nickName);

    HashSet<String> getFollowers(String nickName);

    void updateUser(User updUser);

    List<Post> getPosts(String nickName);

    void addPost(String nickName, Post post);
}
