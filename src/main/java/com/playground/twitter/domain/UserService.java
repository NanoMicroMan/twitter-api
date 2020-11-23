package com.playground.twitter.domain;

import com.playground.twitter.errors.NickNameExistsError;
import com.playground.twitter.errors.UserNotFound;
import com.playground.twitter.models.Post;
import com.playground.twitter.models.User;
import com.playground.twitter.services.IDataStore;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@NoArgsConstructor
public class UserService  {
    @Autowired
    IDataStore dataStore;

    public Collection<User> getAllUsers() {
        return dataStore.getUsers();
    }

    public User registerUser(final User user) throws NickNameExistsError {
        if (dataStore.exists(user.getNickName())) {
            throw new NickNameExistsError();
        }
        dataStore.addUser(user);
        return dataStore.getUser(user.getNickName());
    }

    public User updateUserName(final String nickName, final String name) throws UserNotFound {
        final User updUser = getUser(nickName);
        updUser.setName(name);
        dataStore.updateUser(updUser);
        return updUser;
    }

    public User addFollow(final String nickFollower, final String nickFollow) throws UserNotFound {
        if (!dataStore.exists(nickFollow)) {
            throw new UserNotFound();
        }
        final User updUser = getUser(nickFollower);
        updUser.addFollow(nickFollow);
        dataStore.updateUser(updUser);
        dataStore.addFollower(nickFollow, nickFollower);
        return getUser(nickFollower);
    }

    public void addPost(String nickName, Post post) throws UserNotFound {
        verifyUserExists(nickName);
        dataStore.addPost(nickName, post);
    }

    public Collection<String> getFollowers(final String nickName) throws UserNotFound {
        verifyUserExists(nickName);
        return dataStore.getFollowers(nickName);
    }

    public User getUser(final String nickName) throws UserNotFound {
        verifyUserExists(nickName);
        return dataStore.getUser(nickName);
    }

    private void verifyUserExists(String nickName) throws UserNotFound {
        if (!dataStore.exists(nickName)) {
            throw new UserNotFound();
        }
    }

    public List<Post> getPosts(String nickName) throws UserNotFound {
        verifyUserExists(nickName);
        return dataStore.getPosts(nickName);
    }
}
