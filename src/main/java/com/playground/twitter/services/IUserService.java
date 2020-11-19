package com.playground.twitter.services;

import com.playground.twitter.errors.NickNameExistsError;
import com.playground.twitter.errors.UserNotFound;
import com.playground.twitter.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

public interface IUserService {

    Collection<User> getAllUsers();

    User getUserByNick(@PathVariable String nick);

    User registerUser(User user) throws NickNameExistsError;

    User updateUserName(String nickName, String name) throws UserNotFound;

    User addFollow(String nickFollower, String nickFollow) throws UserNotFound;

    Collection<String> getFollowers(String nickName);
}
