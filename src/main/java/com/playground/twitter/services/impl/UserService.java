package com.playground.twitter.services.impl;

import com.playground.twitter.errors.NickNameExistsError;
import com.playground.twitter.errors.UserNotFound;
import com.playground.twitter.models.User;
import com.playground.twitter.services.IDataStore;
import com.playground.twitter.services.IUserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@NoArgsConstructor
public class UserService implements IUserService {
    @Autowired
    IDataStore dataStore;

    @Override
    public Collection<User> getAllUsers() {
        return dataStore.getUsers();
    }

    @Override
    public User getUserByNick(final String nick) {
        return dataStore.getUser(nick);
    }

    @Override
    public User registerUser(final User user) throws NickNameExistsError {
        if (dataStore.exists(user.getNickName())) {
            throw new NickNameExistsError();
        }
        dataStore.addUser(user);
        return dataStore.getUser(user.getNickName());
    }

    @Override
    public User updateUserName(final String nickName, final String name) throws UserNotFound {
        final User updUser = getUser(nickName);
        updUser.setName(name);
        dataStore.updateUser(updUser);
        return updUser;
    }

    @Override
    public User addFollow(final String nickFollower, final String nickFollow) throws UserNotFound {
        if (!dataStore.exists(nickFollow)) {
            throw new UserNotFound();
        }
        final User updUser = getUser(nickFollower);
        updUser.addFollow(nickFollow);
        dataStore.updateUser(updUser);
        dataStore.addFollower(nickFollow, nickFollower);
        return updUser;
    }

    @Override
    public Collection<String> getFollowers(final String nickName) {
        return dataStore.getFollowers(nickName);
    }

    private User getUser(final String nickName) {
        final User user = dataStore.getUser(nickName);
        if (user==null) {
            throw new UserNotFound();
        }
        return user;
    }
}
