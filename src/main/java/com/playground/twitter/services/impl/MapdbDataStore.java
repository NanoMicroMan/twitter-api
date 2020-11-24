package com.playground.twitter.services.impl;

import com.playground.twitter.models.Post;
import com.playground.twitter.models.User;
import com.playground.twitter.services.IDataStore;
import com.playground.twitter.services.IPersistentMap;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
@NoArgsConstructor
@Data
public class MapdbDataStore implements IDataStore {
    private IPersistentMap<User> userMap = new PersistentMap<>("users");
    private IPersistentMap<HashSet<String>> followersMap = new PersistentMapSet<>("followers");
    private IPersistentMap<HashSet<Post>> postsMap = new PersistentMapSet<>("posts");

    @Override
    public User getUser(final String nickName) {
        return getUserMap().getVal(nickName);
    }

    @Override
    public void addUser(final User user) {
        getUserMap().addVal(user.getNickName(), user);
    }

    @Override
    public void addFollower(String nick, final String nickFollower) {
        getFollowersMap().addVal(nick, nickFollower);
    }

    @Override
    public Collection<User> getUsers() {
        return getUserMap().values();
    }

    @Override
    public boolean exists(final String nickName) {
        return getUserMap().exists(nickName);
    }

    @Override
    public HashSet<String> getFollowers(final String nickName) {
        return getFollowersMap().getVal(nickName);
    }

    @Override
    public void updateUser(final User updUser) {
        getUserMap().update(updUser.getNickName(), updUser);
    }

    @Override
    public HashSet<Post> getPosts(final String nickName) {
        return getPostsMap().getVal(nickName);
    }

    @Override
    public void addPost(String nickName, final Post post) {
        getPostsMap().addVal(nickName, post);
    }

    @Override
    public void clearAll() {
        userMap.clear();
        followersMap.clear();
        postsMap.clear();
    }

}
