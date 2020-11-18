package com.playground.twitter.service.impl;

import com.playground.twitter.errors.NickNameExistsError;
import com.playground.twitter.errors.UserNotFound;
import com.playground.twitter.model.User;
import com.playground.twitter.service.IDataStore;
import com.playground.twitter.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class UserService implements IUserService {
    @Autowired
    IDataStore dataStore;

    @Override
    public Collection<User> all() {
        return dataStore.getUsers();
    }

    @Override
    public User one(final String nick) {
        return dataStore.getUser(nick);
    }

    @Override
    public User register(final User user) throws NickNameExistsError {
        if (dataStore.exists(user.getNickName())) {
            throw new NickNameExistsError();
        }
        dataStore.putUser(user);
        return dataStore.getUser(user.getNickName());
    }

    @Override
    public User updateName(final String nickName, final String name) throws UserNotFound {
        final User updUser = getUser(nickName);
        updUser.setName(name);
        dataStore.putUser(updUser);
        return updUser;
    }

    @Override
    public User addFollow(final String nickFollower, final String nickFollow) throws UserNotFound {
        if (!dataStore.exists(nickFollow)) {
            throw new UserNotFound();
        }
        final User updUser = getUser(nickFollower);
        updUser.addFollow(nickFollow);
        dataStore.putUser(updUser);
        return updUser;
    }

    private User getUser(final String nickName) {
        final User user = dataStore.getUser(nickName);
        if (user==null) {
            throw new UserNotFound();
        }
        return user;
    }
}
